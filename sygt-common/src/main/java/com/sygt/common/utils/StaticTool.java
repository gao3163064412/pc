package com.sygt.common.utils;

public class StaticTool {

    /**
     * 拼接IN条件值
     * @param str
     * @return
     */
    public static String getInCond(String str) {
        String[] strArr = str.split(",");
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < strArr.length; i++){
            if(i == strArr.length-1){
                sb.append("'"+strArr[i]+"'");
            }else{
                sb.append("'"+strArr[i]+"'"+",");
            }
        }
        return sb.toString();
    }

}
