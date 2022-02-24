package com.sygt.web.controller.system;

import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.SysMenu;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.core.domain.model.LoginBody;
import com.sygt.common.core.domain.model.LoginUser;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.utils.ServletUtils;
import com.sygt.framework.web.service.SysLoginService;
import com.sygt.framework.web.service.SysPermissionService;
import com.sygt.framework.web.service.TokenService;
import com.sygt.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 登录验证（字典类型）
 * @class: SysLoginController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "用户登录",tags = "用户登录")
@RestController
@Slf4j
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录方法", notes = "登录方法", position = 2)
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        // 将生成的令牌存入redis中
        redisCache.setCacheObject(loginBody.getUsername(),token);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    /**
     * 访客登录方法
     *
     * @return 结果
     */
    @PostMapping("/getToken")
    public AjaxResult getToken(HttpServletRequest request) {
        String token =  tokenService.getUserToken(request);
        return AjaxResult.success(token);
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", position = 2)
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation(value = "获取路由信息", notes = "获取路由信息", position = 2)
    public AjaxResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }




}
