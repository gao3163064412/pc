package com.sygt.framework.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 通过@ConfigurationProperties根据prefix从配置文件中读取配置块
 * ignoreInvalidFields: 为了避免消费者填的字段不符合我们规定的字段，无法解析而导致启动报错
 * 	比如enabled消费者填的是abc，不是boolean类型。
 * 	设置了ignoreInvalidFields，就会忽略配置文件中填的错误内容，而使用我们规定的默认值，如果没有默认值则为null
 *
 * @date: 2021/05/20 10:51:52
 * @author : zhang'ai'jun
 */
@ConfigurationProperties(prefix = "my.interceptor", ignoreInvalidFields = true)
public class MybatisInterceptorProperties {
    // 默认为true
    private Boolean enabled = Boolean.TRUE;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
