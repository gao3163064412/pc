package com.sygt.web.controller.system;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sygt.common.annotation.Log;
import com.sygt.common.core.controller.BaseController;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.enums.BusinessType;
import com.sygt.system.domain.SysSignature;
import com.sygt.system.service.ISysSignatureService;
import com.sygt.common.utils.poi.ExcelUtil;
import com.sygt.common.core.page.TableDataInfo;
import springfox.documentation.annotations.ApiIgnore;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 签章管理Controller
 * @class: SysSignatureController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "签章信息管理", tags = "签章信息管理")
@RestController
@RequestMapping("/system/signature")
public class SysSignatureController extends BaseController {
    @Autowired
    private ISysSignatureService sysSignatureService;

    /**
     * 查询签章管理列表
     */
    @ApiOperation(value = "查询签章管理列表", notes = "查询签章管理列表", position = 2)
    @ApiImplicitParam(name = "sysSignature", value = "签章信息", dataType = "SysSignature")
    @PreAuthorize("@ss.hasPermi('system:signature:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSignature sysSignature) {
        startPage();
        List<SysSignature> list = sysSignatureService.selectSysSignatureList(sysSignature);
        return getDataTable(list);
    }

    /**
     * 导出签章管理列表
     */
    @ApiIgnore
    @ApiOperation(value = "导出签章管理列表", notes = "导出签章管理列表", position = 2)
    @ApiImplicitParam(name = "sysSignature", value = "签章信息", dataType = "SysSignature")
    @PreAuthorize("@ss.hasPermi('system:signature:export')")
    @Log(title = "签章管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysSignature sysSignature) {
        List<SysSignature> list = sysSignatureService.selectSysSignatureList(sysSignature);
        ExcelUtil<SysSignature> util = new ExcelUtil<SysSignature>(SysSignature.class);
        return util.exportExcel(list, "签章管理数据");
    }

    /**
     * 获取签章管理详细信息
     */
    @ApiOperation(value = "获取签章管理详细信息", notes = "获取签章管理详细信息", position = 2)
    @ApiImplicitParam(name = "id", value = "签章信息ID", required = true , dataType = "Long")
    @PreAuthorize("@ss.hasPermi('system:signature:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysSignatureService.selectSysSignatureById(id));
    }

    /**
     * 新增签章管理
     */
    @ApiOperation(value = "新增签章管理", notes = "新增签章管理", position = 2)
    @ApiImplicitParam(name = "sysSignature", value = "签章信息", required = true , dataType = "SysSignature")
    @PreAuthorize("@ss.hasPermi('system:signature:add')")
    @Log(title = "签章管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSignature sysSignature) {
        return toAjax(sysSignatureService.insertSysSignature(sysSignature));
    }

    /**
     * 修改签章管理
     */
    @ApiOperation(value = "修改签章管理", notes = "修改签章管理", position = 2)
    @ApiImplicitParam(name = "sysSignature", value = "签章信息", required = true , dataType = "SysSignature")
    @PreAuthorize("@ss.hasPermi('system:signature:edit')")
    @Log(title = "签章管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody SysSignature sysSignature) {
        return toAjax(sysSignatureService.updateSysSignature(sysSignature));
    }

    /**
     * 删除签章管理
     */
    @ApiOperation(value = "删除签章管理", notes = "删除签章管理", position = 2)
    @ApiImplicitParam(name = "ids", value = "需要删除的签章信息ID数组", required = true , dataType = "Long")
    @PreAuthorize("@ss.hasPermi('system:signature:remove')")
    @Log(title = "签章管理", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysSignatureService.deleteSysSignatureByIds(ids));
    }
}
