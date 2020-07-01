package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizFlowProcessOperate;
import com.github.wxiaoqi.security.app.entity.BizFlowService;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author huangxl
 * @Date 2019-01-09 17:22:54
 */
public interface BizFlowServiceMapper extends CommonMapper<BizFlowService> {
    List<BizFlowService> selectBizFlowServiceList(Map<?, ?> map);
}
