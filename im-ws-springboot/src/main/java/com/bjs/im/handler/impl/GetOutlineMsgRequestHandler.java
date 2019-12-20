package com.bjs.im.handler.impl;

import com.alibaba.fastjson.JSON;
import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.GetOutlineMsgRequestPacket;
import com.bjs.im.protocol.response.GetOutlineMsgResponsePacket;
import com.bjs.im.protocol.response.MessageResponsePacket;
import com.bjs.im.util.RedisUtil;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

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
public class GetOutlineMsgRequestHandler extends AbstractHandler<GetOutlineMsgRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, GetOutlineMsgRequestPacket packet) {
        GetOutlineMsgResponsePacket responsePacket = new GetOutlineMsgResponsePacket();
        User user = SessionUtil.getUser(ctx.channel());
        Long userId = user.getId();
        Map<String, String> outlineMsg = RedisUtil.readOutlineMsg(String.valueOf(userId));
        responsePacket.setSuccess(true);
        if (!outlineMsg.isEmpty()) {
            responsePacket.setHasData(true);
            List<GetOutlineMsgResponsePacket.UserMsg> userMsgList = outlineMsg.entrySet().stream().map(entry -> {
                GetOutlineMsgResponsePacket.UserMsg userMsg = responsePacket.new UserMsg();
                userMsg.setSrcUser(RedisUtil.getUserSession(entry.getKey()));
                userMsg.setMsgList(JSON.parseArray(entry.getValue(), MessageResponsePacket.class));
                return userMsg;
            }).collect(Collectors.toList());
            responsePacket.setUserMsgList(userMsgList);
        }
        writeAndFlush(ctx, responsePacket).addListener(future -> {
            if (future.isDone() && responsePacket.isHasData()) {
                RedisUtil.deleteOutlineMsg(String.valueOf(userId));
            }
        });
    }
}
