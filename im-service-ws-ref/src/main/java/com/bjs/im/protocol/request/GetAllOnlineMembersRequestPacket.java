package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.GET_ALL_ONLINE_MEMBERS_REQUEST;

import com.bjs.im.protocol.Packet;

/**
 * @Description
 * @Date 2019-12-05 11:43:57
 * @Author BianJiashuai
 */
public class GetAllOnlineMembersRequestPacket extends Packet {

    @Override
    public int getCommand() {
        return GET_ALL_ONLINE_MEMBERS_REQUEST;
    }
}
