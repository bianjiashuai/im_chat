package com.bjs.im.util;

import static com.bjs.im.protocol.command.Command.MEMBER_OFFLINE_NOTIFY;
import static com.bjs.im.protocol.command.Command.MEMBER_ONLINE_NOTIFY;

import com.alibaba.fastjson.JSON;
import com.bjs.im.entity.User;
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
     * @param user
     */
    public static void memberOnlineNotify(User user) {
        memberOnOffNotify(user, true);
    }

    /**
     * 用户下线通知.
     *
     * @param user
     */
    public static void memberOfflineNotify(User user) {
        memberOnOffNotify(user, false);
    }

    /**
     * 用户通知.
     *
     * @param online
     */
    public static void memberOnOffNotify(User user, boolean online) {
        Map<Long, Channel> userIdChannelMap = SessionUtil.userIdChannelMap;
        Collection<Channel> channels = userIdChannelMap.values();
        if (!channels.isEmpty()) {
            // 当前有其他在线用户则通知
            MemberOnOffNotifyPacket notifyPacket = new MemberOnOffNotifyPacket();
            notifyPacket.setSuccess(true);
            notifyPacket.setUser(user);
            notifyPacket.setTime(IMUtil.getDateStr());
            notifyPacket.setCommand(online ? MEMBER_ONLINE_NOTIFY : MEMBER_OFFLINE_NOTIFY);
            channels.parallelStream().forEach(channel -> channel.writeAndFlush(
                    new TextWebSocketFrame(JSON.toJSONString(notifyPacket))));
        }
    }

    private NotifyUtil() {
    }
}
