package com.sygt.framework.web.service;

import javax.annotation.Resource;

import com.sygt.common.constant.HttpStatus;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.exception.user.UserBlockException;
import com.sygt.common.exception.user.UserPasswordLockException;
import com.sygt.common.exception.user.UserPasswordNotMatchException;
import com.sygt.system.service.ISysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.model.LoginUser;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.exception.CustomException;
import com.sygt.common.utils.MessageUtils;
import com.sygt.framework.manager.AsyncManager;
import com.sygt.framework.manager.factory.AsyncFactory;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 登录校验方法
 * @class: SysLoginService
 * @date: 2021/05/20 10:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);



    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
//        if (captcha == null) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
//            throw new CaptchaExpireException();
//        }
//        if (!code.equalsIgnoreCase(captcha)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//            throw new CaptchaException();
//        }

        if(ObjectUtils.isNotEmpty(redisCache.getCacheObject(UserConstants.LOGIN_ERROR_LOCK+username))){
            long beginTime = Long.parseLong(redisCache.getCacheObject(UserConstants.LOGIN_ERROR_LOCK + username).toString());
            long surplusTime = 30-(System.currentTimeMillis()-beginTime)/60000;
            throw new CustomException("密码错误超过3次已被锁定,请"+surplusTime+"分钟后再登录", HttpStatus.ERROR);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                int count = ObjectUtils.isEmpty(redisCache.getCacheObject(UserConstants.LOGIN_ERROR_COUNT+username))?0:Integer.parseInt(redisCache.getCacheObject(UserConstants.LOGIN_ERROR_COUNT+username).toString());

                if(count==3 && ObjectUtils.isEmpty(redisCache.getCacheObject(UserConstants.LOGIN_ERROR_LOCK+username))){
                    redisCache.setCacheObject(UserConstants.LOGIN_ERROR_LOCK+username,System.currentTimeMillis());
                    redisCache.expire(UserConstants.LOGIN_ERROR_LOCK+username,1800);//1800
                    redisCache.incr(UserConstants.LOGIN_ERROR_COUNT+username,122400);
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,"密码错误超过3次,请三十分钟后再登录"));
                    throw new UserPasswordLockException();
                }else if(count>5){//错误十次锁定账号
                    SysUser sysUser = userService.selectUserByUserName(username);
                    sysUser.setStatus(UserConstants.NOT_UNIQUE);
                    sysUser.setUpdateUserId(sysUser.getCreateUserId());
                    userService.updateUserStatus(sysUser);
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,"密码错误超过5次,账号已被锁定请联系管理员"));
                    throw new UserBlockException();

                }else {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                    redisCache.incr(UserConstants.LOGIN_ERROR_COUNT+username,122400);
                    throw new UserPasswordNotMatchException();
                }
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        /**
         * 登录成功数据清空
         */
        redisCache.deleteObject(UserConstants.LOGIN_ERROR_LOCK+username);
        redisCache.deleteObject(UserConstants.LOGIN_ERROR_COUNT+username);
        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * 登录验证
     *
     * @return 结果
     */
    public String osslogin(String username, String password) {

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (Exception e) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        /**
         * 登录成功数据清空
         */
        redisCache.deleteObject(UserConstants.LOGIN_ERROR_LOCK+username);
        redisCache.deleteObject(UserConstants.LOGIN_ERROR_COUNT+username);
        // 生成token
        return tokenService.createToken(loginUser);
    }

}
