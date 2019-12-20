package com.bjs.im.protocol.notify;

import com.bjs.im.entity.UserSession;
import com.bjs.im.protocol.Packet;

import lombok.Data;

/**
 * 用户上线离/线通知包装对象.
 */
@Data
public class MemberOnOffNotifyPacket extends Packet {

    private boolean success;
    private UserSession userSession;     // 上线用户信息
    private String time;                // 上线时间
}
