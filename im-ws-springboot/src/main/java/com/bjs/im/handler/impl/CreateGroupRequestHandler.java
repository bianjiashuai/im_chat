package com.bjs.im.handler.impl;

import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.CreateGroupRequestPacket;
import com.bjs.im.protocol.response.CreateGroupResponsePacket;
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
        List<Long> userIdList = createGroupRequestPacket.getUserIds();

        Channel channel = ctx.channel();
        User user = SessionUtil.getUser(channel);

        List<String> accountList = new ArrayList<>();
        accountList.add(user.getAccount());

        // 构建群组名称, 默认为: 群聊
        String groupName = createGroupRequestPacket.getGroupName();

        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(groupName, ctx.executor());
        channelGroup.add(channel);    // 将自己加入群组

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (Long userId : userIdList) {
            Channel dstChannel = SessionUtil.getChannel(userId);
            if (dstChannel != null) {
                // 剔除了不存在或者不在线的的用户
                channelGroup.add(dstChannel);
                accountList.add(SessionUtil.getUser(dstChannel).getAccount());
            }
        }

        // TODO 后面处理创建失败的情况

        // 3. 创建群聊创建结果的响应
        Long groupId = IMUtil.randomId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setGroupName(groupName);
        responsePacket.setAccounts(accountList);

        // 4. 给每个客户端发送拉群通知
        writeAndFlush(channelGroup, responsePacket);

        System.out.print("群创建成功，id 为 " + responsePacket.getGroupId() + ", ");
        System.out.print("名称为 " + responsePacket.getGroupName() + ", ");
        System.out.println("群里面有：" + responsePacket.getAccounts());

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
