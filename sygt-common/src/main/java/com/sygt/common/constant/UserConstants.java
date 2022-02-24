package com.sygt.common.constant;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 用户常量信息
 * @class: UserConstants
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class UserConstants {
    /**
     * 平台内系统用户的唯一标志
     */
    public static final String SYS_USER = "SYS_USER";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String EXCEPTION = "1";

    /**
     * 用户封禁状态
     */
    public static final String USER_DISABLE = "1";

    /**
     * 角色封禁状态
     */
    public static final String ROLE_DISABLE = "1";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "0";

    /**
     * 部门停用状态
     */
    public static final String DEPT_DISABLE = "1";

    /**
     * 字典正常状态
     */
    public static final String DICT_NORMAL = "0";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 是否菜单外链（是）
     */
    public static final String YES_FRAME = "0";

    /**
     * 是否菜单外链（否）
     */
    public static final String NO_FRAME = "1";

    /**
     * 菜单类型（目录）
     */
    public static final String TYPE_DIR = "M";

    /**
     * 菜单类型（菜单）
     */
    public static final String TYPE_MENU = "C";

    /**
     * 菜单类型（按钮）
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * Layout组件标识
     */
    public final static String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public final static String PARENT_VIEW = "ParentView";

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /**
     * 登录错误的次数
     */
    public static final String LOGIN_ERROR_COUNT ="login:error:count:";
    /**
     * 错误超过5次锁定账号
     */
    public static final String LOGIN_ERROR_LOCK ="login:error:lock:";

    /**
     * 用户类型（0：企业员工，1：非企业员工）
     */
    public static final String USER_TYPE_1 = "1";
    public static final String USER_TYPE_0 = "0";
    //超级管理员角色
    public static final Long SUPER_MANAGER_ROLE =1L;
    //项目负责人
    public static final Long ROLE_102 =102L;
    //院级管理员1
    public static final Long ROLE_103 =103L;
    //实验室负责人
    public static final Long ROLE_104 =104L;
    //检测中心负责人
    public static final Long ROLE_105 =105L;
    //院级管理员3
    public static final Long ROLE_106 =106L;
    //管理员3
    public static final Long ROLE_107 =107L;
    //二级单位管理员
    public static final Long ROLE_108 =108L;
    //二级单位负责人
    public static final Long ROLE_109 =109L;
    //检测中心管理员
    public static final Long ROLE_110 =110L;
    //实验人员
    public static final Long ROLE_2 =2L;
    //院领导
    public static final Long ROLE_101 =101L;
    //危化品管理员
    public static final Long ROLE_111 =111L;
    //仪器管理员
    public static final Long ROLE_113 =113L;
    //仪器设备管理员
    public static final Long ROLE_116 =116L;
    //专业实验室管理员
    public static final Long ROLE_117 =117L;
    //院级管理员2
    public static final Long ROLE_100 =100L;
    //管理员1
    public static final Long ROLE_115 =115L;
    //OA默认密码
    public static String OSSPASSWORD="9Ii5WKcT$vhynEw#";
    /**
     * 性别
     */
    public static final String SEX_TYPE_0 ="0";//男

    public static final String SEX_TYPE_1 ="1";//女

    public static final String SEX_TYPE_2 ="2";//未知

    /**
     * 当前用户已阅读完培训须知的数据
     */
    public static final String TRAIN_NOTICE_IDS ="train:notice:ids:";

    /**
     * 当前用户已学习完的数据
     */

    public static final String TRAIN_STUDY_IDS ="train:study:ids:";


}
