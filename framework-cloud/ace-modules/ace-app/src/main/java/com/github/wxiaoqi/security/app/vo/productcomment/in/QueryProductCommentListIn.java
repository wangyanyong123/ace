package com.github.wxiaoqi.security.app.vo.productcomment.in;

import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryProductCommentListIn extends BaseQueryIn {
    private static final long serialVersionUID = -1258117505783299908L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "商品id")
    private String productId;

    @Override
    protected void doCheck() {

    }
}
