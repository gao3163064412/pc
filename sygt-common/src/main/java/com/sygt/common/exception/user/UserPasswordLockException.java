package com.sygt.common.exception.user;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 密码错误超过五次异常类
 * @class: UserPasswordLockException
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class UserPasswordLockException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordLockException() {
        super("user.login.error.lock", null);
    }
}
