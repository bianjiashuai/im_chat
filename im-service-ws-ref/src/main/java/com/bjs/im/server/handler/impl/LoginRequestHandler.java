package com.bjs.im.server.handler.impl;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.request.LoginRequestPacket;
import com.bjs.im.protocol.response.LoginResponsePacket;
import com.bjs.im.server.handler.AbstractHandler;
import com.bjs.im.server.handler.annotation.IMRequest;
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
        UserSession userSession = getUserSession(loginRequestPacket);
        if (null != userSession) {
            responsePacket.setSuccess(true);
            responsePacket.setUserSession(userSession);
            // 通知其他在线用户有新用户上线
            NotifyUtil.memberOnlineNotify(userSession);

            System.out.println(IMUtil.getDateStr() + " : [" + userSession.info() + "]登录成功");
            /**
             * 绑定新的channel
             * TODO 1、原有channel与用户关系会被新的channel覆盖, 但是原有channel仍是活跃状态但无人使用
             * TODO 2、用户离线后上线channel对象已经不是原有对象, 那么应该替换该用户所在群组的channel, 否则无法接收消息
             */
            SessionUtil.bindUser(userSession, ctx.channel());
        } else {
            responsePacket.setErrMsg("帐号密码校验失败");
            responsePacket.setSuccess(false);
            System.out.println(IMUtil.getDateStr() + " : [" + loginRequestPacket.getUserName() + "]登录失败");
        }

        writeAndFlush(ctx, responsePacket);
    }

    private UserSession getUserSession(LoginRequestPacket loginRequestPacket) {
        String userName = loginRequestPacket.getUserName();
        UserSession userSession = RedisUtil.getUserSession(userName, true);
        if (!Objects.isNull(userSession)) {
            if (userSession.getPassword().equals(loginRequestPacket.getPassword())) {
                userSession.setPassword(null);
                return userSession;
            }
        }
        return null;
    }
}
