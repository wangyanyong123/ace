package com.github.wxiaoqi.security.app.mapper;


import com.github.wxiaoqi.security.app.entity.BizSensitiveWords;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 敏感词表
 * 
 * @author zxl
 * @Date 2018-12-19 16:23:26
 */
public interface BizSensitiveWordsMapper extends CommonMapper<BizSensitiveWords> {

    /**
     * 查询敏感词
     * @return
     */
    List<String> getSensitiveWordList();
}
