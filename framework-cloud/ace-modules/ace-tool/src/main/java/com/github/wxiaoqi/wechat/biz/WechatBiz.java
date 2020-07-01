package com.github.wxiaoqi.wechat.biz;

import com.github.wxiaoqi.security.api.vo.wechat.out.WechatOpenIdResult;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.wechat.jssdk.jssdk.JSSDKAPI;

/**
 * 微信。小程序 公共业务
 *
 * @author: guohao
 * @create: 2020-04-12 16:44
 **/
public interface WechatBiz {

    /**
     * 微信/小程序 获取openId
     *
     * @param code : 微信小程序授权码
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @Author guohao
     * @Date 2020/4/12 16:45
     */
    ObjectRestResponse<WechatOpenIdResult> getOpenId(String code);

    ObjectRestResponse getJsSdkConfig(String url, JSSDKAPI[] jssdkapis, boolean debug);

    String getUserAuthorizationURL(String notifyUrl, String scope, String state);
}
