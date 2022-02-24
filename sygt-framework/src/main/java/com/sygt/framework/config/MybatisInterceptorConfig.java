package com.sygt.framework.config;

import com.sygt.framework.interceptor.MybatisInterceptorProperties;
import com.sygt.framework.interceptor.StuffInterceptor;
import com.sygt.framework.web.service.DefaultOperInfoImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MybatisInterceptorProperties.class})// 激活配置模块
@ConditionalOnProperty(name = {"my.interceptor.enabled"}, matchIfMissing = true, havingValue = "true")
public class MybatisInterceptorConfig {

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
        StuffInterceptor stuffInterceptor = new StuffInterceptor();
        stuffInterceptor.setOperInfo(new DefaultOperInfoImpl());
        sqlSessionFactory.getConfiguration().addInterceptor(stuffInterceptor);

        return "interceptor";
    }
}
