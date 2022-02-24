package com.sygt.common.utils;

import com.sygt.common.constant.Constants;
import com.sygt.common.config.LimsConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateWordUtil {


    /**
     * word动态导出代码
     */
    public static Map<String,String> createWord(Map<String, Object> dataMap , HttpServletResponse response, String fileName) {
        //读取文件
        return getWriter(dataMap,response,fileName);
    }


    /**
     * 读取文件
     *
     * @param dataMap 数据
     * @return
     */
    private static Map<String,String> getWriter(Map<String, Object> dataMap,HttpServletResponse response,String fileName) {
        Map<String,String> map = new HashMap<>();
        String filePath;
        SimpleDateFormat si=new SimpleDateFormat("yyyyMMddHHmmss");
        //获得当前系统时间
        String time=si.format(new Date());
        String pa = fileName+time;
        try {
            //Configuration 用于读取ftl文件
            Configuration configuration = new Configuration(new Version("2.3.28"));
            configuration.setDefaultEncoding("utf-8");
            /**
             * 以下是两种指定ftl文件所在目录路径的方式，注意这两种方式都是
             * 指定ftl文件所在目录的路径，而不是ftl文件的路径
             */
            //指定路径的第一种方式（根据某个类的相对路径指定）
//                configuration.setClassForTemplateLoading(this.getClass(), "");
            //指定路径的第二种方式，我的路径是C：/a.ftld
//            configuration.setDirectoryForTemplateLoading(new File("D:\\fzzx\\freemarker\\template\\"));
            configuration.setDirectoryForTemplateLoading(new File(LimsConfig.getUploadPath()+ Constants.TEMPLATE_FTL_PATH_1));
            //路径指定到当前电脑桌面
            File outFile = new File(LimsConfig.getUploadPath()+Constants.TEMPLATE_FTL_PATH_2+pa+".doc");
            //以utf-8的编码读取ftl文件
            Template template = configuration.getTemplate(fileName+".ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            template.process(dataMap, out);
            out.close();
        } catch (Exception e) {
            System.err.println("getWriter:printStackTraceException");
            e.printStackTrace();
        }finally {
            String fileSavePath = LimsConfig.getUploadPath();
            fileSavePath = fileSavePath + Constants.TEMPLATE_FTL_PATH_2;
            filePath = fileSavePath+pa+".doc";
            if(response !=null ){
                //上传路径设置
                try {
//                    FileDownload.fileDownload(response,filePath,pa+".doc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        map.put("fileName",pa+".doc");
        map.put("filePath",filePath);
        map.put("name",pa);
        return map;
    }



}
