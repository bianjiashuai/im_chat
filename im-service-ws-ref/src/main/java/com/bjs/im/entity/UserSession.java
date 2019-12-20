package com.bjs.im.entity;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Date 2019-12-03 12:50:26
 * @Author BianJiashuai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private String userId;          // 用户ID
    private String userName;        // 用户名称
    private String password;        // 用户密码
    private String nickName;        // 昵称

    public String info() {
        return nickName + ":" + userId + ":" + userName;
    }
}
