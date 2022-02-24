package com.sygt.common.service.impl;

import com.sygt.common.constant.Constants;
import com.sygt.common.utils.DateUtils;
import com.sygt.common.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangaijun
 * @date 2018/8/14
 */
@Service
public class ConfigServiceImpl implements ConfigService {


    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public String getXItemNo() {
        String pre = "X";
        int length = 3;
        return incrNumber(pre, length, Constants.ITEM);
    }

    @Override
    public String getDItemNo() {
        String pre = "D";
        int length = 3;
        return incrNumber(pre, length, Constants.ITEM);
    }

    @Override
    public String getSampleNo() {
        String pre = "YP";
        return generateOrderNo(pre, 4);
    }

    /**
     * 委托单编号
     * @return
     */
    @Override
    public String getEntrustFormNo() {
        String pre = "WT";
        return generateFormNo(pre, 3);
    }
    @Override
    public String getPurchasePlanNo() {
        String pre = "JH";
        return generateFormNo(pre, 4);
    }

    /**
     *通过首字母缩写获取编码号
     * @param acronym 缩写字母
     * @return
     */
    @Override
    public String getPlanNoByAcronym (String acronym,Integer size) {
        return generateFormNo(acronym, size);
    }

    //获取仪器外出申请批号
    @Override
    public String getInstrumentCode(String code) {
        return generateFormNo(code, 3);
    }

    //获取危化品编号
    @Override
    public String getDangerCode(String code) {
        return generateFormNo(code, 2);
    }

    //获取危化品采购编号
    @Override
    public String getDangerApplyCode() {
        String pre = "CG";
        return generateFormNo(pre, 3);
    }

    private String incrNumber(String pre, int length, String redisKey) {
        String key = pre + "_incr_number";
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(redisKey + key, redisTemplate.getConnectionFactory());
        Long incrNeedNum = entityIdCounter.getAndIncrement();
        if (incrNeedNum == 0) {
            incrNeedNum = entityIdCounter.getAndIncrement();
        }
        String lengthFormat = "%0" + length + "d";
        //格式化为length位数字前面补0
        return String.format(lengthFormat, incrNeedNum);
    }


    private String incrOrderNo(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("order_no:" + key, redisTemplate.getConnectionFactory());
        Long incrNeedNum = entityIdCounter.getAndIncrement();
        if (incrNeedNum == 0) {
            incrNeedNum = entityIdCounter.getAndIncrement();
        }
        String lengthFormat = "%0" + 4 + "d";
        //格式化为length位数字前面补0
        return String.format(lengthFormat, incrNeedNum);
    }


    private String generateOrderNo(String pre, int length) {
        String incrNumber = incrNumber(pre, length, Constants.SAMPLE);
        return pre + DateUtils.parseDateToStr("yyyyMMdd", new Date()) + incrNumber;
    }

    private String generateFormNo(String pre, int length) {
        String incrNumber = incrNumber(pre, length, Constants.ENTRUST_FORM);
        return pre + DateUtils.parseDateToStr("yyyyMMdd", new Date()) + incrNumber;
    }

    public Long decrNumber(String pre){
        String key = pre + "_incr_number";
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(Constants.ENTRUST_FORM + key, redisTemplate.getConnectionFactory());
        Long decrNeedNum = entityIdCounter.getAndDecrement();
        return decrNeedNum;
    }
}
