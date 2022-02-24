package com.sygt.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.sygt.common.core.domain.BaseEntity;

import java.util.List;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 通知公告表 sys_notice
 * @class: SysNotice
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    //需要导出的消息
    @TableField(exist = false)
    private String ids;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告 3准入管理消息 4危化品管理消息 5仪器管理消息 6预约管理消息）
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

    //用户角色ID列表
    @TableField(exist = false)
    private List<Long> rolesList;

    //用户ID
    @TableField(exist = false)
    private Long userId;

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

    /**
     * 时间区间（yyyy-MM-dd~yyyy-MM-dd）
     */
    @TableField(exist = false)
    private String searchTime;



    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public List<Long> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Long> rolesList) {
        this.rolesList = rolesList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("readStatus", getReadStatus())
                .append("releaseStatus", getReleaseStatus())
                .append("createUserId", getCreateUserId())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateUserId", getUpdateUserId())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("roleIds", getRoleIds())
                .append("userIds", getUserIds())
                .append("staff", getStaff())
                .append("provenance", getProvenance())
                .toString();
    }
}
