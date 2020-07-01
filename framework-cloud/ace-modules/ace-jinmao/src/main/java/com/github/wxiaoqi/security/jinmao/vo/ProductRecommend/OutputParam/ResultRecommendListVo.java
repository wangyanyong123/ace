package com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultRecommendListVo implements Serializable {
    private static final long serialVersionUID = 8404868997270672574L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "序号")
    private Integer viewSort;

    private String imgUrl;

    private String busName;
}
