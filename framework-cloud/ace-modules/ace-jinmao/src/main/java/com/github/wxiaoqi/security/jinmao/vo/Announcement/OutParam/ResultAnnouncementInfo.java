package com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAnnouncementInfo implements Serializable {

    @ApiModelProperty(value = "公告id")
    private String id;
    @ApiModelProperty(value = "公告标题")
    private String title;
    @ApiModelProperty(value = "公告类型:1 停水通知、2 停电通知、3 保洁养护、4 社区文化、5 绿化保养、6 消防演习、7 高温预警")
    private String announcementType;
    @ApiModelProperty(value = "公告类型名称")
    private String announcementName;
    @ApiModelProperty(value = "签名")
    private String publisher;
    @ApiModelProperty(value = "发布时间")
    private String publisherTime;
    @ApiModelProperty(value = "重要程度（1：重要  2：一般）")
    private String importantDegree;
    @ApiModelProperty(value = "图片")
    private String images;
    @ApiModelProperty(value = "公告内容")
    private String content;


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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(String publisherTime) {
        this.publisherTime = publisherTime;
    }
}
