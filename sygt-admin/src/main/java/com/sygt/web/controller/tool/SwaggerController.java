package com.sygt.web.controller.tool;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sygt.common.core.controller.BaseController;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: swagger 接口
 * @class: SwaggerController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController {
    @PreAuthorize("@ss.hasPermi('tool:swagger:view')")
    @GetMapping()
    public String index() {
        return redirect("/swagger-ui.html");
    }
}
