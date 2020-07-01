package com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam;

import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultRecommendInfo implements Serializable {

    private static final long serialVersionUID = 7867615690095010374L;
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @ApiModelProperty(value = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "业务名称")
    private String busName;
    @ApiModelProperty(value = "业务类型(1-优选2-团购)")
    private String busType;
    @ApiModelProperty(value = "所属项目")
    private String projectName;

    private ProductListVo productInfo;

}
