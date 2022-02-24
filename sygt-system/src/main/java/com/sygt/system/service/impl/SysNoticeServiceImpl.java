package com.sygt.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.entity.SysRole;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.system.domain.SysNotice;
import com.sygt.system.domain.vo.SysNoticeDto;
import com.sygt.system.dto.NoticeDto;
import com.sygt.system.mapper.SysNoticeMapper;
import com.sygt.system.mapper.SysNoticeUserMapper;
import com.sygt.system.service.ISysNoticeService;
import com.sygt.system.service.ISysNoticeUserService;
import com.sygt.system.domain.SysNoticeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 公告 服务层实现
 * @class: SysNoticeServiceImpl
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    @Autowired
    private SysNoticeMapper noticeMapper;

    @Autowired
    private SysNoticeUserMapper noticeUserMapper;

    @Autowired
    private ISysNoticeUserService noticeUserService;


    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    @Transactional
    public SysNotice selectNoticeById(Long noticeId) {
        saveReadToNotice(noticeId);//设置消息已读
        return noticeMapper.selectNoticeById(noticeId);
    }

    //将消息ID和用户ID保存到消息阅读表中
    public void saveReadToNotice(Long noticeId){
        SysNoticeUser sysNoticeUser = new SysNoticeUser();
        sysNoticeUser.setNId(noticeId);
        sysNoticeUser.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        noticeUserService.insertSysNoticeUser(sysNoticeUser);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {

        if(StringUtils.isNotNull(notice.getNoticeType())){
            if(notice.getNoticeType().equals("0")){
                notice.setNoticeType(null);
            }
        }

        if (SecurityUtils.isAdmin(SecurityUtils.getLoginUser().getUser().getUserId())) {
            //判断是否为管理员
            notice.setRolesList(null);
            notice.setUserId(null);
        }else {
            //根据角色获取数据
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            //TODO 将list中对象的某个属性转成list
            List<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
            if (roleIds != null && roleIds.size()>0) {
                notice.setRolesList(roleIds);
            }
            notice.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        List<SysNotice> sysNotices = noticeMapper.selectNoticeListByUser(notice);
        //根据用户查询已读的消息
        List<SysNoticeUser> sysNoticeUser = noticeUserMapper.findSysNoticeUserListByUserId(SecurityUtils.getLoginUser().getUser().getUserId());

        if(!StringUtils.isNull(sysNotices) && !StringUtils.isNull(sysNoticeUser)){
            sysNotices.stream().forEach(item->{
                if(sysNoticeUser.stream().filter(m->m.getNId().equals(item.getNoticeId())).findAny().isPresent()){
                    item.setReadStatus("已读");
                }else {
                    item.setReadStatus("未读");
                }
            });
        }
        return sysNotices;
    }

    //获取未读数量
    @Override
    public int get_no_read(){
        int number = 0;
        List<SysNotice> sysNotices = this.selectNoticeList(new SysNotice());
        if(StringUtils.isNotNull(sysNotices)){
            for (SysNotice notice: sysNotices) {
                if(notice.getReadStatus().equals("未读")){
                    number ++;
                }
            }
        }
        return number;
    }

    //获取通知公告树
    @Override
    public List<SysNoticeDto> selectNoticeTree() {
        List<SysNoticeDto> dtos = new ArrayList<>();
        SysNoticeDto all = new SysNoticeDto();
        all.setTitle("系统全部消息");
        all.setId("0");


        SysNoticeDto all1 = new SysNoticeDto();
        all1.setTitle("通知");
        all1.setId(Constants.MESSAGE_TYPE_1);

        SysNoticeDto all2 = new SysNoticeDto();
        all2.setTitle("公告");
        all2.setId(Constants.MESSAGE_TYPE_2);

        SysNoticeDto all3 = new SysNoticeDto();
        all3.setTitle("准入管理消息");
        all3.setId(Constants.MESSAGE_TYPE_3);

        SysNoticeDto all4 = new SysNoticeDto();
        all4.setTitle("危化品消息");
        all4.setId(Constants.MESSAGE_TYPE_4);

        SysNoticeDto all5 = new SysNoticeDto();
        all5.setTitle("设备管理消息");
        all5.setId(Constants.MESSAGE_TYPE_5);

        SysNoticeDto all6 = new SysNoticeDto();
        all6.setTitle("预约管理消息");
        all6.setId(Constants.MESSAGE_TYPE_6);

        SysNoticeDto all7 = new SysNoticeDto();
        all7.setTitle("私拿报警");
        all7.setId(Constants.MESSAGE_TYPE_7);

        SysNotice notice = new SysNotice();
        String startTime = null;
        String stopTime = null;
        if(StringUtils.isNotEmpty(notice.getSearchTime()) && notice.getSearchTime().contains("~")){
            String[] times = notice.getSearchTime().split("~");
            startTime = times[0];
            stopTime = times[1];
            Map<String, Object> params =new HashMap<>();
            params.put("beginTime",startTime);
            params.put("endTime",stopTime);
            notice.setParams(params);
        }

        if (SecurityUtils.isAdmin(SecurityUtils.getLoginUser().getUser().getUserId())) {
            //判断是否为管理员
            notice.setRolesList(null);
            notice.setUserId(null);
        }else {
            //根据角色获取数据
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            //TODO 将list中对象的某个属性转成list
            List<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
            if (roleIds != null && roleIds.size()>0) {
                notice.setRolesList(roleIds);
            }
            notice.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        List<SysNotice> sysNotices = noticeMapper.selectNoticeListByUser(notice);
        //根据用户查询已读的消息
        List<SysNoticeUser> sysNoticeUser = noticeUserMapper.findSysNoticeUserListByUserId(SecurityUtils.getLoginUser().getUser().getUserId());

        if(!StringUtils.isNull(sysNotices) && !StringUtils.isNull(sysNoticeUser)){
            sysNotices.stream().forEach(item->{
                if(sysNoticeUser.stream().filter(m->m.getNId().equals(item.getNoticeId())).findAny().isPresent()){
                    item.setReadStatus("已读");
                }else {
                    item.setReadStatus("未读");
                }
            });
        }
        if(!StringUtils.isNull(sysNotices)){
            sysNotices.stream().forEach(item->{
                if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_1)){
                    updateData(item,all1);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_2)){
                    updateData(item,all2);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_3)){
                    updateData(item,all3);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_4)){
                    updateData(item,all4);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_5)){
                    updateData(item,all5);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_6)){
                    updateData(item,all6);
                }else if(item.getNoticeType().equals(Constants.MESSAGE_TYPE_7)){
                    updateData(item,all7);
                }
                updateData(item,all);
            });
        }

        dtos.add(all);
        dtos.add(all1);
        dtos.add(all2);
        dtos.add(all3);
        dtos.add(all4);
        dtos.add(all5);
        dtos.add(all6);
        dtos.add(all7);

        return dtos;
    }

    //设置所有和未读
    private void updateData(SysNotice notice,SysNoticeDto Dto){
        if(StringUtils.isNotNull(Dto.getAll())){
            Dto.setAll(Dto.getAll()+1);
        }else {
            Dto.setAll(1);
        }
        if(notice.getReadStatus().equals("未读")){
            if(StringUtils.isNotNull(Dto.getNoRead())){
                Dto.setNoRead(Dto.getNoRead()+1);
            }else {
                Dto.setNoRead(1);
            }
        }
    }

    /**
     * 新增公告
     *
     * @param dto 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(NoticeDto dto) {
        SysNotice notice = adapterNotice(dto);
        return noticeMapper.insertNotice(notice);
    }

    private SysNotice adapterNotice(NoticeDto dto){
        SysNotice notice = new SysNotice();
        notice.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        notice.setCreateName(SecurityUtils.getUsername());
        notice.setNoticeId(dto.getNoticeId());
        notice.setNoticeContent(dto.getNoticeContent());
        notice.setNoticeType(dto.getNoticeType());
        notice.setNoticeTitle(dto.getNoticeTitle());
        notice.setProvenance(dto.getProvenance());
        notice.setRoleIds(dto.getRoleIds());
        notice.setStaff(dto.getStaff());
        notice.setReadStatus(dto.getReadStatus());
        notice.setReleaseStatus(dto.getReleaseStatus());
        notice.setUserIds(dto.getUserIds());
        return notice;
    }

    /**
     * 修改公告
     *
     * @param dto 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(NoticeDto dto) {
        SysNotice notice = adapterNotice(dto);
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }

    //发送消息
    @Override
    public int sendMessage(SysNotice notice) {
        return noticeMapper.insertNotice(notice);
    }

    //批量查看
    @Override
    public int batchView(Long[] noticeIds){
        int count = 0;
        if(StringUtils.isNotNull(noticeIds)){
            for (Long noticeId:noticeIds) {
                saveReadToNotice(noticeId);//设置消息已读
            }
            count = noticeIds.length;
        }
        return count;
    }

}


