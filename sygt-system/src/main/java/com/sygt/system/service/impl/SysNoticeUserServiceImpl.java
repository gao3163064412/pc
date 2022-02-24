package com.sygt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sygt.common.constant.Constants;
import com.sygt.common.utils.StringUtils;
import com.sygt.system.domain.SysNoticeUser;
import com.sygt.system.mapper.SysNoticeUserMapper;
import com.sygt.system.service.ISysNoticeUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知公告阅读Service业务层处理
 *
 * @author wt
 * @date 2021-08-20
 */
@Service
public class SysNoticeUserServiceImpl extends ServiceImpl<SysNoticeUserMapper, SysNoticeUser> implements ISysNoticeUserService {

    /**
     * 查询通知公告阅读
     *
     * @param id 通知公告阅读ID
     * @return 通知公告阅读
     */
    @Override
    public SysNoticeUser selectSysNoticeUserById(Long id) {
        return this.baseMapper.selectSysNoticeUserById(id);
    }

    /**
     * 查询通知公告阅读列表
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 通知公告阅读
     */
    @Override
    public List<SysNoticeUser> selectSysNoticeUserList(SysNoticeUser sysNoticeUser) {
        return this.baseMapper.selectSysNoticeUserList(sysNoticeUser);
    }

    /**
     * 根据QueryWrapper查询通知公告阅读列表
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 通知公告阅读集合
     */
    @Override
    public List<SysNoticeUser> SysNoticeUserList(SysNoticeUser sysNoticeUser) {
        QueryWrapper<SysNoticeUser> queryWrapper = queryWrapper(sysNoticeUser);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 设置QueryWrapper 查询条件
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 通知公告阅读集合
     */
    private QueryWrapper<SysNoticeUser> queryWrapper(SysNoticeUser sysNoticeUser) {

        String searchValue = sysNoticeUser.getSearchValue();

        QueryWrapper<SysNoticeUser> queryWrapper = new QueryWrapper<SysNoticeUser>();


        queryWrapper.eq(ObjectUtils.isNotEmpty(sysNoticeUser.getNId()), "n_id", sysNoticeUser.getNId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(sysNoticeUser.getUserId()), "user_id", sysNoticeUser.getUserId());

        queryWrapper.eq("del_flag", Constants.DEL_FLAG)
                .and(StringUtils.isNotBlank(searchValue),
                        c -> c.like("*", searchValue).or().like("*", searchValue))
                .orderByDesc("create_time");

        return queryWrapper;

    }


    /**
     * 新增通知公告阅读
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 结果
     */
    @Override
    public int insertSysNoticeUser(SysNoticeUser sysNoticeUser) {
        return this.baseMapper.insertSysNoticeUser(sysNoticeUser);
    }

    /**
     * 修改通知公告阅读
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 结果
     */
    @Override
    public int updateSysNoticeUser(SysNoticeUser sysNoticeUser) {
        return this.baseMapper.updateSysNoticeUser(sysNoticeUser);
    }

    /**
     * 批量删除通知公告阅读
     *
     * @param ids 需要删除的通知公告阅读ID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeUserByIds(Long[] ids) {
        return this.baseMapper.deleteSysNoticeUserByIds(ids);
    }

    /**
     * 删除通知公告阅读信息
     *
     * @param id 通知公告阅读ID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeUserById(Long id) {
        return this.baseMapper.deleteSysNoticeUserById(id);
    }
}
