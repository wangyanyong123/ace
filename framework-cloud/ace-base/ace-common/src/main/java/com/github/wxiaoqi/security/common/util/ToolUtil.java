package com.github.wxiaoqi.security.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:51 2019/4/9
 * @Modified By:
 */
public class ToolUtil {
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (org.apache.commons.lang3.StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}

		return ipString;
	}

	/**
	 * 随机抽取List集合中特定个数的子项
	 * @param list
	 * @param count
	 * @return
	 */
	public static List getSubStringByRadom(List list, int count){
		List backList = null;
		backList = new ArrayList<>();
		Random random = new Random();
		int backSum = 0;
		if (list.size() >= count) {
			backSum = count;
		}else {
			backSum = list.size();
		}
		for (int i = 0; i < backSum; i++) {
//			随机数的范围为0-list.size()-1
			int target = random.nextInt(list.size());
			backList.add(list.get(target));
			list.remove(target);
		}
		return backList;
	}
}
