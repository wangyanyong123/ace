package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 好物探访入参
 *
 */
public class SaveGoodVisitParam implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String id;
    @ApiModelProperty(value = "关联商品")
    private List<ProductListVo> productInfo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "宣传语")
    private String subHeading;
    @ApiModelProperty(value = "封面图片")
    private List<ImgInfo> recommendImages;
    @ApiModelProperty(value = "发布人")
    private String signer;
    @ApiModelProperty(value = "发布人头像")
    private List<ImgInfo> signerLogo;
    @ApiModelProperty(value = "图片详情、内容")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductListVo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductListVo> productInfo) {
        this.productInfo = productInfo;
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

    public List<ImgInfo> getRecommendImages() {
        return recommendImages;
    }

    public void setRecommendImages(List<ImgInfo> recommendImages) {
        this.recommendImages = recommendImages;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public List<ImgInfo> getSignerLogo() {
        return signerLogo;
    }

    public void setSignerLogo(List<ImgInfo> signerLogo) {
        this.signerLogo = signerLogo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
