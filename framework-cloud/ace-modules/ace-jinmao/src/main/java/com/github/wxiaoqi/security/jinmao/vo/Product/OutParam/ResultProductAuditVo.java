package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultProductAuditVo implements Serializable {


    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "申请时间")
    private String applyTime;
    @ApiModelProperty(value = "发布时间")
    private String publishTime;
    @ApiModelProperty(value = "业务状态(2-待审核，3-已发布，4已驳回，5-已下架）")
    private String busStatus;
    @ApiModelProperty(value = "商品所属业务")
    private String busName;
    @ApiModelProperty(value = "商品所属分类")
    private String classifyName;
    @ApiModelProperty(value = "销量")
    private String sales;
    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商户名称")
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}
