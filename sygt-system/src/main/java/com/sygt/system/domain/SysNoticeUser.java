package com.sygt.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sygt.common.annotation.AutoStuff;
import com.sygt.common.annotation.Excel;
import com.sygt.common.core.domain.BaseEntity;
import com.sygt.common.enums.AnnoEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通知公告阅读对象 sys_notice_user
 *
 * @author wt
 * @date 2021-08-20
 */
@Data
@Accessors(chain = true)
public class SysNoticeUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @AutoStuff(annoType = AnnoEnum.ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 消息ID
     */
    @Excel(name = "消息ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long nId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    //阅读状态
    private String  read;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("nId", getNId())
                .append("userId", getUserId())
                .toString();
    }
}
