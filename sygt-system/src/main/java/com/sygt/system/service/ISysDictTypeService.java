package com.sygt.system.service;

import java.util.List;

import com.sygt.common.core.domain.TreeVo;
import com.sygt.common.core.domain.entity.SysDictData;
import com.sygt.common.core.domain.entity.SysDictType;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 字典 业务层
 * @class: ISysDictTypeService
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public interface ISysDictTypeService {
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
     List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
     List<SysDictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
     List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
     SysDictType selectDictTypeById(Long dictId);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
     SysDictType selectDictTypeByType(String dictType);

    /**
     * 批量删除字典信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
     int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 清空缓存数据
     */
     void clearCache();

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
     int insertDictType(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
     int updateDictType(SysDictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
     String checkDictTypeUnique(SysDictType dictType);

    List<TreeVo> selectRoomAndLevel();

    List<TreeVo> selectChildListByType(String parentDictType,String childDictType);
}
