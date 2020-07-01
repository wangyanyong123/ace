package com.github.wxiaoqi.security.merchant.response;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;

import java.io.Serializable;

/**
 * 异常信息响应
 */
public class MerchantObjectResponse extends ObjectRestResponse implements Serializable {

    public static MerchantObjectResponse error(ResponseCodeEnum responseCodeEnum) {
        MerchantObjectResponse result = new MerchantObjectResponse();
        result.setStatus(responseCodeEnum.getKey());
        result.setMessage(responseCodeEnum.getValue());
        return result;
    }

    public static MerchantObjectResponse error(ResponseCodeEnum responseCodeEnum,String message) {
        MerchantObjectResponse result = new MerchantObjectResponse();
        result.setStatus(responseCodeEnum.getKey());
        result.setMessage(message);
        return result;
    }


}
