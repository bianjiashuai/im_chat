package com.bjs.im.util;

import com.bjs.im.entity.User;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description Session工具类
 * @Date 2019-12-03 16:04:29
 * @Author BianJiashuai
 */
@Slf4j
public class SessionUtil {

    private static final AttributeKey<User> USER_KEY = AttributeKey.newInstance("user");

    public static final Map<Long, Channel> userIdChannelMap = new ConcurrentHashMap<>();         // 用户Channel集合
    public static final Map<Long, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<>();   // 群组Channel集合

    /**
     * 绑定用户.
     *
     * @param user    用户对象
     * @param channel
     */
    public static void bindUser(User user, Channel channel) {
        userIdChannelMap.put(user.getId(), channel);
        channel.attr(USER_KEY).set(user);
    }

    /**
     * 解绑用户Session.
     *
     * @param channel
     */
    public static void unBindUser(Channel channel) {
        if (hasLogin(channel)) {
            User user = getUser(channel);
            userIdChannelMap.remove(user.getId());
            channel.attr(USER_KEY).set(null);
            log.info("[{}]退出登录", user.info());
            // 通知其他用户离线
            NotifyUtil.memberOfflineNotify(user);
        }
    }

    /**
     * 是否已经登录.
     *
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        return getUser(channel) != null;
    }

    /**
     * 获取用户Session.
     *
     * @param channel
     * @return
     */
    public static User getUser(Channel channel) {
        return channel.attr(USER_KEY).get();
    }

    /**
     * 获取用户Channel.
     *
     * @param userId
     * @return
     */
    public static Channel getChannel(Long userId) {
        return userIdChannelMap.get(userId);
    }

    /**
     * 绑定群组Channel.
     *
     * @param groupId
     * @param channelGroup
     */
    public static void bindChannelGroup(Long groupId, ChannelGroup channelGroup) {
        groupIdChannelMap.put(groupId, channelGroup);
    }

    /**
     * 获取群组Channel.
     *
     * @param groupId
     * @return
     */
    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelMap.get(groupId);
    }
}
