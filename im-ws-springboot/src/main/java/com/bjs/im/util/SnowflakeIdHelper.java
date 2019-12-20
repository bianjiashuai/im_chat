package com.bjs.im.util;

/**
 * @Description
 * @Date 2019-12-18 16:14:43
 * @Author BianJiashuai
 */
public class SnowflakeIdHelper {

    public static final SnowflakeIdWorker SNOWFLAKE_ID_WORKER = new SnowflakeIdWorker(0, 0);

    public static Long getId() {
        return SNOWFLAKE_ID_WORKER.nextId();
    }
}
