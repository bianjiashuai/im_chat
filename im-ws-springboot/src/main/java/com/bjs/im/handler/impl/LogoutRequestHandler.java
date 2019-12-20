package com.bjs.im.handler.impl;

import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.LogoutRequestPacket;
import com.bjs.im.protocol.response.LogoutResponsePacket;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;


/**
 * 登出请求
 */
@IMRequest
@ChannelHandler.Sharable
public class LogoutRequestHandler extends AbstractHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        SessionUtil.unBindUser(ctx.channel());
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        responsePacket.setSuccess(true);
        writeAndFlush(ctx, responsePacket);
    }
}
