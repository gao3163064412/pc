package com.sygt.system.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LayoutSetting implements Serializable {

    //主题风格
    private String sideTheme;

    //主题颜色
    private String theme;

    //开启topNav
    private Boolean topNav;

    //开启tagsView
    private Boolean tagsView;

    //开启 fixedHeader
    private Boolean fixedHeader;

    //开启 sidebarLogo
    private Boolean sidebarLogo;

}
