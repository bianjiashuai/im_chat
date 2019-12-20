package com.bjs.im.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Description 好友关系表.
 * @Date 2019-12-18 16:40:23
 * @Author BianJiashuai
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_friend_relation")
public class FriendRelation extends BaseEntity {

    private static final long serialVersionUID = -2332258975305705548L;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 用户ID
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false) // 好友ID
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User friend;
}
