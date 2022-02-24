package com.sygt.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class FileVo implements Serializable
{

    /** 名称 */
    private String name;

    /** 地址 */
    private String url;


    @Override
    public String toString() {
        return "{" +
                "name:'" + name + '\'' +
                ", url:'" + url + '\'' +
                '}';
    }
}
