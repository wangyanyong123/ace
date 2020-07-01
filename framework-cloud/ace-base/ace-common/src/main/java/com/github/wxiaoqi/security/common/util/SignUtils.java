package com.github.wxiaoqi.security.common.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * 数字签名工具类
 * @author liuam
 * @date 2019-07-18 16:31
 */
@Slf4j
public class SignUtils {

    /**
     * 得到参数的签名信息
     * @param map 由于参数的顺序会影响生成的签名，需要使用 TreeMap
     * @return
     */
    private static String getSignParam(TreeMap<String, Object> map) {
        StringBuilder signBuffer = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if("sign".equals(entry.getKey()) || "subProductInfoList".equals(entry.getKey())) {

            }else{
                signBuffer.append(entry.getKey());
                signBuffer.append("=");
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof Collection) {
                        signBuffer.append(FastjsonUtils.toJson(entry.getValue()));
                    } else {
                        signBuffer.append(entry.getValue());
                    }
                }
                signBuffer.append("&");
            }
        }
        signBuffer.deleteCharAt(signBuffer.length() - 1);
        return signBuffer.toString();
    }

    private static TreeMap<String, Object> objectToMap(Object obj) {
        TreeMap<String, Object> map = new TreeMap<>();
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String generateSign(Object obj, String appKey, String invalidTime, String appSecret) {
        TreeMap<String, Object> map = objectToMap(obj);
        map.put("appKey",appKey);
        map.put("invalidTime",invalidTime);
        String signParam = getSignParam(map);
        signParam = signParam + "&appSecret=" + appSecret;
        log.info("MD5加密字符串："+signParam);
        String s = MD5.getMD5Code(signParam);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(s.getBytes());
    }

    public static String generateSign(Object obj, String appSecret) {
        TreeMap<String, Object> map = objectToMap(obj);
        String signParam = getSignParam(map);
        signParam = signParam + "&appSecret=" + appSecret;
        log.info("MD5加密字符串："+signParam);
        String s = MD5.getMD5Code(signParam);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(s.getBytes());
    }

}
