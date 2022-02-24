package com.sygt.common.constant;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 通用常量信息
 * @class: Constants
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class Constants {
    /**
     * 环境科学研究院部门id
     */
    public static final String HKY_DEPT_ID_01 = "100";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    public static final String ITEM = "item_";

    public static final String SAMPLE = "sample_";

    public static final String ENTRUST_FORM = "entrustForm_";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 用户管理 cache key
     */
    public static final String SYS_USER_KEY = "sys_user:";

    /**
     * 组织管理  cache key
     */
    public static final String SYS_DEPT_KEY = "sys_dept:";

    /**
     * 角色管理  cache key
     */
    public static final String  SYS_ROLE_KEY = "sys_role:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    public static  final  String EQU_STATEST_ATISTICAL = "100";

    /**
     * 字典：楼层
     */
    public static final String LAB_STOREY="lab_storey";

    /**
     * 字典：房间号
     */
    public static final String ROOM_NUMBER="room_number";

    /**
     * 字典：摄像头状态
     */
    public static final String CAMERA_STATE="camera_state";

    /**
     * 字典：设备数量/金额比刷新时间
     */
    public static final String EQU_REFRESH="equ_refresh";

    /**
     * 字典：环境气象刷新时间
     */
    public static final String ENV_REFRESH="env_refresh";

    /**
     * 字典：实验中心人员出入统计刷新时间
     */
    public static final String CENTER_REFRESH="center_refresh";

    /**
     * 字典：消防报警刷新时间
     */
    public static final String FIRE_REFRESH="fire_refresh";

    /**
     * 字典：视频轮播时间
     */
    public static final String VIDEO_CAROUSEL="video_carousel";

    /**
     * 删除标识
     */
    public static final Long DEL_FLAG=0l;

    /**
     * 版本号
     */
    public static final Long VERSION=0l;



    /**
     * 状态
     */
    public static final String STSTUS_0="0";
    public static final String STSTUS_1="1";  //正常
    public static final String STSTUS_2="2";  //停用
    /**
     * 模板地址
     */
    public static final String RESOURCE_TEMPLATE="template";
    /**
     * 审核状态
     */
    public static final String ACCESS_STSTUS_0="0";  //待审核
    public static final String ACCESS_STSTUS_1="1";  //审核通过
    public static final String ACCESS_STSTUS_2="2";  //驳回

    //审核人进度
    public static final String AUDIT_PEOPLE_0 ="0";
    public static final String AUDIT_PEOPLE_1 ="1";
    public static final String AUDIT_PEOPLE_2 ="2";
    public static final String AUDIT_PEOPLE_3 ="3";

    /**
     * 培训须知状态
     */
    public static final String TRAIN_STATUS_1 = "0"; //关闭
    public static final String TRAIN_STATUS_2 = "1"; //开启

    /**
     * 试题状态
     */
    public static final String QUE_STATUS_1 = "0"; //关闭
    public static final String QUE_STATUS_2 = "1"; //开启

    /**
     * 试题分类
     */
    public static final String QUE_CLASSIFY_1 = "1"; //选择题
    public static final String QUE_CLASSIFY_2 = "2"; //判断题
    public static final String QUE_CLASSIFY_3 = "3"; //填空题
    public static final String QUE_CLASSIFY_4 = "4"; //简答题

    /**
     * 试题难易
     */
    public static final String QUE_LEVEL_1 = "1"; //简单
    public static final String QUE_LEVEL_2 = "2"; //中等
    public static final String QUE_LEVEL_3 = "3"; //困难

    /**
     * 编号
     */
    public static final String QUE_CODE_ST = "ST";//试题
    public static final String QUE_CODE_SJ = "SJ";//试卷
    public static final String TEST_CODE_KS = "KS";//考试

    /**
     * 组卷类型
     */
    public static final String EXAM_TYPE_1 = "0"; //手动组卷
    public static final String EXAM_TYPE_2 = "1"; //自动组卷

    /**
     * 试题类型
     */
    public static final String QUE_TYPE_1 = "1"; //单选
    public static final String QUE_TYPE_2 = "2"; //多选

    /**
     * 判断题分类
     */
    public static final String QUE_PD_1 = "对";
    public static final String QUE_PD_2 = "错";

    /**
     * 试卷状态
     */
    public static final String EXAM_STATUS_1 = "0"; //关闭
    public static final String EXAM_STATUS_2 = "1"; //开启

    /**
     * 考试结果（0通过，1未通过）
     */
    public static final String EXAM_PASS_STATUS_1 = "0"; //0通过
    public static final String EXAM_PASS_STATUS_2 = "1"; //1未通过

    /**
     * 是否在线考试
     */
    public static final String EXAM_CHECK_1 = "0"; //线上
    public static final String EXAM_CHECK_2 = "1"; //线下

    /**
     * 模板生成文件路径
     */
    public static final String TEMPLATE_FTL_PATH_1= "/freemarker/template/";
    public static final String TEMPLATE_FTL_PATH_2= "/freemarker/file/";
    /**
     * 访客部门
     */
    public static final Long DEPY_ID_214= 214L;

    /**
     * 是否删除
     */
    public static final String DEL_FLAG_1 = "0"; //未删除
    public static final String DEL_FLAG_2 = "1"; //已删除

    //===============================================================================================
    /**
     * 消息发布状态 0:未发布 1：已发布
     */
    public static final String MESSAGE_PUBLIC_STATUS_0= "0";//未发布
    public static final String MESSAGE_PUBLIC_STATUS_1= "1";//已发布

    /**
     * 消息阅读状态 0：未读 1：已读
     */
    public static final String MESSAGE_READ_STATUS_0= "0";//未读
    public static final String MESSAGE_READ_STATUS_1= "1";//已读

    /**
     * 消息公告状态 0：正常 1：关闭
     */
    public static final String MESSAGE_STATUS_0= "0";//正常
    public static final String MESSAGE_STATUS_1= "1";//关闭

    /**
     * 消息类型 1通知 2公告 3准入管理消息 4危化品管理消息 5仪器管理消息 6预约管理消息 7私拿报警
     */
    public static final String MESSAGE_TYPE_1= "1";//1通知
    public static final String MESSAGE_TYPE_2= "2";//公告
    public static final String MESSAGE_TYPE_3= "3";//准入管理消息
    public static final String MESSAGE_TYPE_4= "4";//4危化品管理消息
    public static final String MESSAGE_TYPE_5= "5";//5仪器管理消息
    public static final String MESSAGE_TYPE_6= "6";//6预约管理消息

    public static final String MESSAGE_TYPE_7= "7";//私拿报警


    //===============================================================================================
    /**
     * 危化品入库状态
     */
    public static final String DANGER_WAREHOUSING_STATUS_0= "0"; //未完成
    public static final String DANGER_WAREHOUSING_STATUS_1 = "1"; //已完成

    /**
     * 危化品类型
     */
    public static final String DANGER_WAREHOUSING_CATEGORY_0= "0"; //剧毒
    public static final String DANGER_WAREHOUSING_CATEGORY_1 = "1"; //易制爆
    public static final String DANGER_WAREHOUSING_CATEGORY_STR_0= "toxic"; //剧毒
    public static final String DANGER_WAREHOUSING_CATEGORY_STR_1 = "explode"; //易制爆

    /**
     * 危化品编号
     */
    public static final String DANGER_CODE= "WHP";
    public static final String TOXIC_DANGER_CODE= "J";//剧毒编号
    public static final String EXPLODE_DANGER_CODE= "Y";//易制爆编号

    /**
     * 危化品单位
     */
    public static final String DANGER_UNIT_G= "g";
    public static final String DANGER_UNIT_ML= "ml";

    public static final String DANGER_UNIT_1= "1";//按瓶查询
    public static final String DANGER_UNIT_2= "2";//按g查询
    public static final String DANGER_UNIT_3= "3";//按ml查询

    //时间类型 1：按月度查询 2：按季度查询
    public static final String DANGER_TIME_TYPE_1= "1";//按月度查询
    public static final String DANGER_TIME_TYPE_2= "2";//按季度查询

    /**
     * 危化品采购编号
     */
    public static final String DANGER_PURCHASE_CODE= "CG";
    public static final String DANGER_PURCHASE_CASH_CODE= "dp_code";

    //危化品采购申请审核状态
    public static final String D_APPLY_STATUS_0 = "0"; //待申请
    public static final String D_APPLY_STATUS_1 = "1"; //审核通过
    public static final String D_APPLY_STATUS_2 = "2"; //审核中
    public static final String D_APPLY_STATUS_3 = "3"; //驳回
    public static final String D_APPLY_STATUS_4 = "4"; //驳回中

    //部门编码
    public static final String DEPT_CODE= "unit_code";

    //危化品编号在redis缓存中的key
    public static final String DANGER_REDIS_KEY="dangerCode";

    /**
     * 申请人状态
     */
    public static final String OUT_USER_STATUS_1 = "1"; //已申请
    public static final String OUT_USER_STATUS_2 = "2"; //待确认
    public static final String OUT_USER_STATUS_3 = "3"; //已确认
    public static final String OUT_USER_STATUS_4 = "4"; //驳回

    /**
     * 出库审核状态
     */
    public static final String OUT_STATUS_1 = "0"; //待审核
    public static final String OUT_STATUS_2 = "1"; //审核通过
    public static final String OUT_STATUS_3 = "2"; //审核中
    public static final String OUT_STATUS_4 = "3"; //驳回

    /**
     * 出库类型
     */
    public static final String OUT_TYPE_1 = "0"; //剧毒
    public static final String OUT_TYPE_2 = "1"; //易制爆

    /**
     * 出库审批状态
     */
    public static final String EX_STATUS_0 = "0"; //待审核
    public static final String EX_STATUS_1 = "1"; //同意
    public static final String EX_STATUS_2 = "2"; //逐级驳回
    public static final String EX_STATUS_3 = "3"; //驳回到申请人

    /**
     * 审批节点
     */
    public static final Integer AUDIT_ID_1 = 1; //员工内部申请
    public static final Integer AUDIT_ID_2 = 2; //二级单位管理员审批
    public static final Integer AUDIT_ID_3 = 3; //二级单位负责人审批
    public static final Integer AUDIT_ID_4 = 4; //检测中心管理员审批
    public static final Integer AUDIT_ID_5 = 5; //检测中心负责人审批

    /**
     * 出库按钮权限
     */
    public static final String SQ_STATUS_1 = "1"; //有权限

    /**
     * 角色id
     */
    public static final Long ROLE_ID_1 = 2l;//实验人员
    public static final Long ROLE_ID_2 = 108l;//二级单位管理员
    public static final Long ROLE_ID_3 = 109l;//二级单位负责人
    public static final Long ROLE_ID_7 = 101L;//主管院领导
    public static final Long ROLE_ID_8 = 113L;//仪器管理员
    public static final Long ROLE_ID_9 = 100L;//院级管理员2
    public static final Long ROLE_ID_10 = 114L;//管理员5
    public static final Long ROLE_ID_12 = 117L;//管理员5
    public static final Long ROLE_ID_11 = 115L;//管理员1

    /**
     * 院级管理员3角色
     */
    public static final Long ROLE_ID_4 = 106L;

    /**
     * 管理员3角色
     */
    public static final Long ROLE_ID_5 = 107L;

    /**
     * 数据权限
     */
    public static final String DATA_ROLE_1 = "1";
    public static final String DATA_ROLE_2 = "2";
    public static final String DATA_ROLE_3 = "3";

    /**
     * 类别
     */
    public static final String TYPE_1 = "1";
    public static final String TYPE_2 = "2";
    public static final String TYPE_3 = "3";
    public static final String TYPE_4 = "4";

    /**
     * 易制爆出库完成状态
     */
    public static final String YZB_STATUS_1 = "0";//待出库
    public static final String YZB_STATUS_2 = "1";//已出库

    /**
     * 审核表节点
     */
    public static final Long AUDIT_1 = 1L;//申请节点
    public static final Long AUDIT_2 = 2L;//二级单位管理员审批节点
    public static final Long AUDIT_3 = 3L;//二级单位负责人审批节点
    public static final Long AUDIT_4 = 4L;//检测中心管理员审批节点
    public static final Long AUDIT_5 = 5L;//检测中心负责人审批节点
    public static final Long AUDIT_6 = 6L;//院领导或辅助管理员审批节点



    /**
     * 整体出库完成状态
     */
    public static final String CK_STATUS_1 = "0";//待出库
    public static final String CK_STATUS_2 = "1";//已出库

    /**
     * 整体归还状态
     */
    public static final String REVERT_STATUS_1 = "0";//未归还
    public static final String REVERT_STATUS_2 = "1";//已归还

    /**
     * 统计类型
     */
    public static final Integer TYPE_PURCHASE = 1;//采购
    public static final Integer TYPE_USE = 2;//使用

    //危化品生命类型 1:申购 2：采购 3：入库 4：出库 5：归还
    public static final String DANGER_LIFE_TYPE_1 = "1";//申购
    public static final String DANGER_LIFE_TYPE_2 = "2";//采购
    public static final String DANGER_LIFE_TYPE_3 = "3";//入库
    public static final String DANGER_LIFE_TYPE_4 = "4";//出库
    public static final String DANGER_LIFE_TYPE_5 = "5";//归还

    //===================页面设置=========================================
    public static final String LAYOUT_SETTING_KEY = "layout-setting";//保存到redistribution中的key

    //===============================================================================================
    /**
     * 仪器是否上岗考核
     */
    public static final String INSTRUMENT_DUTY_TYPE_0 = "0";//是
    public static final String INSTRUMENT_DUTY_TYPE_1 = "1";//是

    /**
     * 仪器审核状态
     */
    public static final String INSTRUMENT_AUDIT_STATUS_0 = "0";//待审核
    public static final String INSTRUMENT_AUDIT_STATUS_1 = "1";//审核中
    public static final String INSTRUMENT_AUDIT_STATUS_2 = "2";//审核通过
    public static final String INSTRUMENT_AUDIT_STATUS_3 = "3";//驳回
    public static final String INSTRUMENT_AUDIT_STATUS_4 = "4";//其他

    /**
     * 仪器审核类型
     */
    public static final String INSTRUMENT_AUDIT_TYPES_1 = "1";//一级审核
    public static final String INSTRUMENT_AUDIT_TYPES_2 = "2";//二级审核

    /**
     * 仪器归还状态
     */
    public static final String INSTRUMENT_RETURN_STATUS_0 = "0";//待归还
    public static final String INSTRUMENT_RETURN_STATUS_1 = "1";//已归还

    /**
     * 仪器类型
     */
    public static final String INSTRUMENT_AUDIT_TYPE_1 = "1";//申请
    public static final String INSTRUMENT_AUDIT_TYPE_2 = "2";//审核
    public static final String INSTRUMENT_AUDIT_TYPE_3 = "3";//知会

    /**
     * 仪器状态
     */
    public static final String INSTRUMENT_STATUS_1 = "1";//空闲中
    public static final String INSTRUMENT_STATUS_2 = "2";//使用中
    public static final String INSTRUMENT_STATUS_3 = "3";//外出中
    public static final String INSTRUMENT_STATUS_4 = "4";//维修
    public static final String INSTRUMENT_STATUS_5 = "5";//报废
    public static final String INSTRUMENT_STATUS_6 = "6";//其他

    /**
     * 仪器保养、检定、校准状态
     */
    public static final String INSTRUMENT_MC_STATUS_0 = "0";//待保养/检定/校准/维修
    public static final String INSTRUMENT_MC_STATUS_1 = "1";//已保养/检定/校准/维修

    public static final String INSTRUMENT_MC_TYPE_1 = "1";//待检定
    public static final String INSTRUMENT_MC_TYPE_2 = "2";//已检定
    public static final String INSTRUMENT_MC_TYPE_3 = "3";//待校准
    public static final String INSTRUMENT_MC_TYPE_4 = "4";//已校准

    /**
     * 归还理由 1:损坏 2：丢失 3：其他
     */
    public static final String INSTRUMENT_RETURN_TYPE_1 = "1";//损坏
    public static final String INSTRUMENT_RETURN_TYPE_2 = "2";//丢失
    public static final String INSTRUMENT_RETURN_TYPE_3 = "3";//其他

    /**
     * 仪器外出申请批号
     */
    public static final String INSTRUMENT_EGRESS_CODE = "YQWC";//待保养/检定/校准/维修

    public static final String STATUS_STR_0 ="待审核";
    public static final String STATUS_STR_1 ="审核中";
    public static final String STATUS_STR_2 ="已通过";
    public static final String STATUS_STR_3 ="驳回";
    public static final String STATUS_STR_4 ="同意";
    public static final String STATUS_STR_5 ="合格";


    public static final String INSTRUMENT_TYPE_1 = "1";//合格
    public static final String INSTRUMENT_TYPE_0 = "2";//不合格

}
