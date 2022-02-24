package com.sygt.web.controller.common;

import com.sygt.common.config.LimsConfig;
import com.sygt.common.constant.Constants;
import com.sygt.common.core.domain.AjaxResult;
import com.sygt.common.utils.StringUtils;
import com.sygt.common.utils.file.FileUploadUtils;
import com.sygt.common.utils.file.FileUtils;
import com.sygt.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 通用请求处理
 * @class: CommonController
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
@Api(value = "通用接口", tags = "通用接口")
@RestController
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {

        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
//            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String realFileName = fileName.substring(fileName.indexOf("_") + 1);
            String filePath = LimsConfig.getDownloadPath() + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 试卷下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/examDownload")
    public void ftlDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {

        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = fileName.substring(fileName.indexOf("_") + 1);
            String filePath = LimsConfig.getExamDownloadPath() + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation(value = "多文件上传", notes = "多文件上传", position = 2)
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = LimsConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("size", file.getSize());
            ajax.put("fileName", fileName);
            ajax.put("url", fileName);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation(value = "多文件上传(不需要认证)", notes = "多文件上传(不需要认证)", position = 2)
    @PostMapping("/common/noLoginUpload")
    public AjaxResult noLoginUpload(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = LimsConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("size", file.getSize());
            ajax.put("fileName", fileName);
            ajax.put("url", fileName);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 上传文件请求（转换word文档为pdf）
     */
    @ApiOperation(value = "文件上传（转换word文档为pdf）", notes = "文件上传（转换word文档为pdf）")
    @PostMapping("/common/uploadtopdf")
    public AjaxResult uploadFileTopdf(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = LimsConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String filep = filePath + fileName.split("upload")[1];
            String nameSuffix = filep.substring(filep.lastIndexOf("."));//文件后缀
            if (".doc".equalsIgnoreCase(nameSuffix) || ".docx".equalsIgnoreCase(nameSuffix)) {
                String newPath = filep.replace(nameSuffix, ".pdf");
                fileName = fileName.replace(nameSuffix, ".pdf");
            }
//            String url = serverConfig.getUrl() + fileName;
            String url = fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", file.getOriginalFilename());
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（转换word文档为pdf）
     */
    @ApiOperation(value = "多文件上传（转换word文档为pdf）", notes = "多文件上传(word会转成pdf)", position = 2)
    @PostMapping("/common/uploadFilesToPdf")
    public AjaxResult uploadFilesToPdf(@RequestBody MultipartFile[] files) throws Exception {
        try {
            List<String> fileNames = new ArrayList<String>();
            List<String> urls = new ArrayList<String>();
//             Map<String, Object> map = new HashMap<String, Object>();
            // 上传文件路径
            String filePath = LimsConfig.getUploadPath();
            for (int i = 0; i < files.length; i++) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, files[i]);
                String filep = filePath + fileName.split("upload")[1];

                String nameSuffix = filep.substring(filep.lastIndexOf("."));//文件后缀
                if (".doc".equalsIgnoreCase(nameSuffix) || ".docx".equalsIgnoreCase(nameSuffix)) {
                    String newPath = filep.replace(nameSuffix, ".pdf");

                    // Word2PdfAsposeUtil.doc2pdf(filep, newPath);

                    fileName = fileName.replace(nameSuffix, ".pdf");
                }

                String url = serverConfig.getUrl() + fileName;
                fileNames.add(fileName);
                urls.add(fileName);
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileNames);
            ajax.put("url", urls);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求(多文件)
     */
    @ApiOperation(value = "多文件上传", notes = "多文件上传", position = 2)
    @PostMapping("/common/uploadFiles")
    public AjaxResult uploadFile(@RequestBody MultipartFile[] files) throws Exception {

        try {
            List<String> fileNames = new ArrayList<String>();
            List<String> urls = new ArrayList<String>();
//             Map<String, Object> map = new HashMap<String, Object>();
            // 上传文件路径
            String filePath = LimsConfig.getUploadPath();
            for (int i = 0; i < files.length; i++) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, files[i]);
//                String url = serverConfig.getUrl() + fileName;
                fileNames.add(fileName);
                urls.add(fileName);
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileNames);
            ajax.put("url", urls);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = LimsConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 模板文件下载
     *
     * @param fileName 下载文件名称
     */
    @ApiOperation(value = "模板文件下载", notes = "模板文件下载", position = 2)
    @ApiImplicitParam(name = "fileName", value = "模板文件名称", dataType = "String")
    @GetMapping("/common/template/download")
    public void templateDownload(String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            String realFileName = fileName.substring(fileName.indexOf("_") + 1);
            String filePath = LimsConfig.getProfile() + "/" + Constants.RESOURCE_TEMPLATE + "/" + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
        }
    }

    @GetMapping("/common/template/getTemplateUrl")
    @PreAuthorize("@ss.hasPermi('common:template:getTemplateUrl')")
    public String getTemplateUrl() {
        String filePath = LimsConfig.getProfile() + "/" + Constants.RESOURCE_TEMPLATE + "/" + "试题模板.xls";
        return filePath;
    }

    @ApiOperation(value = "删除文件", notes = "删除文件", position = 2)
    @ApiImplicitParam(name = "fileUrl", value = "文件路径", dataType = "String")
    @GetMapping("/common/deleteFile")
    public static AjaxResult deleteFile(String fileUrl) {
        String deletePath = LimsConfig.getProfile() + StringUtils.substringAfter(fileUrl, Constants.RESOURCE_PREFIX);
        File file = new File(deletePath);
        file.delete();//删除根目录
        if (!file.exists()) {
            return AjaxResult.success("删除成功");
        }else {
            return AjaxResult.error("删除失败");
        }
    }

    @ApiOperation(value = "下载模板", notes = "下载模板", position = 2)
    @GetMapping("/common/downloadTemplate")
    @ApiImplicitParam(name = "name", value = "模板名称", dataType = "String")
    public String downloadTemplate(String name) {
        String filePath = LimsConfig.getProfile() + "/" + Constants.RESOURCE_TEMPLATE + "/" + name+".xls";
        return filePath;
    }


}
