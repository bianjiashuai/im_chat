package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.JOIN_GROUP_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:01:26
 * @Author BianJiashuai
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId; // 群组ID

    @Override
    public int getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
