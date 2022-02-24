package com.sygt.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.entity.SysMenu;
import com.sygt.common.utils.StringUtils;
import com.sygt.common.utils.poi.ExcelUtil;
import com.sygt.system.domain.vo.SysNoticeVo;
import com.sygt.system.dto.NoticeDto;
import com.sygt.system.service.ISysMenuService;
import io.swagger.annotations.*;
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
import com.sygt.common.core.page.TableDataInfo;
import com.sygt.common.enums.BusinessType;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.system.domain.SysNotice;
import com.sygt.system.service.ISysNoticeService;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 公告 信息操作处理
 * @class: SysNoticeController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@RestController
@RequestMapping("/system/notice")
@Api(value = "消息管理",tags = "消息管理")
public class SysNoticeController extends BaseController {

    @Autowired
    private ISysNoticeService noticeService;
    @Autowired
    private ISysMenuService menuService;
    /**
     * 获取通知公告列表
     */
//    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    @ApiOperation(value = "消息管理列表", notes = "消息管理列表")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 导出通知公告列表
     */
    @ApiOperation(value = "导出通知公告列表", notes = "导出通知公告列表")
    @ApiImplicitParam(name = "notice", value = "通知公告信息", dataType = "SysNotice")
    @PreAuthorize("@ss.hasPermi('system:notice:export')")
    @GetMapping("/export")
    public AjaxResult export(SysNotice notice) {
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        List<SysNoticeVo> expList = new ArrayList<>();
        if(StringUtils.isNotNull(list)){
            for (SysNotice sysNotice : list) {
                SysNoticeVo vo = new SysNoticeVo();
                vo.setNoticeTitle(sysNotice.getNoticeTitle());
                vo.setCreateName(sysNotice.getCreateName());
                vo.setReadStatus(sysNotice.getReadStatus());
                vo.setReleaseStatus(sysNotice.getReleaseStatus());
                vo.setCreateTime(sysNotice.getCreateTime());
                expList.add(vo);
            }
        }
        ExcelUtil<SysNoticeVo> util = new ExcelUtil<SysNoticeVo>(SysNoticeVo.class);
        return util.exportExcel(expList, "消息信息数据");
    }
    /**
     * 根据通知公告编号获取详细信息
     */
    @ApiOperation(value = "查询消息详细信息", notes = "查询消息详细信息")
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增消息", notes = "新增消息")
    public AjaxResult add(@Validated @RequestBody NoticeDto notice) {
        //设置发布状态为：未发布
        notice.setReleaseStatus(Constants.MESSAGE_PUBLIC_STATUS_0);
        //设置消息类型为：普通消息
        notice.setNoticeType(Constants.MESSAGE_TYPE_1);
        //设置阅读状态为：未读
        notice.setReadStatus(Constants.MESSAGE_READ_STATUS_0);
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ApiOperation(value = "修改消息", notes = "修改消息")
    public AjaxResult edit(@Validated @RequestBody NoticeDto notice) {
        //设置发布状态为：未发布
        notice.setReleaseStatus(Constants.MESSAGE_PUBLIC_STATUS_0);
        //设置消息类型为：普通消息
        notice.setNoticeType(Constants.MESSAGE_TYPE_1);
        //设置阅读状态为：未读
        notice.setReadStatus(Constants.MESSAGE_READ_STATUS_0);
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{noticeIds}")
    @ApiOperation(value = "删除消息", notes = "删除消息")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }

    /**
     * 批量查看
     */
    @PreAuthorize("@ss.hasPermi('system:notice:batchView')")
    @GetMapping("/batch_view/{noticeIds}")
    @ApiOperation(value = "批量查看", notes = "批量查看")
    public AjaxResult batch_view(@PathVariable Long[] noticeIds) {
        return AjaxResult.success(noticeService.batchView(noticeIds));
    }

    /**
     * 是否展示消息按钮
     */
    @GetMapping("/checkNoticeIcon")
    @ApiOperation(value = "是否展示消息图标", notes = "是否展示消息图标")
    public AjaxResult checkNoticeIcon() {
        List<SysMenu> sysMenus = menuService.selectMenuList(SecurityUtils.getUserId());
        boolean b= false;
        for (SysMenu menu:sysMenus) {
            if(StringUtils.isNotEmpty(menu.getComponent()) && menu.getComponent().contains("system/notice/index")){
                b = true;
                break;
            }
        }
        return AjaxResult.success(b);
    }

    //发布通知公告
    @ApiOperation(value = "发布通知公告", notes = "发布通知公告", position = 2)
    @ApiImplicitParam(name = "notice", value = "消息信息", dataType = "SysNotice")
    @ApiResponses({
            @ApiResponse(code = 200,message = "isOk",response = SysNotice.class)
    })
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/release")
    public AjaxResult release(@Validated @RequestBody NoticeDto notice) {
        //设置阅读状态为：未读
        notice.setReadStatus(Constants.MESSAGE_READ_STATUS_0);
        if(!StringUtils.isNull(notice.getNoticeId())){
            //设置发布状态为：已发布
            notice.setReleaseStatus(Constants.MESSAGE_PUBLIC_STATUS_1);
            return toAjax(noticeService.updateNotice(notice));
        }else {
            //设置发布状态为：已发布
            notice.setReleaseStatus(Constants.MESSAGE_PUBLIC_STATUS_1);
            return toAjax(noticeService.insertNotice(notice));
        }

    }

    //获取通知公告树  selectNoticeTree
    @ApiOperation(value = "获取通知公告树", notes = "获取通知公告树")
    @GetMapping(value = "/selectNoticeTree")
    public AjaxResult selectNoticeTree() {
        return AjaxResult.success(noticeService.selectNoticeTree());
    }

    //获取未读数量
    @ApiOperation(value = "获取未读数量", notes = "获取未读数量")
    @GetMapping(value = "/getNoReadNum")
    public AjaxResult get_no_read() {
        return AjaxResult.success(noticeService.get_no_read());
    }

}
