package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:24:48
 * @Author BianJiashuai
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String srcUserId;   // 源（发送消息）用户ID
    private String srcUserName; // 源（发送消息）用户名称
    private String srcNickName; // 源（发送消息）用户昵称
    private String groupId;     // 群组ID
    private String groupName;   // 群组名称
    private String msg;         // 消息内容
    private boolean success;    // 是否成功
    private String errMsg;      // 错误信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
