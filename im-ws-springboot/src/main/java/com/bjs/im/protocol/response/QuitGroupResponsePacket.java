package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.QUIT_GROUP_RESPONSE;

import com.bjs.im.entity.User;
import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

/**
 * @Description
 * @Date 2019-12-03 14:23:47
 * @Author BianJiashuai
 */
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;             // 群组ID
    private String groupName;           // 群组名称
    private boolean success;            // 是否成功
    private String errMsg;              // 错误信息
    private User user;                  // 用户信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
