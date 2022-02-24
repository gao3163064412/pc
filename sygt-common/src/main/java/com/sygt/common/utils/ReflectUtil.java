package com.sygt.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 反射工具类
 * @class: ReflectUtil
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/

public class ReflectUtil {

    private ReflectUtil() {

    }

    public static Field[] getAllFields(Object object) {
        List<Field> fieldList = new ArrayList<>();
        if(StringUtils.isNotNull(object)){
            Class clazz = object.getClass();

            while (clazz != null) {
                fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                clazz = clazz.getSuperclass();
            }
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    public static Field[] getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

}
