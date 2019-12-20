package com.bjs.im.entity;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Description
 * @Date 2019-12-18 18:22:27
 * @Author BianJiashuai
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6874815211133329309L;

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.bjs.im.generator.CustomIDGenerator")
    protected Long id;
}
