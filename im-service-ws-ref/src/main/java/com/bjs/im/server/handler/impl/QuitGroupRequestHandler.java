package com.bjs.im.server.handler.impl;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.request.QuitGroupRequestPacket;
import com.bjs.im.protocol.response.QuitGroupResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;

@IMRequest
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends AbstractHandler<QuitGroupRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) {
        // 获取群组
        String groupId = requestPacket.getGroupId();
        // 当前用户Channel
        Channel channel = ctx.channel();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        if (null != channelGroup) {
            if (channelGroup.contains(channel)) {
                UserSession userSession = SessionUtil.getUserSession(channel);
                channelGroup.remove(channel);
                responsePacket.setGroupName(channelGroup.name());
                responsePacket.setSuccess(true);
                responsePacket.setUserSession(userSession);
                // TODO 暂时通知给其他所有客户端,不做定向处理
                writeAndFlush(channelGroup, responsePacket);

            } else {
                // 不在此群聊中
                responsePacket.setSuccess(false);
                responsePacket.setErrMsg("不在此群聊中");
            }
        } else {
            // 无此群聊
            responsePacket.setSuccess(false);
            responsePacket.setErrMsg("无此群聊");
        }
        writeAndFlush(channel, responsePacket);
    }
}
