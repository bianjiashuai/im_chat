package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.LOGIN_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 13:52:41
 * @Author BianJiashuai
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userName;
    private String password;

    @Override
    public int getCommand() {
        return LOGIN_REQUEST;
    }
}
