package com.bjs.im.util;

import static com.bjs.im.protocol.command.Command.MEMBER_OFFLINE_NOTIFY;
import static com.bjs.im.protocol.command.Command.MEMBER_ONLINE_NOTIFY;

import com.alibaba.fastjson.JSON;
import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.notify.MemberOnOffNotifyPacket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Collection;
import java.util.Map;

/**
 * 通知工具类.
 */
public class NotifyUtil {

    /**
     * 用户上线通知.
     *
     * @param userSession
     */
    public static void memberOnlineNotify(UserSession userSession) {
        memberOnOffNotify(userSession, true);
    }

    /**
     * 用户下线通知.
     *
     * @param userSession
     */
    public static void memberOfflineNotify(UserSession userSession) {
        memberOnOffNotify(userSession, false);
    }

    /**
     * 用户通知.
     *
     * @param online
     */
    public static void memberOnOffNotify(UserSession userSession, boolean online) {
        Map<String, Channel> userIdChannelMap = SessionUtil.userIdChannelMap;
        Collection<Channel> channels = userIdChannelMap.values();
        if (!channels.isEmpty()) {
            // 当前有其他在线用户则通知
            MemberOnOffNotifyPacket notifyPacket = new MemberOnOffNotifyPacket();
            notifyPacket.setSuccess(true);
            notifyPacket.setUserSession(userSession);
            notifyPacket.setTime(IMUtil.getDateStr());
            notifyPacket.setCommand(online ? MEMBER_ONLINE_NOTIFY : MEMBER_OFFLINE_NOTIFY);
            channels.parallelStream().forEach(channel -> channel.writeAndFlush(
                    new TextWebSocketFrame(JSON.toJSONString(notifyPacket))));
        }
    }

    private NotifyUtil() {
    }
}
