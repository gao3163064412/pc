package com.sygt.common.service;



/**
 * 系统配置
 * @author zhangaijun
 * @date 2018/8/14
 */
public interface ConfigService {


    /**
     * 获取小分子项目编号
     * @return
     */
    String getXItemNo();

    /**
     * 获取大分子项目编号
     * @return
     */
    String getDItemNo();


    /**
     * 获取样品编号
     * @return
     */
    String getSampleNo();

    /**
     * 获取委托单编号
     * @return
     */
    String getEntrustFormNo();

    /**
     * 删除委托单编号
     * @return
     */
    Long decrNumber(String pre);

    /**
     * 获取采购计划编号
     * @return
     */
    String getPurchasePlanNo();

    //获取危化品编号
    String getDangerCode(String code);

    //获取危化品采购编号
    String getDangerApplyCode();

    public String getPlanNoByAcronym (String acronym,Integer size);

    //获取仪器外出申请批号
    String getInstrumentCode(String code);

}
