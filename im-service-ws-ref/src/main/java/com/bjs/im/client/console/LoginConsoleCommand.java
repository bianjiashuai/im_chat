package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.LoginRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;

import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserName(IMUtil.scanner(scanner, "用户名"));
        requestPacket.setPassword(IMUtil.scanner(scanner, "密码"));

        // 发送数据包
        client.send(JSON.toJSONString(requestPacket));

        try {
            // 等待登录响应
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}
