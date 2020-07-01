package com.github.wxiaoqi.security.app.vo.shopping.in;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单页获取商品信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryConfirmOrderInfoIn extends BaseIn {

    private static final long serialVersionUID = -4623343947283561673L;
    @ApiModelProperty(value = "收货地址id")
    private String addressId;

    @ApiModelProperty(value = "购买的商品信息")
    private List<BuySpecInfoParam> specList;


    @Override
    public void check() {

        Assert.notEmpty(specList,"商品信息不能为空");
        specList.forEach(BuySpecInfoParam::check);
    }
}
