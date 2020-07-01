package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizPlanWoPmpsOp;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划工单程序表
 * 
 * @author zxl
 * @Date 2019-03-01 15:20:02
 */
public interface BizPlanWoPmpsOpMapper extends CommonMapper<BizPlanWoPmpsOp> {

	int insertBatch(@Param("list") List<BizPlanWoPmpsOp> planWoPmpsOps);
}
