package com.bjs.im.client.console;

import org.java_websocket.client.WebSocketClient;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台指令接口.
 */
public interface ConsoleCommand {
    /**
     * 执行相关指令.
     * @param scanner
     * @param client
     */
    void exec(Scanner scanner, WebSocketClient client);
}
