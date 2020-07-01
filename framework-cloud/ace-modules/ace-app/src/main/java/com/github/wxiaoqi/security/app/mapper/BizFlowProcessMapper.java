package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.app.entity.BizFlowProcess;

import java.util.List;
import java.util.Map;

/**
 * 流程工序表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
public interface BizFlowProcessMapper extends CommonMapper<BizFlowProcess> {

    List<BizFlowProcess> selectFlowProcessList(Map<?, ?> map);
}
