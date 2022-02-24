package com.sygt.system.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 同步的组织机构
 * @class: SysSyncOrg
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 **********************************************************************/

@Data
@Accessors
public class SysSyncOrg {

    /**
     * 所属单位ID
     */
    private Long orgAccountId;

    /**
     * ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门代码
     */
    private boolean enabled;

    /**
     * 排序号
     */
    private  Integer sortId;

    /**
     * 上级部门Id，一级部门则为所属单位Id
     */
    private Long superior;

    /**
     * 路径
     */
    private String path;

    /**
     * 部门代码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private boolean isDeleted;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 单位简称
     */
    private String shortName;

    /**
     * 英文名称
     */
    private String secondName;

    /**
     * 是否是内部机构
     */
    private String isInternal;

    /**
     * 是否是集团
     */
    private  boolean isGroup;

    /**
     * 只对type=account有效
     */
    private String levelScope;

    /**
     * 类型
     */
    private String type;

    /**
     *
     */
    private  String superiorName;

    private  String sortIdType;

    /**
     * 实体类型
     */
    private String entityType;

    private  String depManager;

    private String depAdmin;

    /**
     * 是否合法
     */
    private  boolean valid;

    private  boolean group;

    private  boolean customLogin;

    /**
     * 父部门路径
     */
    private  String parentPath;

    private String  customLoginUrl;

    /**
     * 扩展属性
     */
    private Properties properties;
}
