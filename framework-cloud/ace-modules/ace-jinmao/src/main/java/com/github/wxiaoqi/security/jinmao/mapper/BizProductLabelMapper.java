package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductLabel;

import java.util.List;

/**
 * 商品标签表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:44
 */
public interface BizProductLabelMapper extends CommonMapper<BizProductLabel> {

    /**
     * 查询标签
     * @param id
     * @return
     */
    List<String> selectLabelList(String id);

    int delLabelfo(String id);
	
}
