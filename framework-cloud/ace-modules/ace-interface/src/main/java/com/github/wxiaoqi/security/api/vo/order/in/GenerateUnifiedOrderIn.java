package com.github.wxiaoqi.security.api.vo.order.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Data
public class GenerateUnifiedOrderIn extends BaseIn {

    @ApiModelProperty(value = "支付方式 1：支付宝，2：微信", required = true)
    private String payType;

    @ApiModelProperty(value = "订单ID", required = true)
    private String subId;

    @ApiModelProperty(value = "订单标题", required = true)
    private String title;

    @ApiModelProperty(value = "实际支付ID", required = true)
    private String actualId;

    @ApiModelProperty(value = "实际支付金额(单元：元)", required = true)
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "应用类型，微信支付使用，后端设置, 应用类型 1：ios， 2：android， 3：h5， 4：mp", hidden = true)
    private Integer appType;

    @ApiModelProperty(value = "用户ip，微信支付使用，后端设置", hidden = true)
    private String userIp;

    @ApiModelProperty(value = "微信关注用户的openId，微信支付使用，后端设置", hidden = true)
    private String openId;

    @ApiModelProperty(value = "支付回调地址,不设置走默认回调地址")
    private String notifyUrl;

    @ApiModelProperty(value = "附加参数，在查询API和支付通知中原样返回。自定义参数", hidden = true)
    private String attach;


    @Override
    public void check() {
        //兼容上一版本
        if ("10".equals(payType)) {
            payType = AceDictionary.PAY_TYPE_ALIPAY;
        }
        Assert.notNull(payType, "支付方式不能为null");
        Assert.isTrue(AceDictionary.PAY_TYPE_ALIPAY.equals(payType)
                || AceDictionary.PAY_TYPE_WECHAT.equals(payType), "非法的支付类型");
        Assert.notNull(subId, "订单ID不能为null");
        Assert.hasLength(title, "订单标题为空");
        Assert.hasLength(actualId, "实际支付ID为空");
        Assert.notNull(actualPrice, "实际支付金额不能为空");
        if(title.length() > 40){
            title = title.substring(0,40);
        }
    }

}
