package com.github.wxiaoqi.security.common.util;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: guohao
 * @create: 2020-04-17 18:00
 **/
public class RequestHeaderUtil {


    public static String getAppVersion(HttpServletRequest request) {
        return getHeadValue(request, RequestHeaderConstants.HEADER, RequestHeaderConstants.CONTEXT_KEY_APP_VERSION);
    }


    public static String getCityCode(HttpServletRequest request) {
        return getHeadValue(request, RequestHeaderConstants.HEADER, RequestHeaderConstants.CITY_CODE);
    }
    public static String getCityName(HttpServletRequest request) {
        return getHeadValue(request, RequestHeaderConstants.HEADER, RequestHeaderConstants.CITY_CODE);
    }

    public static String getPlatform(HttpServletRequest request) {
        return getHeadValue(request, RequestHeaderConstants.HEADER, RequestHeaderConstants.CONTEXT_KEY_PLATFORM);
    }

    public static int getPlatformIntValue(HttpServletRequest request) {
        String headValue = getHeadValue(request, RequestHeaderConstants.HEADER, RequestHeaderConstants.CONTEXT_KEY_PLATFORM);
        return getPlatformIntValue(headValue);
    }
    public static int getPlatformIntValue(String platForm) {
        if(RequestHeaderConstants.PLATFORM_IOS.equals(platForm)){
            return AceDictionary.APP_TYPE_IOS;
        }else   if(RequestHeaderConstants.PLATFORM_ANDROID.equals(platForm)){
            return AceDictionary.APP_TYPE_ANDROID;
        }else   if(RequestHeaderConstants.PLATFORM_H5.equals(platForm)){
            return AceDictionary.APP_TYPE_H5;
        }else   if(RequestHeaderConstants.PLATFORM_WECHAT_SMALL.equals(platForm)){
            return AceDictionary.APP_TYPE_MP;
        }
        //暂时兼容ios 请求头传递不全
        return AceDictionary.APP_TYPE_IOS;
//        throw new BusinessException("未知的应用类型");
    }

    public static String getHeadValue(HttpServletRequest request, String headName) {
        return request.getHeader(headName);

    }

    public static String getHeadValue(HttpServletRequest request, String headName, String key) {
        String header = request.getHeader(headName);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(header)) {
            String[] headerArr = header.split("&");
            for (String tmpHeader : headerArr) {
                if (StringUtils.isNotEmpty(tmpHeader)) {
                    String[] kv = tmpHeader.split("=");
                    if (kv.length == 2 && kv[0].equals(key)) {
                        return kv[1];
                    }
                }
            }
        }
        return "";
    }

    /**
     * 是否为有效的平台类型
     *
     * @param platform :  平台类型
     * @return boolean
     * @Author guohao
     * @Date 2020/4/17 18:25
     */
    public static boolean effectivePlatform(String platform) {
        return RequestHeaderConstants.PLATFORM_ANDROID.equals(platform)
                || RequestHeaderConstants.PLATFORM_IOS.equals(platform)
                || RequestHeaderConstants.PLATFORM_H5.equals(platform)
                || RequestHeaderConstants.PLATFORM_WECHAT_SMALL.equals(platform);
    }
    public static boolean effectivePlatform(Integer platform) {
        return AceDictionary.APP_TYPE.containsKey(platform);
    }

}
