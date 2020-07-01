package com.github.wxiaoqi.security.app.vo.shopping.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.map.Serializers;
import org.springframework.util.Assert;

/**
 * 购买的商品信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuySpecInfoParam extends BaseIn {
    private static final long serialVersionUID = 3400535499113256340L;

    @ApiModelProperty(value = "购物车id")
    private String cartId;

    @ApiModelProperty(value = "规格id")
    private String specId;

    @ApiModelProperty(value = "购买的数量")
    private int quantity;

    @Override
    public void check() {
        Assert.hasLength(specId,"规格id 不能为空");
        Assert.isTrue(quantity > 0,"购买数量大于0");

    }
}
