package com.sygt.common.utils;

import com.sygt.common.constant.HttpStatus;
import com.sygt.common.exception.CustomException;
import com.sygt.common.core.domain.entity.SysRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sygt.common.core.domain.model.LoginUser;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 安全服务工具类
 * @class: SecurityUtils
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class SecurityUtils {
    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new CustomException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getUser().getUserId();
        } catch (Exception e) {
            throw new CustomException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new CustomException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取角色
     **/
    public static List<Long> getRoleIds() {
        List<Long> roleIds = new ArrayList<>();
        try {
            if(!StringUtils.isNull(getLoginUser().getUser().getRoles())){
                List<SysRole> roles = getLoginUser().getUser().getRoles();
                for (SysRole role:roles) {
                    roleIds.add(role.getRoleId());
                }
            }
        } catch (Exception e) {
            throw new CustomException("获取角色信息异常", HttpStatus.UNAUTHORIZED);
        }
        return roleIds;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
