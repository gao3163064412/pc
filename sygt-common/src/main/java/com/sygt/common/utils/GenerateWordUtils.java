package com.sygt.common.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * 动态生成word文档
 */
public class GenerateWordUtils {
    /**
     * 
     * @param templatePath  模板文件路径
     * @param filePath        导出word文件路径
     * @param map            传入需要导出的数据
     * @throws Exception
     */
    public static void templateToWord(String templatePath, String filePath, Map<String,Object> map) throws Exception {
        try {
            // 先创建文件
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            XWPFDocument docx = WordExportUtil.exportWord07(templatePath, map);

            FileOutputStream fos = new FileOutputStream(file);
            // 写入本地服务器
            docx.write(fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

}
