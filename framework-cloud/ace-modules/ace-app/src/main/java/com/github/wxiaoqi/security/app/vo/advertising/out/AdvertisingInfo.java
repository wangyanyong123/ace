package com.github.wxiaoqi.security.app.vo.advertising.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvertisingInfo implements Serializable {
    @ApiModelProperty(value = "广告id")
    private String id;
    @ApiModelProperty(value = "广告标题")
    private String title;
    @ApiModelProperty(value = "广告图片")
    private String advertisingImg;
    @ApiModelProperty(value = "广告图片地址")
    private List<ImgeInfo> advertisingImgUrl;
    @ApiModelProperty(value = "跳转业务类型(0-无1-app内部2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "外部跳转地址")
    private String skipUrl;
    @ApiModelProperty(value = "APP内部跳转业务(1-优选商城2-团购)")
    private String busClassify;
    @ApiModelProperty(value = "内部跳转业务对象")
    private String productId;

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

    public String getAdvertisingImg() {
        return advertisingImg;
    }

    public void setAdvertisingImg(String advertisingImg) {
        this.advertisingImg = advertisingImg;
    }

    public List<ImgeInfo> getAdvertisingImgUrl() {
        List<ImgeInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(advertisingImg)){
            String[] imArrayIds = new String[]{advertisingImg};
            if(advertisingImg.indexOf(",")!= -1){
                imArrayIds = advertisingImg.split(",");
            }
            for (String url:imArrayIds){
                ImgeInfo info = new ImgeInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setAdvertisingImgUrl(List<ImgeInfo> advertisingImgUrl) {
        this.advertisingImgUrl = advertisingImgUrl;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getSkipBus() {
        return skipBus;
    }

    public void setSkipBus(String skipBus) {
        this.skipBus = skipBus;
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
}
