package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.LogoutRequestPacket;

import org.java_websocket.client.WebSocketClient;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        LogoutRequestPacket requestPacket = new LogoutRequestPacket();
        client.send(JSON.toJSONString(requestPacket));
    }
}
