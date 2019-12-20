package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.MESSAGE_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 13:55:27
 * @Author BianJiashuai
 */
@Data
public class MessageRequestPacket extends Packet {
    private Long dstUserId;   // 目标（即接收消息）用户ID
    private String msg;         // 发送的消息

    @Override
    public int getCommand() {
        return MESSAGE_REQUEST;
    }
}
