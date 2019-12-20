package com.bjs.im.handler.impl;

import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.HeartbeatRequestPacket;
import com.bjs.im.protocol.response.HeartbeatResponsePacket;

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
