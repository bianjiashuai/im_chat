package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.GroupMessageRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupId(IMUtil.scanner(scanner, "群组Id"));
        requestPacket.setMsg(IMUtil.scanner(scanner, "群消息内容"));
        client.send(JSON.toJSONString(requestPacket));
    }
}
