package com.github.wxiaoqi.security.jinmao.vo.advertising.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultAdvert implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "项目信息")
    private List<AdvertProjectInfo> projectInfo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "广告图片(不用传)")
    private String advertisingImge;
    @ApiModelProperty(value = "广告图片")
    private List<ImgInfo> advertisingImg;
    @ApiModelProperty(value = "排序")
    private Integer viewSort;
    @ApiModelProperty(value = "商户ID")
    private String tenantId;
    @ApiModelProperty(value = "跳转业务(1-app内部2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "跳转地址")
    private String skipUrl;
    @ApiModelProperty(value = "APP内部跳转业务(1-优选商城2-团购)")
    private String busClassify;
    @ApiModelProperty(value = "内部跳转业务对象")
    private String productId;
    @ApiModelProperty(value = "业务名称")
    private String busName;
    @ApiModelProperty(value = "对象名称")
    private String productName;
    private List<ProductListVo> productInfo;
    @ApiModelProperty(value = "位置")
    private Integer position;

    public List<ProductListVo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductListVo> productInfo) {
        this.productInfo = productInfo;
    }

    public String getSkipBus() {
        return skipBus;
    }

    public void setSkipBus(String skipBus) {
        this.skipBus = skipBus;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAdvertisingImge() {
        return advertisingImge;
    }

    public void setAdvertisingImge(String advertisingImge) {
        this.advertisingImge = advertisingImge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AdvertProjectInfo> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<AdvertProjectInfo> projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImgInfo> getAdvertisingImg() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(advertisingImge)){
            String[] imArrayIds = new String[]{advertisingImge};
            if(advertisingImge.indexOf(",")!= -1){
                imArrayIds = advertisingImge.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setAdvertisingImg(List<ImgInfo> advertisingImg) {
        this.advertisingImg = advertisingImg;
    }

    public Integer getViewSort() {
        return viewSort;
    }

    public void setViewSort(Integer viewSort) {
        this.viewSort = viewSort;
    }

    public String getBusClassify() {
        return busClassify;
    }

    public void setBusClassify(String busClassify) {
        this.busClassify = busClassify;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
