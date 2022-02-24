package com.sygt.web.controller.system;


import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.*;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.utils.DictUtils;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.system.domain.LayoutSetting;
import com.sygt.system.service.ISysDeptService;
import com.sygt.system.service.ISysDictTypeService;
import com.sygt.system.service.ISysRoleService;
import com.sygt.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: Redis缓存刷新
 * @class: SysRedisRefreshController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@RestController
@RequestMapping("/system/redis")
@Api(value = "Redis缓存刷新", tags = "Redis缓存刷新")
public class SysRedisRefreshController {

    /**
     * 数据字典
     */
    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    /**
     * 用户
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 部门信息
     */
    @Autowired
    private ISysDeptService iSysDeptService;

    /**
     * 角色信息
     */
    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private RedisCache redisCache;

    /**
     * Redis字典缓存刷新
     */
    @ApiOperation(value = "Redis-[字典]缓存刷新", notes = "Redis-[字典]缓存刷新")
    @GetMapping("/getRedisDictRefresh")
    public AjaxResult getRedisDictRefresh() {
        List<SysDictType> dictTypeList = iSysDictTypeService.selectDictTypeAll();
        for (SysDictType dictType : dictTypeList) {
            List<SysDictData> dictDatas = iSysDictTypeService.selectDictDataByType(dictType.getDictType());
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
        return AjaxResult.success();
    }

    /**
     * Redis 用户缓存刷新
     */
    @ApiOperation(value = "Redis-[用户]缓存刷新", notes = "Redis-[用户]缓存刷新")
    @GetMapping("/getRedisUserRefresh")
    public AjaxResult getRedisUserRefresh() {
        SysUser user = new SysUser();
        user.setDelFlag(String.valueOf(Constants.DEL_FLAG));
        List<SysUser> userList = iSysUserService.selectUserList(new SysUser());
        if(userList != null && !userList.isEmpty()){
            redisCache.setCacheList(Constants.SYS_USER_KEY + "user_list",userList);
        }
        return AjaxResult.success();
    }


    /**
     * Redis 组织 缓存刷新
     */
    @ApiOperation(value = "Redis-[组织]缓存刷新", notes = "Redis-[组织]缓存刷新")
    @GetMapping("/getRedisDeptRefresh")
    public AjaxResult getRedisDeptRefresh() {
        SysDept dept = new SysDept();
        dept.setDelFlag(String.valueOf(Constants.DEL_FLAG));
        List<SysDept> deptList = iSysDeptService.selectDeptList(new SysDept());
        if(deptList != null && !deptList.isEmpty()){
            redisCache.setCacheList(Constants.SYS_DEPT_KEY + "dept_list",deptList);
        }
        return AjaxResult.success();
    }

    /**
     * Redis 角色 缓存刷新
     */
    @ApiOperation(value = "Redis-[角色]缓存刷新", notes = "Redis-[角色]缓存刷新")
    @GetMapping("/getRedisRoleRefresh")
    public AjaxResult getRedisRoleRefresh() {
        SysRole role = new SysRole();
        role.setDelFlag(String.valueOf(Constants.DEL_FLAG));
        List<SysRole> userList = iSysRoleService.selectRoleList(new SysRole());
        if(userList != null && !userList.isEmpty()){
            redisCache.setCacheList(Constants.SYS_ROLE_KEY + "role_list",userList);
        }
        return AjaxResult.success();
    }

    /**
     * Redis 页面设置 保存缓存
     */
    @PostMapping("/setLayoutSetting")
    public AjaxResult setLayoutSetting(@RequestBody LayoutSetting layoutSetting) {
        if(StringUtils.isNotNull(layoutSetting)){
            redisCache.setCacheObject(Constants.LAYOUT_SETTING_KEY+"_"+ SecurityUtils.getLoginUser().getUser().getUserId(),layoutSetting);
        }
        return AjaxResult.success();
    }

    /**
     * Redis 页面设置 缓存读取
     */
    @GetMapping("/getLayoutSetting")
    public AjaxResult getLayoutSetting() {
        LayoutSetting layoutSetting = new LayoutSetting();
        Object cacheObject = redisCache.getCacheObject(Constants.LAYOUT_SETTING_KEY+"_"+ SecurityUtils.getLoginUser().getUser().getUserId());
        if(cacheObject != null){
            layoutSetting = (LayoutSetting)cacheObject;
            return AjaxResult.success(layoutSetting);
        }
        return AjaxResult.success(layoutSetting);
    }

}
