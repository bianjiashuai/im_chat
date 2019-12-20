package com.bjs.im.handler.impl;

import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.JoinGroupRequestPacket;
import com.bjs.im.protocol.response.JoinGroupResponsePacket;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

@IMRequest
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends AbstractHandler<JoinGroupRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        // 获取对应的ChannelGroup, 然后将当前用户加入
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        if (null != channelGroup) {
            Channel channel = ctx.channel();
            if (channelGroup.contains(channel)) {
                responsePacket.setSuccess(false);
                responsePacket.setErrMsg("已经加入此群聊,不能重复加入");
            } else {
                responsePacket.setSuccess(true);
                responsePacket.setGroupName(channelGroup.name());
                responsePacket.setUser(SessionUtil.getUser(channel));
                writeAndFlush(channelGroup, responsePacket); // 通知其他客户端有新用户加群了
                channelGroup.add(channel);
            }
        } else {
            // 无此群聊
            responsePacket.setSuccess(false);
            responsePacket.setErrMsg("无此群聊");
        }
        writeAndFlush(ctx, responsePacket);  // 通知当前用户加群操作
    }
}
