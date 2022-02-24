package com.sygt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 启动程序
 * @class: LimsApplication
 * @date: 2021/05/20 10:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LimsApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LimsApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  智慧实验室管理系统启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
