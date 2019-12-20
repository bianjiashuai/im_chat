package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.MESSAGE_RESPONSE;

import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:16:08
 * @Author BianJiashuai
 */
@Data
public class MessageResponsePacket extends Packet {

    private Long srcUserId;   // 源（发送消息）用户ID
    private String srcAccount; // 源（发送消息）用户名称
    private String srcNickName; // 源（发送消息）用户昵称
    private String msg;         // 消息内容
    private boolean success;    // 是否成功
    private String errMsg;      // 错误信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return MESSAGE_RESPONSE;
    }
}
