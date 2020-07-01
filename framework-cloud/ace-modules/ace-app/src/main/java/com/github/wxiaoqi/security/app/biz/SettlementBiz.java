package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.app.mapper.SettlementMapper;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.AESUtil;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.RSAUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

@Service
@Slf4j
public class SettlementBiz{

    @Autowired
    private SettlementMapper settlementMapper;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;

    public ObjectRestResponse getAliSettlementInfoByActualId(String actualId) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (StringUtils.isEmpty(actualId)) {
            restResponse.setStatus(101);
            restResponse.setMessage("actualId is null");
            return restResponse;
        }
        HashMap<String, String> dataMap = null;
        String busId = bizAccountBookMapper.selectBusIdByActualId(actualId);
        if(StringUtils.isNotEmpty(busId) && (BusinessConstant.getGroupBuyingBusId().equals(busId) || BusinessConstant.getSeckillBusId().equals(busId)
                || BusinessConstant.getShoppingBusId().equals(busId)) || BusinessConstant.getTravelBusId().equals(busId)){
            //优先商品、团购、旅游、疯抢商品业务走系统统一支付账号
            dataMap = settlementMapper.selectAliPayInMall();
        }else{
           dataMap = settlementMapper.selectAliByActualId(actualId);
        }
        if(dataMap != null && !dataMap.isEmpty()){
            try {
                HashMap<String, String> resultMap = commonEncode(dataMap);
                if(!resultMap.isEmpty() && resultMap.containsKey("text") && resultMap.containsKey("key")){
                    restResponse.data(resultMap);
                } else {
                    log.info("信息加密失败:{}", dataMap);
                    restResponse.setStatus(103);
                    restResponse.setMessage("获取加密信息失败");
                }
            } catch (Exception e) {
                log.info("信息加密失败:{}", e.getMessage());
                restResponse.setStatus(103);
                restResponse.setMessage("信息加密失败");
            }
        } else {
            log.info("根据付款ID获取结算信息为空:{}", actualId);
            restResponse.setStatus(104);
            restResponse.setMessage("结算信息为空");
        }
        return restResponse;
    }

    public ObjectRestResponse getWechatSettlementInfoByActualId(String actualId) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (StringUtils.isEmpty(actualId)) {
            restResponse.setStatus(101);
            restResponse.setMessage("actualId is null");
            return restResponse;
        }

        HashMap<String, String> dataMap = null;
        String busId = bizAccountBookMapper.selectBusIdByActualId(actualId);
        if(StringUtils.isNotEmpty(busId) &&
                (BusinessConstant.getGroupBuyingBusId().equals(busId) || BusinessConstant.getSeckillBusId().equals(busId)
                || BusinessConstant.getShoppingBusId().equals(busId)) || BusinessConstant.getTravelBusId().equals(busId)){
            //优先商品和团购业务走系统统一支付账号
            dataMap = settlementMapper.selectAliPayInMall();
        }else{
            dataMap = settlementMapper.selectAliByActualId(actualId);
        }
        if(dataMap != null && !dataMap.isEmpty()){
            try {
                HashMap<String, String> resultMap = commonEncode(dataMap);
                if(!resultMap.isEmpty() && resultMap.containsKey("text") && resultMap.containsKey("key")){
                    restResponse.data(resultMap);
                } else {
                    log.info("信息加密失败:{}", dataMap);
                    restResponse.setStatus(103);
                    restResponse.setMessage("获取加密信息失败");
                }
            } catch (Exception e) {
                log.info("信息加密失败:{}", e.getMessage());
                restResponse.setStatus(103);
                restResponse.setMessage("信息加密失败");
            }
        } else {
            log.info("根据付款ID获取结算信息为空:{}", actualId);
            restResponse.setStatus(104);
            restResponse.setMessage("结算信息为空");
        }
        return restResponse;
    }

    /**
     * 通用的AES+RSA加密方法
     * @param dataMap
     * @return
     */
    private HashMap<String, String> commonEncode(HashMap<String, String> dataMap){
        InputStream in = null;
        HashMap<String, String> resultMap = new HashMap<String, String>();
        //生成加密密钥和偏移量
        String sKey = AESUtil.createKey();
        try {
            String dataJson = JacksonJsonUtil.beanToJson(dataMap);
            System.out.println("原文："+ dataJson);
            //AES加密报文信息
            String enString = AESUtil.getInstance().encrypt(dataJson, sKey, sKey);
            System.out.println("加密后的字串是：" + enString);
            resultMap.put("text", enString);

            // 加密AES所用密钥
            //fis = new FileInputStream(SettlementBiz.class.getClassLoader().getResource("").getPath() + "/publicKey.pem");
            in=this.getClass().getResourceAsStream("/publicKey.pem");
            PublicKey publicKey = RSAUtil.loadPublicKey(in);

            byte[] encodedText = RSAUtil.RSAEncode(publicKey, sKey.getBytes());
            String result = Base64.encodeBase64String(encodedText);
            System.out.println("RSA encoded: " + result);
            resultMap.put("key", result);
            if(in != null) {
                in.close();
            }

            // 解密AES所用密钥
            in=this.getClass().getResourceAsStream("/privateKey.pem");
            //fis = new FileInputStream(SettlementBiz.class.getClassLoader().getResource("").getPath() + "/privateKey.pem");
            PrivateKey privateKey = RSAUtil.loadPrivateKey(in);
            String result2 = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(result));
            System.out.println("RSA decoded: " + result2);
            if(in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }
}