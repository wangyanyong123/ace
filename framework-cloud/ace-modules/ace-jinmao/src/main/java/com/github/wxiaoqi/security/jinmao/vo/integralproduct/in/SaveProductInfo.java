package com.github.wxiaoqi.security.jinmao.vo.integralproduct.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class SaveProductInfo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品LOGO图片地址")
    private List<ImgInfo> productImageList;
    @ApiModelProperty(value = "商品精选图片")
    private List<ImgInfo> selectionImageList;
    @ApiModelProperty(value = "项目范围")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;
    @ApiModelProperty(value = "商品总数量")
    private String productNum;
    private String productClassify;
}
