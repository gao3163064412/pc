package com.sygt.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 获取拼音首字母工具类
 * @author cm
 * @version 1.0
 * @description: TODO
 * @date 2021/9/1 17:25
 */
public class PinyinHelperUtil {
    /**
     * 得到中文首字母（中国 -> ZG）
     * @param str 需要转化的中文字符串
     * @return 大写首字母缩写的字符串
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();
    }

}
