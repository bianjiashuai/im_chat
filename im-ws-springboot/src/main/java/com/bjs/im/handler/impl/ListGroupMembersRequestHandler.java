package com.bjs.im.handler.impl;

import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.ListGroupMembersRequestPacket;
import com.bjs.im.protocol.response.ListGroupMembersResponsePacket;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

import java.util.List;
import java.util.stream.Collectors;

@IMRequest
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends AbstractHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {
        // 获取群的ChannelGroup
        String groupId = requestPacket.getGroupId();
        // 获取当前用户Channel
        Channel channel = ctx.channel();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        if (null != channelGroup) {
            if (channelGroup.contains(channel)) {
                List<User> users = channelGroup.stream().map(SessionUtil::getUser).collect(Collectors.toList());
                responsePacket.setGroupName(channelGroup.name());
                responsePacket.setSuccess(true);
                responsePacket.setUsers(users);
            } else {
                // 当前用户不在此群聊中
                responsePacket.setSuccess(false);
                responsePacket.setErrMsg("不在此群聊中,请先加入");
                responsePacket.setGroupId(groupId);
            }
        } else {
            // 无此群聊
            responsePacket.setSuccess(false);
            responsePacket.setErrMsg("无此群聊");
        }
        writeAndFlush(ctx, responsePacket);
    }
}
