package com.sygt.common.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sygt.common.enums.AnnoEnum;
import com.sygt.common.annotation.AutoStuff;

/**
 * Entity基类
 *
 * @author ruoyi
 */

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: Entity基类
 * @class: BaseEntity
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 创建者
     */
    @AutoStuff(annoType = AnnoEnum.CREATE_USER)
    private String createUserId;

    /**
     * 创建人名称
     */
    @AutoStuff(annoType = AnnoEnum.CREATE_NAME)
    private String createName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @AutoStuff(annoType = AnnoEnum.CREATE_TIME)
    private Date createTime;

    /**
     * 更新者
     */
    @AutoStuff(annoType = AnnoEnum.UPDATE_USER)
    private String updateUserId;

    /**
     * 更新人名称
     */
    @AutoStuff(annoType = AnnoEnum.UPDATE_NAME)
    private String updateName;


    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @AutoStuff(annoType = AnnoEnum.UPDATE_TIME)
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
