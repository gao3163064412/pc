package com.sygt.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sygt.common.annotation.AutoStuff;
import com.sygt.common.annotation.Excel;
import com.sygt.common.enums.AnnoEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.sygt.common.core.domain.BaseEntity;

/**
 * 签章管理对象 sys_signature
 *
 * @author wangtong
 * @date 2021-06-01
 */
@Data
@Accessors(chain = true)
public class SysSignature extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @AutoStuff(annoType = AnnoEnum.ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 签章名称
     */
    @Excel(name = "签章名称")
    private String signatureName;

    /**
     * 签章类型（CMA、CSMA、合同章、业务章）
     */
    @Excel(name = "签章类型", readConverterExp = "CMA、CSMA、合同章、业务章")
    private String isSignatureType;
    /**
     * 签章类型名称
     */
    @TableField(exist = false)
    private String signatureTypeName;

    /**
     * 图片路径
     */
    @Excel(name = "图片路径")
    private String picturePath;

    /**
     * 是否已删除（0未删除 1 已删除）
     */
    @AutoStuff(annoType = AnnoEnum.DEL_FLAG)
    private Integer delFlag;

    /**
     * 版本号
     */
    @AutoStuff(annoType = AnnoEnum.VERSION)
    private Integer version;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("signatureName", getSignatureName())
                .append("isSignatureType", getIsSignatureType())
                .append("picturePath", getPicturePath())
                .append("createUserId", getCreateUserId())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateUserId", getUpdateUserId())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("version", getVersion())
                .toString();
    }
}
