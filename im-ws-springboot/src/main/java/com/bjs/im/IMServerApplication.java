package com.bjs.im;

import com.bjs.im.config.NettyConfig;
import com.bjs.im.server.NettyServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Date 2019-12-18 10:37:31
 * @Author BianJiashuai
 */
@SpringBootApplication
public class IMServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }

    @Autowired
    NettyConfig nettyConfig;

    @Override
    public void run(String... args) throws Exception {
        NettyServer.run(nettyConfig, args);
    }
}
