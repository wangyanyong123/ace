package com.github.wxiaoqi.security.app.vo.integralproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/2
 */
@Data
public class CashVo implements Serializable {

    @ApiModelProperty(value = "兑换id")
    private String id;
    @ApiModelProperty(value = "兑换商品名称")
    private String productName;
    @ApiModelProperty(value = "兑换积分")
    private String cashIntegral;
    @ApiModelProperty(value = "兑换时间")
    private String cashTime;
}
