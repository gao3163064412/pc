package com.sygt.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NoticeListDto {

    private static final long serialVersionUID = 1L;


    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 时间区间（yyyy-MM-dd~yyyy-MM-dd）
     */
    private String searchTime;


}
