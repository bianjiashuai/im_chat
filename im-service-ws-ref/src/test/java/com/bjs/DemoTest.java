package com.bjs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description
 * @Date 2019-12-04 16:32:56
 * @Author BianJiashuai
 */
public class DemoTest<T> {
    public Class<T> getTClass() {
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        return (Class<T>) types[0];
    }

    public void test() {
        System.out.println(getTClass());
    }

}
