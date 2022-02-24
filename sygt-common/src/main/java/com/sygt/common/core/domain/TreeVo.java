package com.sygt.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class TreeVo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeVo> children;


}
