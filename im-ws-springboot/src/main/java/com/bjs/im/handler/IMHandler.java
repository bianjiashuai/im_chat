package com.bjs.im.handler;

import static com.bjs.im.protocol.command.Command.HEARTBEAT_REQUEST;
import static com.bjs.im.protocol.command.Command.LOGIN_REQUEST;

import com.alibaba.fastjson.JSON;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.Packet;
import com.bjs.im.protocol.response.LoginResponsePacket;
import com.bjs.im.util.SessionUtil;

import org.reflections.Reflections;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Date 2019-12-04 16:10:54
 * @Author BianJiashuai
 */
@Slf4j
public class IMHandler extends AbstractHandler<String> {
    public static final IMHandler INSTANCE = new IMHandler();
    private static final Map<Integer, AbstractHandler<? extends Packet>> handlerMap = new ConcurrentHashMap<>();

    private IMHandler() {
    }

    /**
     * 装载业务处理器.
     */
    public void loadHandler() {
        log.info("业务处理器装载中...");
        Long start = System.currentTimeMillis();
        Reflections rf = new Reflections();
        Set<Class<?>> classes = rf.getTypesAnnotatedWith(IMRequest.class);
        classes.stream().forEach(clazz -> {
            try {
                Type type = clazz.getGenericSuperclass();
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    String typeName = pt.getActualTypeArguments()[0].getTypeName();
                    Packet packet = (Packet) Class.forName(typeName).newInstance();
                    handlerMap.put(packet.getCommand(), (AbstractHandler<? extends Packet>) clazz.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Long tc = System.currentTimeMillis() - start;
        log.info("[{}]个业务处理器装载完成!!!, 耗时[{}]ms", handlerMap.size(), tc);
    }

    @Override
    protected void channelRead(ChannelHandlerContext ctx, String msg) throws Exception {
        Packet packet = JSON.parseObject(msg, Packet.class);
        int command = packet.getCommand();
        if (LOGIN_REQUEST == command || HEARTBEAT_REQUEST == command || SessionUtil.hasLogin(ctx.channel())) {
            // 登录请求或者心跳请求或者已经登录的放行
            handlerMap.get(command).exec(ctx, msg);
        } else {
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setErrMsg("请先登录后操作");
            responsePacket.setSuccess(false);
            writeAndFlush(ctx, responsePacket);
        }
    }
}
