package com.github.wxiaoqi.security.app.vo.integralproduct;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/2
 */
@Data
public class IntegralProductInfo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品封面")
    private String productImage;
    @ApiModelProperty(value = "商品兑换数量")
    private String cashNum;
    @ApiModelProperty(value = "商品精选图片")
    private String selectionImage;
    @ApiModelProperty(value = "商品兑换积分")
    private String specIntegral;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格单位")
    private String specUnit;
    @ApiModelProperty(value = "是否可兑换")
    private String cashStatus;
    @ApiModelProperty(value = "商品标签")
    private List<String> label;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;



    public List<ImgInfo> getSelectionImage() {
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



}
