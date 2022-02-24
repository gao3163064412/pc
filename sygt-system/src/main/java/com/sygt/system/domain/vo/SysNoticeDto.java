package com.sygt.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysNoticeDto implements Serializable {

    //标题
    private String title;

    //全部消息
    private Integer all = 0;

    //未读消息
    private Integer noRead = 0;

    //id
    private String id;

}
