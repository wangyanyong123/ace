package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.PlanWoOptDetail;
import com.github.wxiaoqi.security.app.entity.BizPlanWoPmps;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划工单步骤表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
public interface BizPlanWoPmpsMapper extends CommonMapper<BizPlanWoPmps> {

	List<PlanWoOptDetail> getPlanWoOptDetail(@Param("pmpId") String pmpId, @Param("pwrId") String pwrId);
}
