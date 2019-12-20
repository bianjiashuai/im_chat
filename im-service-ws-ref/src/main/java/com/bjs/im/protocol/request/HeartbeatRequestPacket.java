package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.HEARTBEAT_REQUEST;

import com.bjs.im.protocol.Packet;

/**
 * @Description
 * @Date 2019-12-03 14:09:00
 * @Author BianJiashuai
 */
public class HeartbeatRequestPacket extends Packet {
    @Override
    public int getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
