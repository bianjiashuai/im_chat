package com.bjs.im.handler.impl;

import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.GetAllOnlineMembersRequestPacket;
import com.bjs.im.protocol.response.GetAllOnlineMembersResponsePacket;
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
        Map<Long, Channel> userIdChannelMap = new HashMap<>(SessionUtil.userIdChannelMap);
        Collection<Channel> channels = userIdChannelMap.values();
        channels.remove(ctx.channel());
        GetAllOnlineMembersResponsePacket responsePacket = new GetAllOnlineMembersResponsePacket();
        if (!channels.isEmpty()) {
            List<User> users = channels.stream().map(SessionUtil::getUser).collect(Collectors.toList());
            responsePacket.setUsers(users);
        }
        responsePacket.setSuccess(true);
        writeAndFlush(ctx, responsePacket);
    }
}
