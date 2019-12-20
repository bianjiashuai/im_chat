package com.bjs.im.server.handler.impl;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.request.GetAllOnlineMembersRequestPacket;
import com.bjs.im.protocol.response.GetAllOnlineMembersResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2019-12-05 11:43:23
 * @Author BianJiashuai
 */
@IMRequest
@ChannelHandler.Sharable
public class GetAllOnlineMembersRequestHandler extends AbstractHandler<GetAllOnlineMembersRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, GetAllOnlineMembersRequestPacket packet) {
        Map<String, Channel> userIdChannelMap = new HashMap<>(SessionUtil.userIdChannelMap);
        Collection<Channel> channels = userIdChannelMap.values();
        channels.remove(ctx.channel());
        GetAllOnlineMembersResponsePacket responsePacket = new GetAllOnlineMembersResponsePacket();
        if (!channels.isEmpty()) {
            List<UserSession> userSessions = channels.stream().map(SessionUtil::getUserSession).collect(Collectors.toList());
            responsePacket.setUserSessions(userSessions);
        }
        responsePacket.setSuccess(true);
        writeAndFlush(ctx, responsePacket);
    }
}
