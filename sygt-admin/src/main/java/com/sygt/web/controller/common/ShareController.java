package com.sygt.web.controller.common;

import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.core.domain.entity.SysDept;
import com.sygt.common.core.domain.entity.SysRole;
import com.sygt.common.utils.SecurityUtils;
import com.sygt.common.utils.StringUtils;
import com.sygt.system.service.ISysDeptService;
import com.sygt.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "公共接口", tags = "公共接口")
@RestController
@RequestMapping("/share")
public class ShareController  {

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysRoleService roleService;

    //获取二级单位下拉列表接口
    @ApiOperation(value = "获取二级单位下拉列表接口", notes = "获取二级单位下拉列表接口")
    @GetMapping("/get_dept_list")
    public AjaxResult getDeptList(){
        List<SysDept> deptList = this.sysDeptService.selectAllDeptList(new SysDept());
        if(StringUtils.isNotNull(SecurityUtils.getLoginUser().getUser().getDeptId())){
            Long deptId = SecurityUtils.getLoginUser().getUser().getDeptId();
            deptList.stream().forEach(e->{
                if(e.getDeptId().longValue() == deptId.longValue()){
                    e.setSelect(true);
                }else {
                    e.setSelect(false);
                }
            });
        }
        return AjaxResult.success(deptList);
    }

    //获取角色信息列表
    @ApiOperation(value = "获取角色信息列表", notes = "获取角色信息列表", position = 2)
    @GetMapping("/getRoles")
    public AjaxResult getRoles() {
        List<SysRole> roles = roleService.selectRoleAll();
        return AjaxResult.success(roles);
    }

}
