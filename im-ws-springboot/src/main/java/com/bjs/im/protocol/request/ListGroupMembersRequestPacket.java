package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:06:01
 * @Author BianJiashuai
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId; // 群组ID

    @Override
    public int getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
