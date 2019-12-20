package com.bjs.im.server.handler.impl;

import com.bjs.im.protocol.request.HeartbeatRequestPacket;
import com.bjs.im.protocol.response.HeartbeatResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description
 * @Date 2019-12-04 18:52:08
 * @Author BianJiashuai
 */
@IMRequest
@ChannelHandler.Sharable
public class HeartbeatRequestHandler extends AbstractHandler<HeartbeatRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, HeartbeatRequestPacket packet) throws Exception {
        writeAndFlush(ctx, new HeartbeatResponsePacket());
    }
}
