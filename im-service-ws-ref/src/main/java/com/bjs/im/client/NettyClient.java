package com.bjs.im.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjs.im.client.console.ConsoleCommandManager;
import com.bjs.im.protocol.request.HeartbeatRequestPacket;
import com.bjs.im.util.IMUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2019-12-04 17:08:42
 * @Author BianJiashuai
 */
public class NettyClient {

    public static final int HEARTBEAT_INTERVAL = 5;
    private static WebSocketClient client;

    public static void main(String[] args) throws Exception {
        URI uri = new URI("ws://localhost:8000");
        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println(IMUtil.getDateStr() + "连接打开");
                startHeartbeatThread(client);
                startConsoleThread(client);
            }

            @Override
            public void onMessage(String message) {
                JSONObject retData = JSON.parseObject(message);
                if (18 != ((int)retData.get("command"))) {
                    System.out.println("接收消息" + message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println(IMUtil.getDateStr() + "连接关闭");
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("发生错误");
                ex.printStackTrace();
            }
        };

        client.connect();
    }

    private static void startHeartbeatThread(WebSocketClient client) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            if (client.isOpen()) {
                client.send(JSON.toJSONString(new HeartbeatRequestPacket()));
            }
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    private static void startConsoleThread(WebSocketClient client) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                consoleCommandManager.exec(scanner, client);
            }
        }).start();
    }
}
