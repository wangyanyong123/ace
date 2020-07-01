package com.github.wxiaoqi.security.jinmao.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    /**
     * date format 格式
     */
    private static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss","yyyy"};

    @Override
    public Date convert(String s) {
        if (StringUtils.isNotBlank(s)) {
            try {
                return DateUtils.parseDate(s, PATTERNS);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
