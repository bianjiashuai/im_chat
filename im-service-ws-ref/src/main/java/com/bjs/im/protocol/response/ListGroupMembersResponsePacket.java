package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Date 2019-12-03 14:40:45
 * @Author BianJiashuai
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;             // 群组ID
    private String groupName;           // 群组名称
    private List<UserSession> users;    // 群组用户集合
    private boolean success;            // 是否成功
    private String errMsg;              // 错误信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
