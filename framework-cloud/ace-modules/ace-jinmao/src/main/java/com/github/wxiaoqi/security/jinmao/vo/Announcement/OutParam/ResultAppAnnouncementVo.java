package com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAppAnnouncementVo implements Serializable {

    @ApiModelProperty(value = "公告id")
    private String id;
    @ApiModelProperty(value = "公告标题")
    private String title;
    @ApiModelProperty(value = "图片")
    private String images;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "重要程度（1：重要  2：一般）")
    private String importantDegree;
    @ApiModelProperty(value = "公告类型（001 停水通知、002 停电通知、003 保洁养护、004 社区文化、005 绿化保养、006 消防演习、007 高温预警）")
    private String announcementType;
    @ApiModelProperty(value = "公告类型名称")
    private String announcementName;
    @ApiModelProperty(value = "阅读数")
    private int readerNum;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImportantDegree() {
        return importantDegree;
    }

    public void setImportantDegree(String importantDegree) {
        this.importantDegree = importantDegree;
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

    public int getReaderNum() {
        return readerNum;
    }

    public void setReaderNum(int readerNum) {
        this.readerNum = readerNum;
    }
}
