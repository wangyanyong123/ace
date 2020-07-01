package com.github.wxiaoqi.security.api.vo.wechat.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;

/**
 * 微信授权
 *
 * @author: guohao
 * @create: 2020-04-12 11:32
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BindPhoneIn extends BaseIn {

    private static final long serialVersionUID = 5225583436467335607L;

    @ApiModelProperty(value = "客户端应用类型", hidden = true)
    private Integer appType;

    @ApiModelProperty(value = "微信授权code,本地存储没有openId时选择code（场景清除缓存）， code与openId 二选一")
    private String code;
    @ApiModelProperty(value = "微信openId  code与openId 二选一")
    private String openId;

    @ApiModelProperty(value = "手机号  待绑定手机号")
    private String phone;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @Override
    public void check() {
        Assert.isTrue(StringUtils.isNotEmpty(code) || StringUtils.isNotEmpty(openId),
                "授权码与openId不能同时为空。");
        if (StringUtils.isNotEmpty(phone)) {
            Assert.hasLength(smsCode, "请填写短信验证码");
        }
        if (StringUtils.isNotEmpty(code)) {
            Assert.isTrue(RequestHeaderUtil.effectivePlatform(appType), "未知的授权应用");
        }
    }
}
