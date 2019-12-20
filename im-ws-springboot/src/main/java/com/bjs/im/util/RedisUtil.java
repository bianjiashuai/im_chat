package com.bjs.im.util;

import com.alibaba.fastjson.JSON;
import com.bjs.im.entity.User;
import com.bjs.im.protocol.response.MessageResponsePacket;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RedisUtil {

    public static final String OUTLINE_MSG_PREFIX = "outline_msg:";
    public static final String IM_WS_PREFIX = "im_ws:";

    public static final Jedis JEDIS = new Jedis();

    private RedisUtil() {
    }


    /**
     * 获取用户信息.
     *
     * @param userParam 用户ID或用户名或手机号
     * @return
     */
    public static User getUserSession(String userParam) {
        return getUserSession(userParam, false);
    }

    /**
     * 获取用户信息.
     *
     * @param userParam   用户ID或用户名
     * @param hasPassword 是否包含密码
     * @return
     */
    public static User getUserSession(String userParam, boolean hasPassword) {
        User user = JSON.parseObject(JEDIS.get(IM_WS_PREFIX + userParam), User.class);
        if (!hasPassword) {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 存储漫游消息.
     *
     * @param dstUserId 接收消息的用户ID
     * @param packet    消息对象
     */
    public static void writeOutlineMsg(String dstUserId, MessageResponsePacket packet) {
        String srcUserId = String.valueOf(packet.getSrcUserId());
        String msgData = readOutlineMsg(dstUserId, srcUserId);
        List<MessageResponsePacket> packets;
        if (Objects.isNull(msgData)) {
            packets = new ArrayList<>();
        } else {
            packets = JSON.parseArray(msgData, MessageResponsePacket.class);
        }
        packets.add(packet);
        JEDIS.hset(OUTLINE_MSG_PREFIX + dstUserId, srcUserId, JSON.toJSONString(packets));
    }

    /**
     * 获取指定用户的所有漫游消息.
     *
     * @param userId 接收消息的用户ID
     * @return
     */
    public static Map<String, String> readOutlineMsg(String userId) {
        return JEDIS.hgetAll(OUTLINE_MSG_PREFIX + userId);
    }

    /**
     * 获取当前用户的漫游消息中指定用户发送的
     *
     * @param dstUserId 接收消息的用户ID
     * @param srcUserId 发送消息的用户ID
     * @return
     */
    public static String readOutlineMsg(String dstUserId, String srcUserId) {
        return JEDIS.hget(OUTLINE_MSG_PREFIX + dstUserId, srcUserId);
    }

    /**
     * 删除指定用户的漫游消息.
     *
     * @param userId 接收消息的用户ID
     */
    public static void deleteOutlineMsg(String userId, String... fields) {
        if (fields.length > 0) {
            JEDIS.hdel(OUTLINE_MSG_PREFIX + userId, fields);
        } else {
            JEDIS.del(OUTLINE_MSG_PREFIX + userId);
        }
    }
}
