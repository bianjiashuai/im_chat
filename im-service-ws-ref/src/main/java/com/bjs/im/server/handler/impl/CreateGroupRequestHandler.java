package com.bjs.im.server.handler.impl;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.request.CreateGroupRequestPacket;
import com.bjs.im.protocol.response.CreateGroupResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;
import com.bjs.im.util.IMUtil;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

@IMRequest
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends AbstractHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) {
        List<String> userIdList = createGroupRequestPacket.getUserIds();

        Channel channel = ctx.channel();
        UserSession userSession = SessionUtil.getUserSession(channel);

        List<String> userNameList = new ArrayList<>();
        userNameList.add(userSession.getUserName());

        // 构建群组名称, 默认为: 群聊
        String groupName = createGroupRequestPacket.getGroupName();

        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(groupName, ctx.executor());
        channelGroup.add(channel);    // 将自己加入群组

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel dstChannel = SessionUtil.getChannel(userId);
            if (dstChannel != null) {
                // 剔除了不存在或者不在线的的用户
                channelGroup.add(dstChannel);
                userNameList.add(SessionUtil.getUserSession(dstChannel).getUserName());
            }
        }

        // TODO 后面处理创建失败的情况

        // 3. 创建群聊创建结果的响应
        String groupId = IMUtil.randomId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setGroupName(groupName);
        responsePacket.setUserNames(userNameList);

        // 4. 给每个客户端发送拉群通知
        writeAndFlush(channelGroup, responsePacket);

        System.out.print("群创建成功，id 为 " + responsePacket.getGroupId() + ", ");
        System.out.print("名称为 " + responsePacket.getGroupName() + ", ");
        System.out.println("群里面有：" + responsePacket.getUserNames());

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
