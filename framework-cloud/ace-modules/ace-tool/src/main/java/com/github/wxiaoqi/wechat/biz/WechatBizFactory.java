package com.github.wxiaoqi.wechat.biz;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: guohao
 * @create: 2020-04-12 16:28
 **/
@Component
public class WechatBizFactory {

    @Autowired
    private WechatMpBiz wechatMpBiz;

    @Autowired
    private WechatSmallBiz wechatSmallBiz;

    public WechatBiz getCommonsBiz(Integer appType) {

        if (AceDictionary.APP_TYPE_H5.equals(appType)) {
            return wechatMpBiz;
        } else if (AceDictionary.APP_TYPE_MP.equals(appType)) {
            return wechatSmallBiz;
        }
        throw new BusinessException("微信请求 非法的应用类型，appType:" + appType);
    }
}
