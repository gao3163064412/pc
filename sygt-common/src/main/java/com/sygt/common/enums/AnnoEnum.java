package com.sygt.common.enums;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： 操作状态
 * @fileName: 可供选择的归属类型
 * @class: AnnoEnum
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public enum AnnoEnum {
    ID("ID"),
    CREATE_USER("createUserId"),
    CREATE_NAME("createName"),
    CREATE_TIME("createTime"),
    UPDATE_USER("updateUserId"),
    UPDATE_NAME("updateName"),
    UPDATE_TIME("updateTime"),
    DEL_FLAG("delFlag"),
    VERSION("version");


    AnnoEnum(String annoType) {
        this.annoType = annoType;
    }

    private String annoType;

    public String getAnnoType() {
        return annoType;
    }

}
