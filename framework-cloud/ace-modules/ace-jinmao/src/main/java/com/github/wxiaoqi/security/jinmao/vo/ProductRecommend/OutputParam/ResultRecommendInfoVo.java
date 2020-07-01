package com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultRecommendInfoVo implements Serializable {

    private static final long serialVersionUID = 1046187334109575188L;
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "排序")
    private Integer viewSort;

    private String imgUrl;
    private String busName;

}
