package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.QuitGroupRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        requestPacket.setGroupId(IMUtil.scanner(scanner, "群聊Id"));
        client.send(JSON.toJSONString(requestPacket));
    }
}
