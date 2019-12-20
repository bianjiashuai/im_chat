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
 * @Description 群组用户表.
 * @Date 2019-12-18 10:48:38
 * @Author BianJiashuai
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_group_user")
public class GroupUser extends BaseEntity {

    private static final long serialVersionUID = -2332258975305705546L;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false) // 群组ID
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 用户ID
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
