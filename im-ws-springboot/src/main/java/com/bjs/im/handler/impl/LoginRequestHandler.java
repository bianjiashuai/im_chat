package com.bjs.im.handler.impl;

import com.bjs.im.entity.User;
import com.bjs.im.handler.AbstractHandler;
import com.bjs.im.handler.annotation.IMRequest;
import com.bjs.im.protocol.request.LoginRequestPacket;
import com.bjs.im.protocol.response.LoginResponsePacket;
import com.bjs.im.util.IMUtil;
import com.bjs.im.util.NotifyUtil;
import com.bjs.im.util.RedisUtil;
import com.bjs.im.util.SessionUtil;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Objects;

@IMRequest
@ChannelHandler.Sharable
public class LoginRequestHandler extends AbstractHandler<LoginRequestPacket> {

    @Override
    protected void channelRead(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        User user = getUserSession(loginRequestPacket);
        if (null != user) {
            responsePacket.setSuccess(true);
            responsePacket.setUser(user);
            // 通知其他在线用户有新用户上线
            NotifyUtil.memberOnlineNotify(user);

            System.out.println(IMUtil.getDateStr() + " : [" + user.info() + "]登录成功");
            /**
             * 绑定新的channel
             * TODO 1、原有channel与用户关系会被新的channel覆盖, 但是原有channel仍是活跃状态但无人使用
             * TODO 2、用户离线后上线channel对象已经不是原有对象, 那么应该替换该用户所在群组的channel, 否则无法接收消息
             */
            SessionUtil.bindUser(user, ctx.channel());
        } else {
            responsePacket.setErrMsg("帐号密码校验失败");
            responsePacket.setSuccess(false);
            System.out.println(IMUtil.getDateStr() + " : [" + loginRequestPacket.getUserName() + "]登录失败");
        }

        writeAndFlush(ctx, responsePacket);
    }

    private User getUserSession(LoginRequestPacket loginRequestPacket) {
        String userName = loginRequestPacket.getUserName();
        User user = RedisUtil.getUserSession(userName, true);
        if (!Objects.isNull(user)) {
            if (user.getPassword().equals(loginRequestPacket.getPassword())) {
                user.setPassword(null);
                return user;
            }
        }
        return null;
    }
}
