package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Date 2019-12-03 14:18:29
 * @Author BianJiashuai
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private Long groupId;         // 群组ID
    private String groupName;       // 群组名称
    private boolean success;        // 是否成功
    private List<String> accounts;  // 群组成员
    private String errMsg;          // 错误信息
    private String time = IMUtil.getDateStr();

    @Override
    public int getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
