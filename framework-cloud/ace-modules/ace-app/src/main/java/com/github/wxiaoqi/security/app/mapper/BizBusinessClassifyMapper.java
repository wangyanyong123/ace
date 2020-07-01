package com.github.wxiaoqi.security.app.mapper;


import com.github.wxiaoqi.security.app.entity.BizBusinessClassify;
import com.github.wxiaoqi.security.app.vo.product.out.ClassifyVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 业务商品分类表
 * 
 * @author zxl
 * @Date 2018-12-10 16:29:40
 */
public interface BizBusinessClassifyMapper extends CommonMapper<BizBusinessClassify> {

    /**
     * 查询优选商品下的商品分类列表
     * @return
     */
    List<ClassifyVo> selectClassifyList(String busId);
}
