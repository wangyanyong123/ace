package com.github.wxiaoqi.security.jinmao.vo.integralproduct.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class ProductInfo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    private String productImage;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    private String selectionImage;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    private String productImagetextInfo;
    @ApiModelProperty(value = "项目范围")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "商品总数量")
    private String productNum;
    private String productClassify;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格积分")
    private String specIntegral;
    @ApiModelProperty(value = "规格单位")
    private String unit;

    @ApiModelProperty(value = "业务状态(1-待发布,2-已发布3-已下架）")
    private String busStatus;


    public List<ImgInfo> getProductImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(productImage)){
            String[] imgArrayIds =new String[] { productImage };
            if(productImage.indexOf(",")!=-1){
                imgArrayIds=productImage.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }

    public List<ImgInfo> getSelectionImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(selectionImage)){
            String[] imgArrayIds =new String[] { selectionImage };
            if(selectionImage.indexOf(",")!=-1){
                imgArrayIds=selectionImage.split(",");
            }
            for (String url : imgArrayIds){
                ImgInfo imgInfo = new ImgInfo();
                imgInfo.setUrl(url);
                list.add(imgInfo);
            }
        }
        return list;
    }
}
