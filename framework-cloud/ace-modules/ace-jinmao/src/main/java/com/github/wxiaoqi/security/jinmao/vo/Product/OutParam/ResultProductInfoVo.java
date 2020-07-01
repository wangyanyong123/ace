package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultProductInfoVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "业务id")
    private String busId;
    @ApiModelProperty(value = "业务名称(不需要传)")
    private String busName;
    @ApiModelProperty(value = "业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)")
    private String busType;
    @ApiModelProperty(value = "商品LOGO(不需要传)")
    private String productImage;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品精选(不需要传)")
    private String selectionImage;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "商品简介")
    private String productSummary;
    @ApiModelProperty(value = "商品售后")
    private String productAfterSale;
    @ApiModelProperty(value = "图文详情")
    private String productImagetextInfo;
    @ApiModelProperty(value = "团购总份数")
    private String productNum;
    @ApiModelProperty(value = "团购成团数")
    private String groupbuyNum;
    @ApiModelProperty(value = "团购开始时间 ")
    private String begTime;
    @ApiModelProperty(value = "团购结束时间 ")
    private String endTime;
    @ApiModelProperty(value = "项目范围")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "商品分类 ")
    private List<ResultClassifyVo> classifyVo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;

    @ApiModelProperty(value = "疯抢商品")
    private List<ResultSpike> spikeArr;
    @ApiModelProperty(value = "单账号限制购买数量")
    private int limitNum;

    @ApiModelProperty(value = "供应商")
    private String supplier;
    @ApiModelProperty(value = "销售方式")
    private String salesWay;

    private Integer productNums;


    public List<ResultSpike> getSpikeArr() {
        return spikeArr;
    }

    public void setSpikeArr(List<ResultSpike> spikeArr) {
        this.spikeArr = spikeArr;
    }

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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductSummary() {
        return productSummary;
    }

    public void setProductSummary(String productSummary) {
        this.productSummary = productSummary;
    }

    public String getProductAfterSale() {
        return productAfterSale;
    }

    public void setProductAfterSale(String productAfterSale) {
        this.productAfterSale = productAfterSale;
    }

    public String getProductImagetextInfo() {
        return productImagetextInfo;
    }

    public void setProductImagetextInfo(String productImagetextInfo) {
        this.productImagetextInfo = productImagetextInfo;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
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

    public List<ResultProjectVo> getProjectVo() {
        return projectVo;
    }

    public void setProjectVo(List<ResultProjectVo> projectVo) {
        this.projectVo = projectVo;
    }

    public List<ResultClassifyVo> getClassifyVo() {
        return classifyVo;
    }

    public void setClassifyVo(List<ResultClassifyVo> classifyVo) {
        this.classifyVo = classifyVo;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<ImgInfo> getProductImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imArrayIds = new String[]{productImage};
            if(productImage.indexOf(",")!= -1){
                imArrayIds = productImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setProductImageList(List<ImgInfo> productImageList) {
        this.productImageList = productImageList;
    }


    public String getSelectionImage() {
        return selectionImage;
    }

    public void setSelectionImage(String selectionImage) {
        this.selectionImage = selectionImage;
    }

    public List<ImgInfo> getSelectionImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(selectionImage)){
            String[] imArrayIds = new String[]{selectionImage};
            if(selectionImage.indexOf(",")!= -1){
                imArrayIds = selectionImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setSelectionImageList(List<ImgInfo> selectionImageList) {
        this.selectionImageList = selectionImageList;
    }

    public String getGroupbuyNum() {
        return groupbuyNum;
    }

    public void setGroupbuyNum(String groupbuyNum) {
        this.groupbuyNum = groupbuyNum;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSalesWay() {
        return salesWay;
    }

    public void setSalesWay(String salesWay) {
        this.salesWay = salesWay;
    }

    public Integer getProductNums() {
        return productNums;
    }

    public void setProductNums(Integer productNums) {
        this.productNums = productNums;
    }
}
