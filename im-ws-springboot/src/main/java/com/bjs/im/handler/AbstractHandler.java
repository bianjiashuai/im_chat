package com.bjs.im.handler;

import com.alibaba.fastjson.JSON;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.ParameterizedType;

/**
 * @Description
 * @Date 2019-12-04 16:16:00
 * @Author BianJiashuai
 */
public abstract class AbstractHandler<T> {

    protected void exec(ChannelHandlerContext ctx, Object msg) throws Exception {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        channelRead(ctx, JSON.parseObject(msg.toString(), clazz));
    }

    protected ChannelFuture writeAndFlush(ChannelHandlerContext ctx, Object packet) {
        return ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(packet)));
    }

    protected ChannelFuture writeAndFlush(Channel channel, Object packet) {
        return channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(packet)));
    }

    protected ChannelGroupFuture writeAndFlush(ChannelGroup channelGroup, Object packet) {
        return channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(packet)));
    }

    protected abstract void channelRead(ChannelHandlerContext ctx, T packet) throws Exception;
}
