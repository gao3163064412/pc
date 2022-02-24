package com.sygt.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sygt.system.domain.SysSignature;

/**
 * 签章管理Mapper接口
 *
 * @author wangtong
 * @date 2021-06-01
 */
public interface SysSignatureMapper extends BaseMapper<SysSignature> {
    /**
     * 查询签章管理
     *
     * @param id 签章管理ID
     * @return 签章管理
     */
    public SysSignature selectSysSignatureById(Long id);

    /**
     * 查询签章管理列表
     *
     * @param sysSignature 签章管理
     * @return 签章管理集合
     */
    public List<SysSignature> selectSysSignatureList(SysSignature sysSignature);

    /**
     * 新增签章管理
     *
     * @param sysSignature 签章管理
     * @return 结果
     */
    public int insertSysSignature(SysSignature sysSignature);

    /**
     * 修改签章管理
     *
     * @param sysSignature 签章管理
     * @return 结果
     */
    public int updateSysSignature(SysSignature sysSignature);

    /**
     * 删除签章管理
     *
     * @param id 签章管理ID
     * @return 结果
     */
    public int deleteSysSignatureById(Long id);

    /**
     * 批量删除签章管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSignatureByIds(Long[] ids);
}
