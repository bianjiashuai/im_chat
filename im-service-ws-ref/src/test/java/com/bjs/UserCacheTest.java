package com.bjs;

import static com.bjs.im.util.RedisUtil.IM_WS_PREFIX;

import com.alibaba.fastjson.JSON;
import com.bjs.im.entity.UserSession;
import com.bjs.im.util.IMUtil;
import com.bjs.im.util.RedisUtil;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

public class UserCacheTest {

    public static final Jedis jedis = RedisUtil.JEDIS;

    @Test
    public void cacheUserTest() {
        jedis.flushDB();

        List<UserSession> userSessionCache = Arrays.asList(
                new UserSession(IMUtil.randomId(), "zs", "111111", "张三"),
                new UserSession(IMUtil.randomId(), "ls", "111111", "李四"),
                new UserSession(IMUtil.randomId(), "ww", "111111", "王五"),
                new UserSession(IMUtil.randomId(), "zl", "111111", "赵六"),
                new UserSession(IMUtil.randomId(), "bjs", "111111", "边佳帅"),
                new UserSession(IMUtil.randomId(), "ssx", "111111", "苏圣翔"),
                new UserSession(IMUtil.randomId(), "lhw", "111111", "刘虹伟"),
                new UserSession(IMUtil.randomId(), "xbb", "666666", "小宝贝")
        );
        userSessionCache.stream().forEach(userSession -> {
            String userSessionString = JSON.toJSONString(userSession);
            jedis.set(IM_WS_PREFIX + userSession.getUserId(), userSessionString);
            jedis.set(IM_WS_PREFIX + userSession.getUserName(), userSessionString);
        });

        System.out.println("缓存完成");
    }
}
