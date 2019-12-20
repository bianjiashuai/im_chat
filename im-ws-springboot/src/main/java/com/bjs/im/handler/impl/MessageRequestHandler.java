package com.bjs.im.handler.impl;

import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.MessageRequestPacket;
import com.bjs.im.protocol.response.MessageResponsePacket;
import com.bjs.im.util.RedisUtil;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Objects;

@IMRequest
@ChannelHandler.Sharable
public class MessageRequestHandler extends AbstractHandler<MessageRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        // 获取消息发送方的会话信息
        User user = SessionUtil.getUser(ctx.channel());

        // 构造回话信息包
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        // 获取目标用户Id
        Long dstUserId = messageRequestPacket.getDstUserId();
        User dstUser = RedisUtil.getUserSession(String.valueOf(dstUserId));
        if (Objects.isNull(dstUser)) {
            // 无此用户
            responsePacket.setSuccess(false);
            responsePacket.setErrMsg("没有该用户, 发送失败");
        } else {
            responsePacket.setSuccess(true);
            responsePacket.setSrcUserId(user.getId());
            responsePacket.setSrcAccount(user.getAccount());
            responsePacket.setSrcNickName(user.getNickName());
            responsePacket.setMsg(messageRequestPacket.getMsg());
            // 获取消息接收方的Channel
            Channel dstChannel = SessionUtil.getChannel(dstUserId);
            if (Objects.isNull(dstChannel)) {
                System.out.println("1 - [" + dstUser.info() + "] 不在线, 漫游消息暂存!");
                // 存储漫游消息
                RedisUtil.writeOutlineMsg(String.valueOf(dstUserId), responsePacket);
            } else {
                if (SessionUtil.hasLogin(dstChannel)) {
                    // 用户在线
                    writeAndFlush(dstChannel, responsePacket).addListener(future -> {
                        if (future.isDone()) {
                            System.out.println("消息发送完成!!!");
                        }
                    });
                } else {
                    System.out.println("2 - [" + dstUser.info() + "] 不在线, 漫游消息暂存!");
                    // 存储漫游消息
                    RedisUtil.writeOutlineMsg(String.valueOf(dstUserId), responsePacket);
                }
            }
        }
        writeAndFlush(ctx, responsePacket);
    }
}
