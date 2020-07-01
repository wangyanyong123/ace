package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductLabel;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 商品标签表
 * 
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
public interface BizProductLabelMapper extends CommonMapper<BizProductLabel> {

    /**
     * 查询标签
     * @param id
     * @return
     */
    List<String> selectLabelList(String id);
}
