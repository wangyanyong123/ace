package com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.InputParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveRecommendParam implements Serializable {
    private static final long serialVersionUID = 7124783220309550795L;
    @ApiModelProperty(value = "主键id(不用传)")
    private String id;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "商品ID")
    private String productId;
    @ApiModelProperty(value = "排序")
    private Integer viewSort;

    @ApiModelProperty(value = "排序")
    private String imgUrl;

}
