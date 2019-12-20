package com.bjs.im.generator;

import com.bjs.im.util.SnowflakeIdHelper;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description
 * @Date 2019-12-18 16:12:10
 * @Author BianJiashuai
 */
public class CustomIDGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Long id = SnowflakeIdHelper.getId();
        if (Objects.isNull(id)) {
            return super.generate(session, obj);
        }
        return id;
    }
}
