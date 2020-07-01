package com.github.wxiaoqi.security.jinmao.vo.Announcement.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveAnnouncementParam implements Serializable {

    @ApiModelProperty(value = "公告id")
    private String id;
    @ApiModelProperty(value = "公告标题")
    private String title;
    @ApiModelProperty(value = "公告类型:1 停水通知、2 停电通知、3 保洁养护、4 社区文化、5 绿化保养、6 消防演习、7 高温预警")
    private String announcementType;
    @ApiModelProperty(value = "公告类型名称")
    private String announcementName;
    @ApiModelProperty(value = "重要程度1：重要  2：一般")
    private String importantDegree;
    @ApiModelProperty(value = "公告内容")
    private String content;
    @ApiModelProperty(value = "签名")
    private String publisher;
    @ApiModelProperty(value = "发布时间")
    private String publisherTime;
    @ApiModelProperty(value = "图片")
    private String images;
    @ApiModelProperty(value = "状态:1 发布,2 草稿")
    private String status ;


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

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public String getImportantDegree() {
        return importantDegree;
    }

    public void setImportantDegree(String importantDegree) {
        this.importantDegree = importantDegree;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAnnouncementName() {
        return announcementName;
    }

    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(String publisherTime) {
        this.publisherTime = publisherTime;
    }
}
