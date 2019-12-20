package com.bjs.im.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Description 读取yml配置文件中的信息
 * @Date 2019-12-19 16:50:48
 * @Author BianJiashuai
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {
    private int port = 8000;
}
