package com.sygt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: Excel注解集
 * @class: Excels
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {
    Excel[] value();
}
