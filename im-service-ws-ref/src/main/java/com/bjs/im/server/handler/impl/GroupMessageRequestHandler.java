package com.bjs.im.server.handler.impl;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.request.GroupMessageRequestPacket;
import com.bjs.im.protocol.response.GroupMessageResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

@IMRequest
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends AbstractHandler<GroupMessageRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) {
        // 1.拿到 groupId 构造群聊消息的响应
        String groupId = requestPacket.getGroupId();

        // 2. 拿到群聊对应的 channelGroup
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        // 获取当前channel
        Channel channel = ctx.channel();
        if (null != channelGroup) {
            if (channelGroup.contains(channel)) {
                responsePacket.setSuccess(true);
                responsePacket.setGroupId(groupId);
                responsePacket.setMsg(requestPacket.getMsg());
                responsePacket.setGroupName(channelGroup.name());
                UserSession userSession = SessionUtil.getUserSession(channel);
                responsePacket.setSrcUserId(userSession.getUserId());
                responsePacket.setSrcUserName(userSession.getUserName());
                responsePacket.setSrcNickName(userSession.getNickName());

                // 写到每个客户端
                writeAndFlush(channelGroup, responsePacket);
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setErrMsg("不在此群聊中");
                writeAndFlush(channel, responsePacket);
            }
        } else {
            // 无此群聊
            responsePacket.setSuccess(false);
            responsePacket.setErrMsg("无此群聊");
            writeAndFlush(channel, responsePacket);
        }
    }
}
