package com.bjs.im.protocol.response;

import static com.bjs.im.protocol.command.Command.GET_ALL_ONLINE_MEMBERS_RESPONSE;

import com.bjs.im.entity.User;
import com.bjs.im.protocol.Packet;
import com.bjs.im.util.IMUtil;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Date 2019-12-05 11:50:41
 * @Author BianJiashuai
 */
@Data
public class GetAllOnlineMembersResponsePacket extends Packet {

    private boolean success;
    private String time = IMUtil.getDateStr();
    private List<User> users = new ArrayList<>();

    @Override
    public int getCommand() {
        return GET_ALL_ONLINE_MEMBERS_RESPONSE;
    }
}
