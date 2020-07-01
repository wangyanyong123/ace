package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


public class ResultGoodVisitVo implements Serializable {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "副标题")
    private String subHeading;
    @ApiModelProperty(value = "关联商品")
    private String productName;
    @ApiModelProperty(value = "发布项目")
    private String projectName;
    @ApiModelProperty(value = "签名")
    private String signer;
    @ApiModelProperty(value = "发布时间")
    private Date publishTime;
    @ApiModelProperty(value = "阅读量")
    private Integer visitCount;
    @ApiModelProperty(value = "商品状态(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架)")
    private String busStatus;
    @ApiModelProperty(value = "启用状态(1-草稿，2-已发布，3-已撤回,4-全部)")
    private String enableStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
