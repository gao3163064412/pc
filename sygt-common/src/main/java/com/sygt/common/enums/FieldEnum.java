package com.sygt.common.enums;

import java.sql.Timestamp;
import java.util.Date;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： 操作状态
 * @fileName: 限制注入的类型
 * @class: FieldEnum
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public enum  FieldEnum {
    STRING(String.class),
    LONG(Long.class),
    DATE(Date .class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    TIMESTAMP(Timestamp .class);

    FieldEnum(Class fieldType) {
        this.fieldType = fieldType;
    }

    private Class fieldType;

    public Class getFieldType() {
        return fieldType;
    }

}
