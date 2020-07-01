package com.github.wxiaoqi.security.app.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * 获取支付宝/微信配置信息
 * 
 * @author huangxl
 * @Date 2018-12-25 13:59:20
 */
public interface SettlementMapper {

    /**
     * 根据实际支付id获取支付宝配置信息
     * @param actualId
     * @return
     */
    HashMap<String, String> selectAliByActualId(@Param("actualId")String actualId);

    /**
     * 根据实际支付id获取微信配置信息
     * @param actualId
     * @return
     */
    HashMap<String, String> selectByWechatAccountId(@Param("actualId")String actualId);

    /**
     * 获取支付宝商城管理账号配置信息
     * @return
     */
    HashMap<String, String> selectAliPayInMall();

    /**
     * 获取微信商城管理账号配置信息
     * @return
     */
    HashMap<String, String> selectByWechatPayInMall();

}
