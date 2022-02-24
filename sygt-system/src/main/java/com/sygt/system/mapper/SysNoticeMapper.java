package com.sygt.system.mapper;

import java.util.List;

import com.sygt.system.dto.NoticeListDto;
import com.sygt.system.domain.SysNotice;
import org.apache.ibatis.annotations.Param;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 通知公告表 数据层
 * @class: SysMenuMapper
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface SysNoticeMapper {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
     SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
     List<SysNotice> selectNoticeList(@Param("notice") NoticeListDto notice, @Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("createUser") Long createUser, @Param("roleIds") List<Long> roleIds);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
     int insertNotice(SysNotice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
     int updateNotice(SysNotice notice);

    /**
     * 批量删除公告
     *
     * @param noticeId 公告ID
     * @return 结果
     */
     int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
     int deleteNoticeByIds(Long[] noticeIds);

    //获取普通消息列表
    List<SysNotice> getGeneralNoticeList(SysNotice notice);

    //获取系统消息列表
    List<SysNotice> getSysNoticeList(SysNotice notice);

    //根据消息ID更新消息阅读状态
    void updateNoticeRead(@Param("noticeId") Long noticeId,@Param("readStatus") String readStatus);

    /**
     * 根据不同的用户查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    List<SysNotice> selectNoticeListByUser(SysNotice notice);

}
