package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodVisitVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "商品ID")
    private String productId;
    @ApiModelProperty(value = "签名")
    private String signer;
    @ApiModelProperty(value = "签名头像")
    private String singerLogo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "副标题")
    private String subHeading;
    @ApiModelProperty(value = "推荐图片")
    private String recommendImages;
    @ApiModelProperty(value = "用户头像")
    private List<ImgeInfo> profilePhoto;
    @ApiModelProperty(value = "浏览数")
    private Integer viewNum;

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

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSingerLogo() {
        return singerLogo;
    }

    public void setSingerLogo(String singerLogo) {
        this.singerLogo = singerLogo;
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

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getRecommendImages() {
        return recommendImages;
    }

    public void setRecommendImages(String recommendImages) {
        this.recommendImages = recommendImages;
    }

//

    public List<ImgeInfo> getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(List<ImgeInfo> profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }
}
