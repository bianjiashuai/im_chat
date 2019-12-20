package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.HEARTBEAT_RESPONSE;

import com.bjs.im.protocol.Packet;

/**
 * @Description
 * @Date 2019-12-03 14:27:03
 * @Author BianJiashuai
 */
public class HeartbeatResponsePacket extends Packet {
    @Override
    public int getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
