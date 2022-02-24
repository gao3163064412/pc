package com.sygt.web.controller.system;

import java.util.List;

import io.swagger.annotations.Api;
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
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.controller.BaseController;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.page.TableDataInfo;
import com.sygt.common.enums.BusinessType;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.poi.ExcelUtil;
import com.sygt.system.domain.SysPost;
import com.sygt.system.service.ISysPostService;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 岗位信息操作处理
 * @class: SysPostController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "岗位信息操作处理",tags = "岗位信息操作处理")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    @Autowired
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @ApiOperation(value = "获取岗位列表", notes = "获取岗位列表")
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @GetMapping("/export")
    public AjaxResult export(SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @ApiOperation(value = "根据岗位编号获取详细信息", notes = "根据岗位编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        post.setCreateName(SecurityUtils.getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        post.setUpdateName(SecurityUtils.getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @ApiOperation(value = "删除岗位", notes = "删除岗位")
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}
