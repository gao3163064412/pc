package com.sygt.common.exception.file;

import java.util.Arrays;

import org.apache.commons.fileupload.FileUploadException;


/*******************************************************************
 * Copyright (C)  版权所有
 * @projectName： LIMS系统
 * @fileName: 文件上传 误异常类
 * @class: InvalidExtensionException
 * @date: 2021/05/18 08:51:52
 * @author : zhang'ai'jun
 * @version: v1.0.0
 * My blog： https://zaj553.gitee.io/blog
 **********************************************************************/
public class InvalidExtensionException extends FileUploadException {
    private static final long serialVersionUID = 1L;

    private String[] allowedExtension;
    private String extension;
    private String filename;

    public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
        super("filename : [" + filename + "], extension : [" + extension + "], allowed extension : [" + Arrays.toString(allowedExtension) + "]");
        this.allowedExtension = allowedExtension;
        this.extension = extension;
        this.filename = filename;
    }

    public String[] getAllowedExtension() {
        return allowedExtension;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilename() {
        return filename;
    }

    public static class InvalidImageExtensionException extends InvalidExtensionException {
        private static final long serialVersionUID = 1L;

        public InvalidImageExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    public static class InvalidFlashExtensionException extends InvalidExtensionException {
        private static final long serialVersionUID = 1L;

        public InvalidFlashExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    public static class InvalidMediaExtensionException extends InvalidExtensionException {
        private static final long serialVersionUID = 1L;

        public InvalidMediaExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    public static class InvalidVideoExtensionException extends InvalidExtensionException {
        private static final long serialVersionUID = 1L;

        public InvalidVideoExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }
}
