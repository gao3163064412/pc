package com.sygt.common.config;

import com.sygt.common.constant.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 读取项目相关配置
 * @class: LimsConfig
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Component
@ConfigurationProperties(prefix = "lims")
public class LimsConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        LimsConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        LimsConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取下载路径
     */
    public static String getExamDownloadPath() {
        return getProfile()+"/upload" + Constants.TEMPLATE_FTL_PATH_2;
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
    /**
     * 获取下载路径
     */
    public static String getDoorUploadPath() {
        return getProfile() ;
    }
}
