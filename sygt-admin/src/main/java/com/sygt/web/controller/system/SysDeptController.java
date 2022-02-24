package com.sygt.web.controller.system;

import java.util.Iterator;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sygt.common.annotation.Log;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.controller.BaseController;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.SysDept;
import com.sygt.common.enums.BusinessType;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.system.service.ISysDeptService;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 部门信息
 * @class: SysDeptController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "部门信息",tags = "部门信息")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @ApiOperation(value = "获取部门列表", notes = "获取部门列表")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @ApiOperation(value = "查询部门列表（排除节点）", notes = "查询部门列表（排除节点）")
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @ApiOperation(value = "根据部门编号获取详细信息", notes = "根据部门编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId) {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @ApiOperation(value = "获取部门下拉树列表", notes = "获取部门下拉树列表")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        dept.setCreateName(SecurityUtils.getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        dept.setUpdateName(SecurityUtils.getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @ApiOperation(value = "删除部门", notes = "删除部门")
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }
    /**
     * 查询所有二级单位
     */
    @ApiOperation(value = "查询所有二级单位", notes = "查询所有二级单位")
    @GetMapping("/childList")
    @ApiImplicitParam(name = "deptId", value = "部门Id", dataType = "Long")
    public AjaxResult selectChildList(Long deptId) {
        List<SysDept> deptList = deptService.selectChildList(deptId);
        return AjaxResult.success(deptList);
    }

    /**
     * 查询角色对应展示的二级单位
     */
    @ApiOperation(value = "查询用户对应展示的二级单位", notes = "查询用户对应展示的二级单位")
    @GetMapping("/ListByRole")
    public AjaxResult selectListByRole() {
        List<SysDept> deptList = deptService.selectListByRole();
        return AjaxResult.success(deptList);
    }
}
