package com.sygt.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.sygt.common.core.domain.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import com.sygt.common.core.controller.BaseController;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.SysDictData;
import com.sygt.common.core.page.TableDataInfo;
import com.sygt.common.enums.BusinessType;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.common.utils.poi.ExcelUtil;
import com.sygt.system.service.ISysDictDataService;
import com.sygt.system.service.ISysDictTypeService;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 数据字典信息
 * @class: SysDeptController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "数据字典信息（字典值）",tags = "数据字典信息（字典值）")
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @GetMapping("/export")
    public AjaxResult export(SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @ApiOperation(value = "查询字典数据详细", notes = "查询字典数据详细")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息", notes = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return AjaxResult.success(data);
    }
    /**
     * 查询字典类型详细(不需要登录认证)
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息(不需要登录认证)", notes = "根据字典类型查询字典数据信息(不需要登录认证)")
    @GetMapping(value = "/selectByType/{dictType}")
    public AjaxResult selectByCode(@PathVariable  String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return AjaxResult.success(data);
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        dict.setCreateName(SecurityUtils.getUsername());

        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        dict.setUpdateName(SecurityUtils.getUsername());

        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @ApiOperation(value = "删除字典值", notes = "删除字典值")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }

    /**
     * 查找所有楼层对应的房间号
     */
    @ApiOperation(value = "查找所有楼层对应的房间号", notes = "查找所有楼层对应的房间号")
    @GetMapping(value = "/selectLevelAndRoom")
    public AjaxResult selectRoomAndLevel() {
        List<TreeVo> data = dictTypeService.selectRoomAndLevel();
        return AjaxResult.success(data);
    }

    /**
     * 通过类型查找父子集数据（通过备注找子数据）
     */
    @ApiOperation(value = "通过类型查找父子集数据", notes = "通过类型查找父子集数据")
    @GetMapping(value = "/selectChildListByType")
    @ApiImplicitParams({@ApiImplicitParam(name = "parentDictType", value = "父字典类型", dataType = "parentDictType"), @ApiImplicitParam(name = "childDictType", value = "子字典类型", dataType = "childDictType")})
    public AjaxResult selectChildListByType(String parentDictType,String childDictType) {
        List<TreeVo> data = dictTypeService.selectChildListByType(parentDictType,childDictType);
        return AjaxResult.success(data);
    }

}
