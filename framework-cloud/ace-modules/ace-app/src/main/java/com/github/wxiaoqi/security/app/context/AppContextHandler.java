package com.github.wxiaoqi.security.app.context;

import java.util.HashMap;
import java.util.Map;

public class AppContextHandler {
    public static final String HEADER = "X-Sinochem-Info";
    public static final String PLATFORM_ANDROID = "android";
    public static final String PLATFORM_IOS = "ios";

    public static final String CONTEXT_KEY_APP_VERSION = "c_v";
    public static final String CONTEXT_KEY_PLATFORM = "c_p";

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getAppVersion() {
        Object value = get(CONTEXT_KEY_APP_VERSION);
        return returnObjectValue(value);
    }

    public static void setAppVersion(String version) {
        set(CONTEXT_KEY_APP_VERSION, version);
    }

    public static String getPlatform() {
        Object value = get(CONTEXT_KEY_PLATFORM);
        return returnObjectValue(value);
    }

    public static void setPlatform(String platform) {
        set(CONTEXT_KEY_PLATFORM, platform);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
