package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.BuildInfoVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultServiceInfo implements Serializable {

    @ApiModelProperty(value = "物业人员id")
    private String id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "个人照片")
    private String profilePhoto;
    @ApiModelProperty(value = "资质图片")
    private String seniorityPhoto;
    @ApiModelProperty(value = "物业人员类型id")
    private String serviceGroupId;
    @ApiModelProperty(value = "人员类型")
    private String groupName;
    @ApiModelProperty(value = "技能")
    private List<ResultSkillVo> skillName;
    @ApiModelProperty(value = "服务范围")
    private List<ResultServiceAreaInfoVo> areaName;
    @ApiModelProperty(value = "状态(0表示无效，1表示有效")
    private String enableStatus;

    @ApiModelProperty(value = "状态(0表示否，1表示是")
    private String isService;

    @ApiModelProperty(value = "状态(0表示否，1表示是")
    private String isHouseKeeper;

    @ApiModelProperty(value = "状态(0表示否，1表示是")
    private String isCustomer;

    private List<BuildInfoVo> buildInfoVos;

    @ApiModelProperty(value = "个人图片地址")
    private List<ImgInfo> logo;

    @ApiModelProperty(value = "资质图片地址")
    private List<ImgInfo> senlogo;

    public List<ImgInfo> getSenlogo() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(seniorityPhoto)){
            String[] imArrayIds = new String[]{seniorityPhoto};
            if(seniorityPhoto.indexOf(",")!= -1){
                imArrayIds = seniorityPhoto.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setSenlogo(List<ImgInfo> senlogo) {
        this.senlogo = senlogo;
    }






    public List<ImgInfo> getLogo() {
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

    public void setLogo(List<ImgInfo> logo) {
        this.logo = logo;
    }








    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getSeniorityPhoto() {
        return seniorityPhoto;
    }

    public void setSeniorityPhoto(String seniorityPhoto) {
        this.seniorityPhoto = seniorityPhoto;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ResultSkillVo> getSkillName() {
        return skillName;
    }

    public void setSkillName(List<ResultSkillVo> skillName) {
        this.skillName = skillName;
    }

    public List<ResultServiceAreaInfoVo> getAreaName() {
        return areaName;
    }

    public void setAreaName(List<ResultServiceAreaInfoVo> areaName) {
        this.areaName = areaName;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(String serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public String getIsService() {
        return isService;
    }

    public void setIsService(String isService) {
        this.isService = isService;
    }

    public String getIsHouseKeeper() {
        return isHouseKeeper;
    }

    public void setIsHouseKeeper(String isHouseKeeper) {
        this.isHouseKeeper = isHouseKeeper;
    }

    public String getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(String isCustomer) {
        this.isCustomer = isCustomer;
    }

    public List<BuildInfoVo> getBuildInfoVos() {
        return buildInfoVos;
    }

    public void setBuildInfoVos(List<BuildInfoVo> buildInfoVos) {
        this.buildInfoVos = buildInfoVos;
    }
}
