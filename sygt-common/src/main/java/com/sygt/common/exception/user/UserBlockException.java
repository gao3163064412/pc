package com.sygt.common.exception.user;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 账号被封异常类
 * @class: UserBlockException
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class UserBlockException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserBlockException() {
        super("user.blocked", null);
    }
}
