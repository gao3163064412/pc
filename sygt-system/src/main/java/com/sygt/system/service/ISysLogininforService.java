package com.sygt.system.service;

import java.util.List;

import com.sygt.system.domain.SysLogininfor;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 系统访问日志情况信息 服务层
 * @class: ISysLogininforService
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface ISysLogininforService {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
     void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
     List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
     int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
     void cleanLogininfor();
}
