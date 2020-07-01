package com.github.wxiaoqi.security.plan.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
public class DynamicDataSourceContextHolder {



    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    public static List<String> dataSourceIds = new ArrayList<>();

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static String getDataSourceRouterKey () {
        return HOLDER.get();
    }

    public static void setDataSourceRouterKey (String dataSourceRouterKey) {
        HOLDER.set(dataSourceRouterKey);
    }

    public static void removeDataSourceRouterKey () {
        HOLDER.remove();
    }

    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

}
