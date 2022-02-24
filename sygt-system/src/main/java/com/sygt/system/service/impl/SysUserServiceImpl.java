package com.sygt.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sygt.common.annotation.DataScope;
import com.sygt.common.constant.Constants;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.domain.TreeVo;
import com.sygt.common.core.domain.entity.SysRole;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.exception.CustomException;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.common.utils.spring.SpringUtils;
import com.sygt.system.domain.SysPost;
import com.sygt.system.mapper.*;
import com.sygt.system.service.ISysConfigService;
import com.sygt.system.service.ISysUserService;
import com.sygt.system.domain.SysUserPost;
import com.sygt.system.domain.SysUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 用户 业务层处理
 * @class: SysUserServiceImpl
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>  implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化用户到缓存
     */
    @PostConstruct
    public void init() {
        SysUser user = new SysUser();
        user.setDelFlag(String.valueOf(Constants.DEL_FLAG));
        List<SysUser> userList = userMapper.selectUserList(new SysUser());
        if(userList != null && !userList.isEmpty()){
            redisCache.setCacheList(Constants.SYS_USER_KEY + "user_list",userList);
        }
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        if(StringUtils.isNotNull(user.getDeptId())){
            if(user.getDeptId().toString().equals(Constants.HKY_DEPT_ID_01)){
                user.setDeptId(null);
            }

        }
        return userMapper.selectUserList(user);
    }

    @Override
    public List<SysUser> getUserList(SysUser user) {
        List<SysUser> list =userMapper.getUserList(user);
        return list;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        // 清空缓存
        this.clearUserCache();
        // 重新加载缓存
        this.init();
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        // 修改用户信息
        int i = userMapper.updateUser(user);
        // 清空缓存
        this.clearUserCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        // 修改用户状态信息
        int i = userMapper.updateUserStatus(user);
        // 清空缓存
        this.clearUserCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }
    @Override
    public int batchUpdateStatus(List<Long> visitorIds) {
        return userMapper.batchUpdateStatus(visitorIds);
    }

    /**
     * 更改培训状态
     * @param chenckTime
     * @return
     */
    @Override
    public int updateTrainStatus(Boolean chenckTime) {
        int status = 0;
        if(chenckTime){
            status = 1;
        }
        Long userId = SecurityUtils.getUserId();
        return userMapper.updateTrainStatus(status,userId);
    }

    /**
     * 更改所有用户阅读状态为未读
     * @return
     */
    @Override
    public int updateAllNoticeStatus() {
        return userMapper.updateAllNoticeStatus(0);
    }
    /**
     * 查找所有访客的用户名
     * @return
     */
    @Override
    public List<String> selectAllUserName() {
        return  userMapper.selectAllUserName();
    }

    @Override
    public List<SysUser> studylist(SysUser user) {
        if(StringUtils.isNotNull(user.getDeptId())){
            if(user.getDeptId().toString().equals(Constants.HKY_DEPT_ID_01)){
                user.setDeptId(null);
            }

        }
        return userMapper.studylist(user);
    }

    @Override
    public List<Map<String,String>> selectUserByRole(Long roleId,Long deptId) {
        return userMapper.selectUserByRole(roleId,deptId);
    }

    public List<TreeVo> selectListByRole(Long roleId,Long deptId){
        return userRoleMapper.selectUserByRole(roleId,deptId);
    }

    /**
     * 重置密码
     * @param password
     * @param id
     * @return
     */
    @Override
    public int resetPassword(String password,Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        return userMapper.updateUser(sysUser);
    }


    /**
     * 查找用户角色
     * @param id
     * @return
     */
    @Override
    public List<Long> findRolesByUserId(Long id) {
        return  userRoleMapper.selectRoleIdsByUid(id);
    }



    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        // 删除用户信息
        int i = userMapper.deleteUserById(userId);
        // 清空缓存
        this.clearUserCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        // 删除用户信息
        int i = userMapper.deleteUserByIds(userIds);
        // 清空缓存
        this.clearUserCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateName(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateName(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 清空用户缓存
     */
    public static void clearUserCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_USER_KEY + "user_list");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }
}
