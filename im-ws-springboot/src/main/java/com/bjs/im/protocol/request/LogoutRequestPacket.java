package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.LOGOUT_REQUEST;

import com.bjs.im.protocol.Packet;

/**
 * @Description
 * @Date 2019-12-03 13:54:24
 * @Author BianJiashuai
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public int getCommand() {
        return LOGOUT_REQUEST;
    }
}
