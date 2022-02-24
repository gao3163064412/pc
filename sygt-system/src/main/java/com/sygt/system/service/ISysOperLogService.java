package com.sygt.system.service;

import java.util.List;

import com.sygt.system.domain.SysOperLog;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 操作日志 服务层
 * @class: ISysOperLogService
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface ISysOperLogService {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
     void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
     List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
     int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
     SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
     void cleanOperLog();
}
