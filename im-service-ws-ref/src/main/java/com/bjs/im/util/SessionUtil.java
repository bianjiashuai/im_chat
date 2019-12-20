package com.bjs.im.util;

import com.bjs.im.entity.UserSession;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description Session工具类
 * @Date 2019-12-03 16:04:29
 * @Author BianJiashuai
 */
public class SessionUtil {

    private static final AttributeKey<UserSession> USER_KEY = AttributeKey.newInstance("user");

    public static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();         // 用户Channel集合
    public static final Map<String, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<>();   // 群组Channel集合

    /**
     * 绑定用户Session.
     * @param userSession 用户Session对象
     * @param channel
     */
    public static void bindUser(UserSession userSession, Channel channel) {
        userIdChannelMap.put(userSession.getUserId(), channel);
        channel.attr(USER_KEY).set(userSession);
    }

    /**
     * 解绑用户Session.
     * @param channel
     */
    public static void unBindUser(Channel channel) {
        if (hasLogin(channel)) {
            UserSession userSession = getUserSession(channel);
            userIdChannelMap.remove(userSession.getUserId());
            channel.attr(USER_KEY).set(null);
            System.out.println(userSession.info() + " 退出登录");
            // 通知其他用户离线
            NotifyUtil.memberOfflineNotify(userSession);
        }
    }

    /**
     * 是否已经登录.
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        return getUserSession(channel) != null;
    }

    /**
     * 获取用户Session.
     * @param channel
     * @return
     */
    public static UserSession getUserSession(Channel channel) {
        return channel.attr(USER_KEY).get();
    }

    /**
     * 获取用户Channel.
     * @param userId
     * @return
     */
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    /**
     * 绑定群组Channel.
     * @param groupId
     * @param channelGroup
     */
    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelMap.put(groupId, channelGroup);
    }

    /**
     * 获取群组Channel.
     * @param groupId
     * @return
     */
    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelMap.get(groupId);
    }
}
