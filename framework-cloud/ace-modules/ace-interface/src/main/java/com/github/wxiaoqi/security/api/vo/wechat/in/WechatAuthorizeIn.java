package com.github.wxiaoqi.security.api.vo.wechat.in;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 微信授权
 *
 * @author: guohao
 * @create: 2020-04-12 11:32
 **/
@Data
public class WechatAuthorizeIn extends BaseIn implements Serializable {

    private static final long serialVersionUID = -5287093330296119723L;

    @ApiModelProperty(value = "客户端应用类型,", hidden = true)
    private Integer appType;

    @ApiModelProperty(value = "微信授权code")
    private String code;

    @Override
    public void check() {
        Assert.hasLength(code, "授权码不能不空。");
        Assert.isTrue(RequestHeaderUtil.effectivePlatform(appType), "未知的授权应用");
    }

    public static void main(String[] args) {
        WechatAuthorizeIn wechatAuthorizeIn = new WechatAuthorizeIn();
        wechatAuthorizeIn.setCode("1");

        System.out.println(JSON.toJSONString(wechatAuthorizeIn));

    }
}
