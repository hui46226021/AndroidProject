package com.jrfunclibrary.model;

import android.graphics.Bitmap;



/**
 * Created by zhush on 2016/10/24.
 * E-mail 405086805@qq.com
 * 附件实体
 */

public class AttachmentModel {
    private Long attachmentId;//业务表 主键
    private Long attachmentDetailId;//主键
    private String originalName;
    private String fileName;
    private String pathName;
    private String fileType;
    private Long fileSize;
    private String realPath;
    private String createUserName;
    private String createDate;
    private Long attachTypeId;//附件类型
    private Bitmap bitmap;
    private String basePath;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getAttachTypeId() {
        return attachTypeId;
    }

    public void setAttachTypeId(Long attachTypeId) {
        this.attachTypeId = attachTypeId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUrl(){
        return basePath+pathName+fileName;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentDetailId() {
        return attachmentDetailId;
    }

    public void setAttachmentDetailId(Long attachmentDetailId) {
        this.attachmentDetailId = attachmentDetailId;
    }
}
