package com.github.wxiaoqi.security.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 〈〉
 *
 * @author guohao
 * @date 2019/1/22 11:04
 * @since 1.0.0
 */
public class IpUtils {
    public static final String UNKNOWN = "unknown";

    /**
     * 获取用户ip地址
     * <p>
     * 有可能会返回多个ip地址  多个，分隔
     *
     * @param request HttpServletRequest
     * @return ipAddress
     */
    public static String getRequestIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本地ip地址
     *
     * @return ipAddress
     */
    public static String getLocalIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
