package com.sygt.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sygt.common.constant.UserConstants;
import com.sygt.common.core.domain.FileVo;
import com.sygt.common.core.domain.entity.SysUser;
import com.sygt.common.main.PublicDate;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配常用参数工具类
 * @author cm
 * @version 1.0
 * @description: TODO
 * @date 2021/9/16 9:42
 */
public class AdapterParameterUtil {

    /**
     * 将接收到的参数适配成具体的开始与结束时间
     * @param dateTime
     * @return
     */
    public static PublicDate AdapterDate(List<String> dateTime){
        PublicDate publicDate = new PublicDate();
        if(dateTime!=null && dateTime.size()==2){
            publicDate.setBeginDate(dateTime.get(0));
            publicDate.setEndDate(dateTime.get(1));
        }
        return publicDate;
    }

    /**
     * 校验危化品模块拥有耍赖权限的角色
     * @return
     */
    public static Boolean dangerSuperRole(){
        List<Long> roleIds = SecurityUtils.getRoleIds();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        boolean b = false;
        if(user.isAdmin() || roleIds.contains(UserConstants.ROLE_103) || roleIds.contains(UserConstants.ROLE_106) || roleIds.contains(UserConstants.ROLE_111)) {
            b=true;
        }
        return b;
    }

    /**
     * 校验两个集合是否存在相同的元素
     * @return
     */
    public static Boolean checkEqualList(List<Long> oneList,List<Long> twoList){
        List<Long> copyList = oneList;
        oneList.retainAll(twoList);
        boolean b = true;
        if(copyList.size()!=oneList.size()){
            //存在相同的元素
            b=true;
        }
        return b;
    }
    /**
     * 文件适配
     * @return
     */
    public static List<FileVo> AdapterFile(String fileName,String fileUrl){
        List<FileVo> fileVos = new ArrayList<>();
        if (StringUtils.isNotEmpty(fileName) && StringUtils.isNotEmpty(fileUrl)){
            FileVo fileVo = new FileVo();
            fileVo.setName(fileName);
            fileVo.setUrl(fileUrl);
            fileVos.add(fileVo);
        }
        return fileVos;
    }

    /**
     * 文件适配
     * @return
     */
    public static List<FileVo> AdapterFile(String jsonStr){
        List<FileVo> fileVos = new ArrayList<>();
        if (StringUtils.isNotEmpty(jsonStr)){
            JSONArray array = JSONArray.parseArray(jsonStr);
            for (Object obj:array) {
                FileVo fileVo = new FileVo();
                JSONObject file = JSONObject.parseObject(obj.toString());
                String name = file.getString("name");
                String url = file.getString("url");
                fileVo.setName(name);
                fileVo.setUrl(url);
                fileVos.add(fileVo);
            }
        }
        return fileVos;
    }
    /**
     * 校验仪器模块拥有耍赖权限的角色
     * @return
     */
    public static Boolean instrumentSuperRole(){
        List<Long> roleIds = SecurityUtils.getRoleIds();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        boolean b = false;
        if(user.isAdmin() || roleIds.contains(UserConstants.ROLE_103) || roleIds.contains(UserConstants.ROLE_100) || roleIds.contains(UserConstants.ROLE_101)) {
            b=true;
        }
        return b;
    }

}
