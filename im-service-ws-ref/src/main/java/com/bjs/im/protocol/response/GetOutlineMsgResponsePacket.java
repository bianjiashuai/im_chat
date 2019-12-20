package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.GET_OUTLINE_MSG_RESPONSE;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2019-12-05 11:50:41
 * @Author BianJiashuai
 */
@Data
public class GetOutlineMsgResponsePacket extends Packet {

    private boolean success;
    private String time = IMUtil.getDateStr();
    private boolean hasData = false;
    private List<UserMsg> userMsgList = new ArrayList<>();

    @Override
    public int getCommand() {
        return GET_OUTLINE_MSG_RESPONSE;
    }

    @Data
    public class UserMsg {
        private UserSession srcUser;
        private List<MessageResponsePacket> msgList;
    }
}


