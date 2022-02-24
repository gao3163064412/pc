package com.sygt.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sygt.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysNoticeVo implements Serializable {

    /**
     * 公告标题
     */
    @Excel(name = "标题")
    private String noticeTitle;

    /**
     * 发布者
     */
    @Excel(name = "发布者")
    private String createName;

    //发布状态(0:未发布 1:已发布)
    @Excel(name = "发布状态")
    private String releaseStatus;

    //阅读状态
    @Excel(name = "读取状态")
    private String readStatus;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
