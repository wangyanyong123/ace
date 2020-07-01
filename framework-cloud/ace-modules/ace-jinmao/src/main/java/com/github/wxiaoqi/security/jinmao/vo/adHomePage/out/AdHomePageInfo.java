package com.github.wxiaoqi.security.jinmao.vo.adHomePage.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdHomePageInfo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "排序")
    private String sort;
    private String androidImage;
    private String iosImage;
    @ApiModelProperty(value = "停留时间")
    private String stopTime;
    @ApiModelProperty(value = "是否发布 1:待发布，2:已发布,3:已撤回")
    private String isPublish;
    @ApiModelProperty(value = "安卓图片")
    private List<ImgInfo> androidImg;
    @ApiModelProperty(value = "ios图片")
    private List<ImgInfo> iosImg;
    @ApiModelProperty(value = "所属项目")
    private List<AdProjectInfo> projectVo;


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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAndroidImage() {
        return androidImage;
    }

    public void setAndroidImage(String androidImage) {
        this.androidImage = androidImage;
    }

    public String getIosImage() {
        return iosImage;
    }

    public void setIosImage(String iosImage) {
        this.iosImage = iosImage;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public List<ImgInfo> getAndroidImg() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(androidImage)){
            String[] imArrayIds = new String[]{androidImage};
            if(androidImage.indexOf(",")!= -1){
                imArrayIds = androidImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setAndroidImg(List<ImgInfo> androidImg) {
        this.androidImg = androidImg;
    }

    public List<ImgInfo> getIosImg() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(iosImage)){
            String[] imArrayIds = new String[]{iosImage};
            if(iosImage.indexOf(",")!= -1){
                imArrayIds = iosImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setIosImg(List<ImgInfo> iosImg) {
        this.iosImg = iosImg;
    }


    public List<AdProjectInfo> getProjectVo() {
        return projectVo;
    }

    public void setProjectVo(List<AdProjectInfo> projectVo) {
        this.projectVo = projectVo;
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish;
    }
}
