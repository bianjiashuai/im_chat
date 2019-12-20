package com.bjs.im.handler;

import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2019-12-04 18:27:12
 * @Author BianJiashuai
 */
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    public static final int READER_IDLE_TIME = 15;
    public static final int IDLE_TIME_0 = 0;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, IDLE_TIME_0, IDLE_TIME_0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("[{}]秒为读取到数据, 关闭连接, 释放用户Session", READER_IDLE_TIME);
        Channel channel = ctx.channel();
        SessionUtil.unBindUser(channel);
        channel.close();
    }
}
