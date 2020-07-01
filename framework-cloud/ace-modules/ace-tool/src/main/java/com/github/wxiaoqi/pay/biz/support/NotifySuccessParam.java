package com.github.wxiaoqi.pay.biz.support;

import lombok.Data;

/**
 * @author: guohao
 * @create: 2020-04-09 17:33
 **/
@Data
public class NotifySuccessParam {
    private String appId;

    private String subId;

    private String mchId;

    private String payType;

    private String outTradeNo;

    private String tradeNo;

    private String totalAmount;


}
