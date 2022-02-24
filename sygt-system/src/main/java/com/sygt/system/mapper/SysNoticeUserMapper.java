package com.sygt.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sygt.system.domain.SysNoticeUser;

import java.util.List;

/**
 * 通知公告阅读Mapper接口
 *
 * @author wt
 * @date 2021-08-20
 */
public interface SysNoticeUserMapper extends BaseMapper<SysNoticeUser> {
    /**
     * 查询通知公告阅读
     *
     * @param id 通知公告阅读ID
     * @return 通知公告阅读
     */
    public SysNoticeUser selectSysNoticeUserById(Long id);

    /**
     * 查询通知公告阅读列表
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 通知公告阅读集合
     */
    public List<SysNoticeUser> selectSysNoticeUserList(SysNoticeUser sysNoticeUser);

    /**
     * 新增通知公告阅读
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 结果
     */
    public int insertSysNoticeUser(SysNoticeUser sysNoticeUser);

    /**
     * 修改通知公告阅读
     *
     * @param sysNoticeUser 通知公告阅读
     * @return 结果
     */
    public int updateSysNoticeUser(SysNoticeUser sysNoticeUser);

    /**
     * 删除通知公告阅读
     *
     * @param id 通知公告阅读ID
     * @return 结果
     */
    public int deleteSysNoticeUserById(Long id);

    /**
     * 批量删除通知公告阅读
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNoticeUserByIds(Long[] ids);

    //根据用户查询已读的消息
    List<SysNoticeUser> findSysNoticeUserListByUserId(Long userId);

}
