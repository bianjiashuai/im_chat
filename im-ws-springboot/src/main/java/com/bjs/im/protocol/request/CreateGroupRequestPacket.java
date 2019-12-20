package com.bjs.im.protocol.request;

import static com.bjs.im.protocol.command.Command.CREATE_GROUP_REQUEST;

import com.bjs.im.protocol.Packet;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Date 2019-12-03 13:58:05
 * @Author BianJiashuai
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<Long> userIds;           // 加入群组的用户ID集合
    private String groupName = "群聊";       // 群组名称

    @Override
    public int getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
