package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GoodVisitDetailsVo implements Serializable {


    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "签名")
    private String signer;
    @ApiModelProperty(value = "签名头像")
    private String signerLogo;
    @ApiModelProperty(value = "商品ID")
    private String productId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "副标题")
    private String subHeading;
    @ApiModelProperty(value = "图文详情")
    private String content;
    @ApiModelProperty(value = "商品信息")
    private ProductInfoVo productInfo;

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignerLogo() {
        return signerLogo;
    }

    public void setSignerLogo(String signerLogo) {
        this.signerLogo = signerLogo;
    }

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

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProductInfoVo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoVo productInfo) {
        this.productInfo = productInfo;
    }
}
