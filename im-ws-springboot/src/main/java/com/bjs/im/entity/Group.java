package com.bjs.im.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Description 群组表.
 * @Date 2019-12-18 10:48:27
 * @Author BianJiashuai
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_group")
public class Group extends BaseEntity {

    private static final long serialVersionUID = -2332258975305705547L;

    @Column(name = "name", columnDefinition = "VARCHAR(100) COMMENT '群聊名称'")
    private String name;
    @Column(name = "add_time", columnDefinition = "VARCHAR(25) COMMENT '添加时间'")
    private String addTime;
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false) // 管理员ID
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User manager;
}
