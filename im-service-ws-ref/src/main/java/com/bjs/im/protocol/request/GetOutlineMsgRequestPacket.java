package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.GET_OUTLINE_MSG_REQUEST;

import com.bjs.im.protocol.Packet;

/**
 * @Description
 * @Date 2019-12-05 11:43:57
 * @Author BianJiashuai
 */
public class GetOutlineMsgRequestPacket extends Packet {

    @Override
    public int getCommand() {
        return GET_OUTLINE_MSG_REQUEST;
    }
}
