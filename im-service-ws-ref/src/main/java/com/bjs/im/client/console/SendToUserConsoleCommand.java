package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.MessageRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        requestPacket.setDstUserId(IMUtil.scanner(scanner, "对方用户Id"));
        requestPacket.setMsg(IMUtil.scanner(scanner, "消息内容"));
        client.send(JSON.toJSONString(requestPacket));
    }
}
