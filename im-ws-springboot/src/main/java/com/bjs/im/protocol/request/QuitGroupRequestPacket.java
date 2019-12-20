package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.QUIT_GROUP_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:02:35
 * @Author BianJiashuai
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId; // 群组ID

    @Override
    public int getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
