package com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAnnouncementVo implements Serializable {

    @ApiModelProperty(value = "公告id")
    private String id;
    @ApiModelProperty(value = "公告标题")
    private String title;
    @ApiModelProperty(value = "公告类型名称")
    private String announcementName;
    @ApiModelProperty(value = "签名")
    private String publisher;
    @ApiModelProperty(value = "发布时间")
    private String publisherTime;
    @ApiModelProperty(value = "重要程度（1：重要  2：一般）")
    private String importantDegree;
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
    @ApiModelProperty(value = "状态")
    private String statusName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnouncementName() {
        return announcementName;
    }

    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImportantDegree() {
        return importantDegree;
    }

    public void setImportantDegree(String importantDegree) {
        this.importantDegree = importantDegree;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(String publisherTime) {
        this.publisherTime = publisherTime;
    }
}
