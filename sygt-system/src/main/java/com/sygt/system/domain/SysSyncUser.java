package com.sygt.system.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 同步员工数据
 */

@Data
public class SysSyncUser {

    /**
     * 所属单位ID
     */
    private Long orgAccountId;

    /**
     * ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门名称
     */
    private String code;
    /**
     * 账户状态：true为启用，false为停用
     */
    private boolean enabled;

    /**
     * 排序号
     */
    private  Integer sortId;

    //人员职务级别Id
    private Long orgLevelId;
    /**
     *人员岗位Id
     */
    private Long orgPostId;
    /**
     *人员所属部门Id
     */
    private Long orgDepartmentId;
    /**
     *是否可登录
     */
    private boolean isLoginable;
    /**
     *是否已分配人员
     */
    private boolean isAssigned;
    /**
     *是否管理员，集团管理员、系统管理员、审计管理员或单位管理员返回true，否则为false
     */
    private boolean isAdmin;
    /**
     *是否可用：已删除、被停用、不可登录、未分配或离职人员返回false，否则返回true
     */
    private boolean isValid;
    /**
     *人员状态：1为在职，2 为离职
     */
    private Integer state;
    /**
     *副岗
     */
    private List<JSONObject> secondPost;
    /**
     *兼职
     */
    private List concurrentPost;
    /**
     *手机号码
     */
    private String telNumber;
    /**
     *出生日期
     */
    private Long birthday;
    /**
     *办公电话
     */
    private String officeNum;
    /**
     *电子邮件
     */
    private String emailAddress;

    /**
     *性别：-1为未指定、1为男、2为女
     */
    private String gender;

    /**
     *登录名
     */
    private String loginName;

    /**
     * 工作地
     */
    private String location;

    /**
     * 汇报人
     */
    private String reporter;

    /**
     * 入职时间
     */
    private String hiredate;



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
     * 描述
     */
    private String description;

    /**
     * 是否内部人员
     */
    private String isInternal;

    /**
     * 实体状态：1为正常、2为申请停用、3为申请删除、4为申请调离；此属性不常用，一般返回1
     */
    private Integer status;






}
