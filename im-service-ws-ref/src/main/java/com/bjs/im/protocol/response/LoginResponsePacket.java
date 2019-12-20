package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.LOGIN_RESPONSE;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:09:43
 * @Author BianJiashuai
 */
@Data
public class LoginResponsePacket extends Packet {

    private UserSession userSession;    // 用户信息
    private boolean success;            // 是否成功
    private String errMsg;              // 错误信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return LOGIN_RESPONSE;
    }
}
