package com.github.wxiaoqi.security.jinmao.vo.Service.InputParam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveServiceParam implements Serializable {

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
    @ApiModelProperty(value = "状态(0表示无效，1表示有效")
    private String enableStatus;
    @ApiModelProperty(value = "个人照片")
    private List<ImgInfo>  profilePhoto;
    @ApiModelProperty(value = "资质图片")
    private List<ImgInfo> seniorityPhoto;
    @ApiModelProperty(value = "物业人员分类id")
    private String serviceGroupId;
    @ApiModelProperty(value = "技能id")
    private List<Map<String,String>> skillId;
    @ApiModelProperty(value = "楼栋id")
    private List<Map<String,String>> buildId;

    @ApiModelProperty(value = "人员类型")
    private String[] personMold;

    @ApiModelProperty(value = "管家所属房屋")
    private List<Map<String,String>> house;




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

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ImgInfo> getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(List<ImgInfo> profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(String serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public List<ImgInfo> getSeniorityPhoto() {
        return seniorityPhoto;
    }

    public void setSeniorityPhoto(List<ImgInfo> seniorityPhoto) {
        this.seniorityPhoto = seniorityPhoto;
    }

    public List<Map<String, String>> getSkillId() {
        return skillId;
    }

    public void setSkillId(List<Map<String, String>> skillId) {
        this.skillId = skillId;
    }

    public List<Map<String, String>> getBuildId() {
        return buildId;
    }

    public void setBuildId(List<Map<String, String>> buildId) {
        this.buildId = buildId;
    }

    public String[] getPersonMold() {
        return personMold;
    }

    public void setPersonMold(String[] personMold) {
        this.personMold = personMold;
    }

    public List<Map<String, String>> getHouse() {
        return house;
    }

    public void setHouse(List<Map<String, String>> house) {
        this.house = house;
    }
}
