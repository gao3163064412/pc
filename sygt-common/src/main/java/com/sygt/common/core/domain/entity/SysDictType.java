package com.sygt.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.sygt.common.annotation.Excel;
import com.sygt.common.annotation.Excel.ColumnType;
import com.sygt.common.core.domain.BaseEntity;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 字典类型表 sys_dict_type
 * @class: SysDictType
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictId", getDictId())
                .append("dictName", getDictName())
                .append("dictType", getDictType())
                .append("status", getStatus())
                .append("createUserId", getCreateUserId())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateUserId", getUpdateUserId())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
