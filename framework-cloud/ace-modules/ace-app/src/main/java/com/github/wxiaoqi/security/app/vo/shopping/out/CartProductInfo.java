package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartProductInfo implements Serializable {

    private static final long serialVersionUID = 6423282244766597741L;
    @ApiModelProperty(value = "购物车id")
    private String cartId;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "购买数量")
    private Integer quantity;
    @ApiModelProperty(value = "单价金额")
    private BigDecimal price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品规格id")
    private String specId;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "商品规格图片")
    private String specImage;

    @ApiModelProperty(value = "商品LOGO")
    private String productImage;

    @ApiModelProperty(value = "提示信息 为空时正常")
    private String msg;

    public String getSpecImage() {
        return StringUtils.isEmpty(specImage)?productImage:specImage;
    }
}
