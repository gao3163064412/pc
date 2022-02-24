package com.sygt.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cm
 * 添加修改入参数据
 */

@Data
@Accessors(chain = true)
public class NoticeDto {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 角色
     */
    private String roleIds;

    //用户ID
    private String userIds;

    /**
     * 人员
     */
    private String staff;
    /**
     * 出处
     */
    private String provenance;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    //发布状态(0:未发布 1:已发布)
    private String releaseStatus;

    //阅读状态
    private String readStatus;

    //跳转路径
    private String linkPath;




}
