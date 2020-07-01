package com.github.wxiaoqi.security.api.vo.wechat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信获取openId返回数据
 *
 * @author: guohao
 * @create: 2020-04-12 16:48
 **/
@Data
public class WechatOpenIdResult implements Serializable {

    private static final long serialVersionUID = -6306603830099300096L;

    @ApiModelProperty(value = "应用appId")
    private String appId;
    @ApiModelProperty(value = "应用类型")
    private Integer appType;

    @ApiModelProperty(value = "用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID")
    private String openId;

    @ApiModelProperty("微信小程序 ，会话密钥")
    private String sessionKey;

    @ApiModelProperty("微信小程序，可能返回,用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。")
    private String unionId;

}
