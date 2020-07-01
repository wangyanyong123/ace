package com.github.wxiaoqi.security.app.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: guohao
 * @create: 2020-04-29 15:47
 **/
public class HttpRequestParamUtil {

    public static Map<String,String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            valueStr = org.apache.commons.lang.StringEscapeUtils.unescapeHtml(valueStr);
            params.put(name, valueStr);
        }
        return params;
    }
}
