package com.bjs.im.client.console;

import com.bjs.im.util.SessionUtil;

import org.java_websocket.client.WebSocketClient;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("login", new LoginConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, WebSocketClient client) {
        // 获取第一个指令
        String command = scanner.nextLine();
        if ("exit".equalsIgnoreCase(command)) {
            client.close();
            System.exit(0);
        } else {
            ConsoleCommand consoleCommand = consoleCommandMap.get(command);
            if (null == consoleCommand) {
                System.err.println("无效指令:[" + command + "],请重新输入！！！" +
                        "期待：login, logout, sendToUser, createGroup, joinGroup, quitGroup, listGroupMembers, sendToGroup");
            } else {
                consoleCommand.exec(scanner, client);
            }
        }
    }
}
