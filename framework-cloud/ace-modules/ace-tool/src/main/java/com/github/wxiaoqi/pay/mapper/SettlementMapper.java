package com.github.wxiaoqi.pay.mapper;

import feign.Param;

import java.util.HashMap;

/**
 * 获取支付宝/微信配置信息
 * 
 * @author huangxl
 * @Date 2018-12-25 13:59:20
 */
public interface SettlementMapper{

    /**
     * 通过属性获取支付宝配置信息
     * @return
     */
    HashMap<String, String> selectALiByPropertyKey(@Param("property") String property, @Param("value")String value);

    /**
     * 通过属性获取微信配置信息
     * @return
     */
    HashMap<String, String> selectWechatByPropertyKey(@Param("property") String property, @Param("value")String value);

}
