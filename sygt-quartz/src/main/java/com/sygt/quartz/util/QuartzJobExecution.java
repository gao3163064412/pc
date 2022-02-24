package com.sygt.quartz.util;

import com.sygt.quartz.domain.SysJob;
import org.quartz.JobExecutionContext;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 定时任务处理（允许并发执行）
 * @class: QuartzJobExecution
 * @date: 2021/05/20 10:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
