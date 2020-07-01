package com.github.wxiaoqi.security.app.mapper;


import com.github.wxiaoqi.security.app.entity.BizProductClassify;
import com.github.wxiaoqi.security.app.vo.classify.out.ClassifyVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类表
 * 
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
public interface BizProductClassifyMapper extends CommonMapper<BizProductClassify> {

    List<ClassifyVo> selectSecondClassifyList(@Param("busId") String busId);
}
