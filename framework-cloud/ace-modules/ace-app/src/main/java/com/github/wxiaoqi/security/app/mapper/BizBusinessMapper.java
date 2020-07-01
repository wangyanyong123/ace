package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.vo.classify.out.ClassifyVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.app.entity.BizBusiness;

import java.util.List;
import java.util.Map;

/**
 * 业务表
 * 
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
public interface BizBusinessMapper extends CommonMapper<BizBusiness> {

    List<BizBusiness> selectAllBusinessList(Map<?, ?> map);

    List<ClassifyVo> selectBusinessClassifyList();

}
