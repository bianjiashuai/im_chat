package com.bjs.im.server.handler;

import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2019-12-04 18:27:12
 * @Author BianJiashuai
 */
public class IMIdleStateHandler extends IdleStateHandler {

    public static final int READER_IDLE_TIME = 15;
    public static final int IDLE_TIME_0 = 0;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, IDLE_TIME_0, IDLE_TIME_0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒未读取到数据, 关闭连接, 并释放用户Session");
        Channel channel = ctx.channel();
        SessionUtil.unBindUser(channel);
        channel.close();
    }
}
