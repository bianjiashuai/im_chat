package com.bjs.im.protocol.command;

/**
 * @Description
 * @Date 2019-12-03 13:41:25
 * @Author BianJiashuai
 */
public interface Command {

    /**
     * 登录请求.
     */
    int LOGIN_REQUEST = 1;

    /**
     * 登录响应.
     */
    int LOGIN_RESPONSE = 2;

    /**
     * 发送信息请求.
     */
    int MESSAGE_REQUEST = 3;

    /**
     * 发送信息响应.
     */
    int MESSAGE_RESPONSE = 4;

    /**
     * 退出请求.
     */
    int LOGOUT_REQUEST = 5;

    /**
     * 退出响应.
     */
    int LOGOUT_RESPONSE = 6;

    /**
     * 创建群组请求.
     */
    int CREATE_GROUP_REQUEST = 7;

    /**
     * 创建群组响应.
     */
    int CREATE_GROUP_RESPONSE = 8;

    /**
     * 获取群组用户列表请求.
     */
    int LIST_GROUP_MEMBERS_REQUEST = 9;

    /**
     * 获取群组用户列表响应.
     */
    int LIST_GROUP_MEMBERS_RESPONSE = 10;

    /**
     * 加入群组请求.
     */
    int JOIN_GROUP_REQUEST = 11;

    /**
     * 加入群组响应.
     */
    int JOIN_GROUP_RESPONSE = 12;

    /**
     * 退出群组请求.
     */
    int QUIT_GROUP_REQUEST = 13;

    /**
     * 退出群组响应.
     */
    int QUIT_GROUP_RESPONSE = 14;

    /**
     * 发送群消息请求.
     */
    int GROUP_MESSAGE_REQUEST = 15;

    /**
     * 发送群消息响应.
     */
    int GROUP_MESSAGE_RESPONSE = 16;

    /**
     * 心跳检测请求.
     */
    int HEARTBEAT_REQUEST = 17;

    /**
     * 心跳检测响应.
     */
    int HEARTBEAT_RESPONSE = 18;

    /**
     * 获取所有在线用户请求.
     */
    int GET_ALL_ONLINE_MEMBERS_REQUEST = 19;

    /**
     * 获取所有在线用户响应.
     */
    int GET_ALL_ONLINE_MEMBERS_RESPONSE = 20;

    /**
     * 用户上线通知.
     */
    int MEMBER_ONLINE_NOTIFY = 21;

    /**
     * 用户上线通知.
     */
    int MEMBER_OFFLINE_NOTIFY = 22;

    /**
     * 获取漫游消息请求.
     */
    int GET_OUTLINE_MSG_REQUEST = 23;

    /**
     * 获取漫游消息响应.
     */
    int GET_OUTLINE_MSG_RESPONSE = 24;
}
