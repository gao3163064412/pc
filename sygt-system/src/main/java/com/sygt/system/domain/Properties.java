package com.sygt.system.domain;

import lombok.Data;

@Data
public class Properties {

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮件
     */
    private String unitMail;

    /**
     * 地址
     */
    private String address;
    /**
     * 负责人
     */
    private String chiefLeader;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 电话
     */
    private String telephone;

    /**
     * IP地址
     */
    private String ipAddress;

    private String unitCategory;
    private String permissionType;
    private String isCustomLoginUrl;
    private String accessLevels;
    private String customLoginUrl;
    private String ldapOu;


}
