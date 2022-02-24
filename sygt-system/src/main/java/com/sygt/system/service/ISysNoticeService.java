package com.sygt.system.service;

import java.util.List;

import com.sygt.system.domain.SysNotice;
import com.sygt.system.domain.vo.SysNoticeDto;
import com.sygt.system.dto.NoticeDto;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 公告 服务层
 * @class: ISysNoticeService
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface ISysNoticeService {
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
     List<SysNotice> selectNoticeList(SysNotice notice);

    //获取通知公告树
     List<SysNoticeDto> selectNoticeTree();

    //获取未读数量
    int get_no_read();

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
     int insertNotice(NoticeDto notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
     int updateNotice(NoticeDto notice);

    /**
     * 删除公告信息
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

     //发送消息
    int sendMessage(SysNotice notice);

    //批量查看
    int batchView(Long[] noticeIds);
}
