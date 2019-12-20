package com.bjs.im.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description 用户表.
 * @Date 2019-12-18 10:48:19
 * @Author BianJiashuai
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -2332258975305705549L;

    @Column(name = "account", columnDefinition = "VARCHAR(50) COMMENT '帐号'")
    private String account;
    @Column(name = "password", columnDefinition = "VARCHAR(255) COMMENT '密码'")
    private String password;
    @Column(name = "mobile_num", columnDefinition = "VARCHAR(25) COMMENT '手机号码'")
    private String mobileNum;
    @Column(name = "add_time", columnDefinition = "VARCHAR(25) COMMENT '添加时间'")
    private String addTime;
    @Column(name = "nick_name", columnDefinition = "VARCHAR(50) COMMENT '昵称'")
    private String nickName;
    @Column(name = "avatar_url", columnDefinition = "VARCHAR(255) COMMENT '头像地址'")
    private String avatarUrl;
    @Column(name = "area", columnDefinition = "VARCHAR(50) COMMENT '地区'")
    private String area;


    public String info() {
        return nickName + ":" + id + ":" + account;
    }
}
