package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultGroupActiveInfoVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "团购开始时间")
    private String begTime;
    @ApiModelProperty(value = "团购结束时间")
    private String endTime;
    @ApiModelProperty(value = "团购状态：1 未开始、2 进行中、3 已成团、4 已售罄、5 已结束")
    private String productStatus;
    @ApiModelProperty(value = "订单数")
    private String sales;
    @ApiModelProperty(value = "商品总份数")
    private String productNum;
    @ApiModelProperty(value = "成团数")
    private String groupbuyNum;
    @ApiModelProperty(value = "商户名称")
    private String name;
    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "订单列表")
    private List<ResultOrderList> orderList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getGroupbuyNum() {
        return groupbuyNum;
    }

    public void setGroupbuyNum(String groupbuyNum) {
        this.groupbuyNum = groupbuyNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ResultOrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ResultOrderList> orderList) {
        this.orderList = orderList;
    }
}
