package com.sygt.framework.web.service;

import com.sygt.common.core.domain.model.LoginUser;
import com.sygt.common.utils.ServletUtils;
import com.sygt.common.utils.SnowflakeIdWorker;
import com.sygt.common.utils.spring.SpringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Component
public class DefaultOperInfoImpl {

    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    //获取ID
    public Long getId() {
        long id = idWorker.nextId();
        return id;
    }

    public Long getOperUserLong() {
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if(loginUser != null){
            return loginUser.getUser().getUserId();
        }
        return null;
    }

    public String getOperUserString() {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if(loginUser != null){
            return loginUser.getUser().getUserId().toString();
        }
        return null;
    }

    //获取用户名称
    public String getOperUserNameString() {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if(loginUser != null){
            return loginUser.getUser().getUserName();
        }
        return null;
    }

    //获取删除标识
    public Long getDelFlagByLong() {
        return 0L;
    }

    public Integer getDelFlagByInteger() {
        return 0;
    }

    //获取版本号
    public Long getVersionByLong() {
        return 0L;
    }

    public Integer getVersionByInteger() {
        return 0;
    }


    public String getString() {
        return "";
    }

    public Long getLong() {
        return 0L;
    }

    public Date getDate() {
        return new Date();
    }

    public Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }


}
