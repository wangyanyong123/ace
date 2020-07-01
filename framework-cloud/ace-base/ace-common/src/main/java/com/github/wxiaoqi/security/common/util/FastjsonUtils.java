package com.github.wxiaoqi.security.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * alibaba fastjson工具类封装
 * @see JSON
 *
 */
public class FastjsonUtils {

	public static Feature DEFAULT_PARSER_FEATURE = Feature.DisableCircularReferenceDetect;
	public static SerializerFeature[] DEFAULT_SERIAL_FEATURE = {SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero};

	/**
	 * 把JSON文本parse为JSONObject或者JSONArray
	 * 
	 * @param text
	 * @return
	 */
	public static Object parse(String text) {
		return JSON.parse(text, DEFAULT_PARSER_FEATURE);
	}

	/**
	 * 把JSON文本parse成JSONObject
	 * 
	 * @param text
	 * @return
	 */
	public static final JSONObject parseObject(String text) {
		return JSON.parseObject(text, DEFAULT_PARSER_FEATURE);
	}

	/**
	 * 把JSON文本parse为JavaBean
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz, DEFAULT_PARSER_FEATURE);
	}

	/**
	 * 把JSON文本parse成JSONArray
	 * 
	 * @param text
	 * @return
	 */
	public static final JSONArray fromJsonArray(String text) {
		return JSON.parseArray(text);
	}

	/**
	 * 把JSON文本parse成JavaBean集合
	 * 
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static final <T> List<T> fromJsonArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * 将JavaBean序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static final String toJson(Object object) {
		return JSON.toJSONString(object, DEFAULT_SERIAL_FEATURE);
	}

	/**
	 * 将JavaBean转换为JSONObject或者JSONArray。
	 * 
	 * @param javaObject
	 * @return
	 */
	public static final Object toJSON(Object javaObject) {
		return JSON.toJSON(javaObject);
	}

	/**
	 * 判断是否是json
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		try {
			JSONObject.parseObject(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
