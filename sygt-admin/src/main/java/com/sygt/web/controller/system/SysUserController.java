package com.sygt.web.controller.system;

import com.sygt.common.annotation.Log;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.controller.BaseController;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.SysRole;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.core.domain.model.LoginUser;
import com.sygt.common.core.page.TableDataInfo;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.enums.BusinessType;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.ServletUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.common.utils.poi.ExcelUtil;
import com.sygt.framework.web.service.TokenService;
import com.sygt.system.service.ISysPostService;
import com.sygt.system.service.ISysRoleService;
import com.sygt.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 用户信息
 * @class: SysUserController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "用户信息",tags = "用户信息")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;
    /**
     * 获取用户列表
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 获取人员授权用户
     */
    @ApiOperation(value = "获取人员授权用户", notes = "获取人员授权用户")
    @GetMapping("/studylist")
    public TableDataInfo studylist(SysUser user) {
        startPage();
        List<SysUser> list = userService.studylist(user);
        return getDataTable(list);
    }

    /**
     * 获取用户列表不分页
     */
    @ApiOperation(value = "获取用户列表不分页", notes = "获取用户列表不分页")
    @GetMapping("/allList")
    public List<SysUser> allList(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return list;
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtils.encryptPassword("sygt2021..."));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
//        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", notes = "删除用户", position = 2)
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd/edit")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        // userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "账号状态修改", notes = "账号状态修改")
    @PostMapping("/changeStatus/edit")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        if(UserConstants.NORMAL.equals(user.getStatus())){//账号启用清空缓存限制
            redisCache.deleteObject(UserConstants.LOGIN_ERROR_COUNT+user.getUserName());
            redisCache.deleteObject(UserConstants.LOGIN_ERROR_LOCK+user.getUserName());
        }
        return toAjax(userService.updateUserStatus(user));
    }
    /**
     * 更改培训状态
     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @GetMapping("/changeStatus")
    @ApiOperation(value = "培训状态更改", notes = "培训状态更改")
    @ApiImplicitParam(name = "chenckTime", value = "时间校验(true:已学习/阅读)", required = true, dataType = "Boolean")
    public AjaxResult changeTrainStatus(Boolean chenckTime) {

        return toAjax(userService.updateTrainStatus(chenckTime));
    }


//    /**
//     * 批量修改角色
//     */
////    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "批量修改角色", businessType = BusinessType.UPDATE)
//    @GetMapping("/updateRole")
//    @ApiOperation(value = "培训状态更改", notes = "培训状态更改")
//    @ApiImplicitParam(name = "chenckTime", value = "时间校验(true:已学习/阅读)", required = true, dataType = "Boolean")
//    public AjaxResult updateRole(Boolean chenckTime) {
//        return toAjax(userService.updateRole());
//    }
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @GetMapping("/selectListByRole")
    @ApiOperation(value = "通过角色查找用户", notes = "通过角色查找用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "deptId", value = "部门Id", required = true, dataType = "Long")})
    public AjaxResult changeTrainStatus(Long roleId,Long deptId) {
        return AjaxResult.success(userService.selectListByRole(roleId,deptId));
    }

}
