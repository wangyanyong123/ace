package com.github.wxiaoqi.security.app.vo.evaluate.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HousekeeperInfo implements Serializable {

    @ApiModelProperty(value = "管家id")
    private String housekeeperId;
    @ApiModelProperty(value = "管家名称")
    private String name;
    @ApiModelProperty(value = "管家头像")
    private String profilePhoto;
    @ApiModelProperty(value = "管家性别(0、未知；1、男；2、女')")
    private String sex;
    @ApiModelProperty(value = "管家头像(从这获取)")
    private List<ImgInfo> aprofilePhotoList;
    @ApiModelProperty(value = "评价数")
    private int evaluateNum;
    @ApiModelProperty(value = "满意度")
    private String satisfaction;
    @ApiModelProperty(value = "评价详情id")
    private String detailId;
    @ApiModelProperty(value = "是否评价(0-没有,1-有)")
    private String isEvaluate;
    @ApiModelProperty(value = "评价等级(1-不满意,2-一般,3-满意)")
    private String evaluateType;

    public String getHousekeeperId() {
        return housekeeperId;
    }

    public void setHousekeeperId(String housekeeperId) {
        this.housekeeperId = housekeeperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<ImgInfo> getAprofilePhotoList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(profilePhoto)){
            String[] imArrayIds = new String[]{profilePhoto};
            if(profilePhoto.indexOf(",")!= -1){
                imArrayIds = profilePhoto.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setAprofilePhotoList(List<ImgInfo> aprofilePhotoList) {
        this.aprofilePhotoList = aprofilePhotoList;
    }

    public int getEvaluateNum() {
        return evaluateNum;
    }

    public void setEvaluateNum(int evaluateNum) {
        this.evaluateNum = evaluateNum;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(String isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
