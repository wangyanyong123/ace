package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultGoodVisitInfoVo implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "关联商品")
    private List<ProductListVo> productInfo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "副标题")
    private String subHeading;
    @ApiModelProperty(value = "推荐图片(不需要传)")
    private String recommendImage;
    @ApiModelProperty(value = "推荐图片")
    private List<ImgInfo> recommendImages;
    @ApiModelProperty(value = "图文详情")
    private String content;
    @ApiModelProperty(value = "签名")
    private String signer;
    @ApiModelProperty(value = "签名头像(不需要传)")
    private String signerLog;
    @ApiModelProperty(value = "签名头像地址")
    private List<ImgInfo> signerLogo;

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



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getRecommendImage() {
        return recommendImage;
    }

    public void setRecommendImage(String recommendImage) {
        this.recommendImage = recommendImage;
    }

    public List<ImgInfo> getRecommendImages() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(recommendImage)){
            String[] imArrayIds = new String[]{recommendImage};
            if(recommendImage.indexOf(",")!= -1){
                imArrayIds = recommendImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setRecommendImages(List<ImgInfo> recommendImages) {
        this.recommendImages = recommendImages;
    }

    public String getSignerLog() {
        return signerLog;
    }

    public void setSignerLog(String signerLog) {
        this.signerLog = signerLog;
    }

    public List<ImgInfo> getSignerLogo() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(signerLog)){
            String[] imArrayIds = new String[]{signerLog};
            if(signerLog.indexOf(",")!= -1){
                imArrayIds = signerLog.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setSignerLogo(List<ImgInfo> signerLogo) {
        this.signerLogo = signerLogo;
    }
}
