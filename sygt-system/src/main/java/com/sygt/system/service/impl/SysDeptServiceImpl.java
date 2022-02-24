package com.sygt.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.core.redis.RedisCache;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.spring.SpringUtils;
import com.sygt.system.mapper.SysDeptMapper;
import com.sygt.system.mapper.SysRoleDeptMapper;
import com.sygt.system.mapper.SysRoleMapper;
import com.sygt.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sygt.common.annotation.DataScope;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.domain.TreeSelect;
import com.sygt.common.core.domain.entity.SysDept;
import com.sygt.common.core.domain.entity.SysRole;
import com.sygt.common.exception.CustomException;
import com.sygt.common.utils.StringUtils;

import javax.annotation.PostConstruct;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 部门管理 服务实现
 * @class: SysDeptServiceImpl
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RedisCache  redisCache;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 项目启动时，初始化用户到缓存
     */
    @PostConstruct
    public void init() {
        SysDept dept = new SysDept();
        dept.setDelFlag(String.valueOf(Constants.DEL_FLAG));
        List<SysDept> deptList = deptMapper.selectDeptList(new SysDept());
        if(deptList != null && !deptList.isEmpty()){
            redisCache.setCacheList(Constants.SYS_DEPT_KEY + "dept_list",deptList);
        }
    }

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        if(StringUtils.isEmpty(dept.getType())){
            dept.setType("staff");
        }
        return deptMapper.selectDeptList(dept);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectAllDeptList(SysDept dept) {
        if(StringUtils.isEmpty(dept.getType())){
            dept.setType("all");
        }
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new CustomException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        // 新增部门信息
        int i = deptMapper.insertDept(dept);
        // 清空缓存
        this.clearDeptCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        // 清空缓存
        this.clearDeptCache();
        // 重新加载缓存
        this.init();
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
        String updateBy = dept.getUpdateUserId();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateUserId(updateBy);
        deptMapper.updateDeptStatus(dept);
        // 清空缓存
        this.clearDeptCache();
        // 重新加载缓存
        this.init();
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        // 删除部门信息
        int i = deptMapper.deleteDeptById(deptId);
        // 清空缓存
        this.clearDeptCache();
        // 重新加载缓存
        this.init();
        return i;
    }

    /**
     * 查询二级单位
     * @return
     */
    @Override
    public List<SysDept> selectChildList(Long deptId) {
        if(deptId!=null){
            List<Long> deptIds = new ArrayList<>();
            deptIds.add(deptId);
            return deptMapper.selectChildList(deptIds);
        }else {
            return deptMapper.selectChildList(null);
        }
    }
    @Override
    public List<SysDept> selectListByRole() {
        List<Long> deptIds = selectDeptIdsByRole();
        return deptMapper.selectChildList(deptIds);
    }

    public List<Long> selectDeptIdsByRole(){
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<Long> deptIds = new ArrayList<>();
        for (SysRole role:user.getRoles()) {
            if(Constants.DATA_ROLE_1.equals(role.getDataScope())){
                deptIds = null;
                break;
            }else if(Constants.DATA_ROLE_3.equals(role.getDataScope())){
                deptIds.add(user.getDeptId());
            }else {
                List<Long> deptList = roleDeptMapper.selectByRoleId(role.getRoleId());
                deptIds.addAll(deptList);
            }
        }
        return deptIds;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 清空用户缓存
     */
    public static void clearDeptCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_DEPT_KEY +"dept_list");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }


    public Long selectIdByName(String deptName) {
        return deptMapper.selectIdByName(deptName);
    }
}
