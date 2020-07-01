package com.github.wxiaoqi.security.app.vo.integralproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/2
 */
@Data
public class IntegralProductVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品封面")
    private String productImage;
    @ApiModelProperty(value = "商品兑换积分")
    private String specIntegral;
    @ApiModelProperty(value = "是否可兑换")
    private String cashStatusStr;
    @ApiModelProperty(value = "商品标签")
    private List<String> label;

}
