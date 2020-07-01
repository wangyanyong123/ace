package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultMerchantManageVo implements Serializable {


    @ApiModelProperty(value = "商户ID")
    private String id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "商户名称")
    private String name;
    @ApiModelProperty(value = "营业执照号")
    private String licenceNo;
    @ApiModelProperty(value = "负责人名字")
    private String contactorName;
    @ApiModelProperty(value = "负责人电话")
    private String contactTel;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "业务名称")
    private String  busName;
    @ApiModelProperty(value = "启用状态(0表示禁用，1表示启用)")
    private String enableStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
