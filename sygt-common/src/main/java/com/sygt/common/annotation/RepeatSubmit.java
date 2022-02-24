package com.sygt.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 自定义注解防止表单重复提交
 * @class: RepeatSubmit
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

}
