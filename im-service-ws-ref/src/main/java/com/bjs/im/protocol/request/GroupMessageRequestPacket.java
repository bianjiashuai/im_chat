package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.GROUP_MESSAGE_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:04:11
 * @Author BianJiashuai
 */
@Data
public class GroupMessageRequestPacket extends Packet {

    private String groupId; // 群组ID
    private String msg;     // 群聊消息

    @Override
    public int getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
