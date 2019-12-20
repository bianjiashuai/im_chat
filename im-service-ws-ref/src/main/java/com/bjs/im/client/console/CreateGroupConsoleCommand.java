package com.bjs.im.client.console;

import com.alibaba.fastjson.JSON;
import com.bjs.im.protocol.request.CreateGroupRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;

import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    public static final String USER_ID_SPLIT = ",";
    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        CreateGroupRequestPacket requestPacket = new CreateGroupRequestPacket();
        String userIds = IMUtil.scanner(scanner, "用户Id,用英文逗号分隔");
        requestPacket.setUserIds(Arrays.asList(userIds.split(USER_ID_SPLIT)));
        String groupName = IMUtil.scanner(scanner, "群聊名称(非必输)", true);
        if (!"".equals(groupName)) {
            requestPacket.setGroupName(groupName);
        }
        client.send(JSON.toJSONString(requestPacket));
    }
}
