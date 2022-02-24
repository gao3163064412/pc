package com.sygt.system.mapper;

import java.util.List;

import com.sygt.common.core.domain.TreeVo;
import com.sygt.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import com.sygt.system.domain.SysUserRole;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 用户与角色关联表 数据层
 * @class: SysUserRoleMapper
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface SysUserRoleMapper {
    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
     int deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
     int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
     int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 删除用户和角色关联信息
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
     int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
     int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

    List<Long> selectRoleIdsByUid(Long id);

    List<TreeVo> selectUserByRole(@Param("roleId") Long roleId,@Param("deptId") Long deptId);


    //通过角色id获取用户信息
    List<SysUser> selectUser(@Param("roleId") Long roleId);
}
