package com.sygt.system.service.impl;

import java.util.List;

import com.sygt.common.utils.DateUtils;
import com.sygt.system.domain.SysSignature;
import com.sygt.system.mapper.SysSignatureMapper;
import com.sygt.system.service.ISysSignatureService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sygt.common.utils.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import com.sygt.common.constant.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 签章管理Service业务层处理
 *
 * @author wangtong
 * @date 2021-06-01
 */
@Service
public class SysSignatureServiceImpl extends ServiceImpl<SysSignatureMapper, SysSignature> implements ISysSignatureService {

    /**
     * 查询签章管理
     *
     * @param id 签章管理ID
     * @return 签章管理
     */
    @Override
    public SysSignature selectSysSignatureById(Long id) {
        return this.baseMapper.selectSysSignatureById(id);
    }

    /**
     * 查询签章管理列表
     *
     * @param sysSignature 签章管理
     * @return 签章管理
     */
    @Override
    public List<SysSignature> selectSysSignatureList(SysSignature sysSignature) {
        return this.baseMapper.selectSysSignatureList(sysSignature);
    }

    /**
     * 根据QueryWrapper查询签章管理列表
     *
     * @param sysSignature 签章管理
     * @return 签章管理集合
     */
    @Override
    public List<SysSignature> SysSignatureList(SysSignature sysSignature) {
        QueryWrapper<SysSignature> queryWrapper = queryWrapper(sysSignature);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 设置QueryWrapper 查询条件
     *
     * @param sysSignature 签章管理
     * @return 签章管理集合
     */
    private QueryWrapper<SysSignature> queryWrapper(SysSignature sysSignature) {

        String searchValue = sysSignature.getSearchValue();

        QueryWrapper<SysSignature> queryWrapper = new QueryWrapper<SysSignature>();


        queryWrapper.like(ObjectUtils.isNotEmpty(sysSignature.getSignatureName()), "signature_name", sysSignature.getSignatureName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(sysSignature.getIsSignatureType()), "is_signature_type", sysSignature.getIsSignatureType());

        queryWrapper.eq("del_flag", Constants.DEL_FLAG)
                .and(StringUtils.isNotBlank(searchValue),
                        c -> c.like("*", searchValue).or().like("*", searchValue))
                .orderByDesc("create_time");

        return queryWrapper;

    }


    /**
     * 新增签章管理
     *
     * @param sysSignature 签章管理
     * @return 结果
     */
    @Override
    public int insertSysSignature(SysSignature sysSignature) {
        sysSignature.setCreateTime(DateUtils.getNowDate());
        return this.baseMapper.insertSysSignature(sysSignature);
    }

    /**
     * 修改签章管理
     *
     * @param sysSignature 签章管理
     * @return 结果
     */
    @Override
    public int updateSysSignature(SysSignature sysSignature) {
        sysSignature.setUpdateTime(DateUtils.getNowDate());
        return this.baseMapper.updateSysSignature(sysSignature);
    }

    /**
     * 批量删除签章管理
     *
     * @param ids 需要删除的签章管理ID
     * @return 结果
     */
    @Override
    public int deleteSysSignatureByIds(Long[] ids) {
        return this.baseMapper.deleteSysSignatureByIds(ids);
    }

    /**
     * 删除签章管理信息
     *
     * @param id 签章管理ID
     * @return 结果
     */
    @Override
    public int deleteSysSignatureById(Long id) {
        return this.baseMapper.deleteSysSignatureById(id);
    }
}
