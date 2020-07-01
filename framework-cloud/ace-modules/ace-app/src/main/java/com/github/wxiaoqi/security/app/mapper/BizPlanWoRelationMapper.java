package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.PlanWoVo;
import com.github.wxiaoqi.security.app.entity.BizPlanWoRelation;
import com.github.wxiaoqi.security.app.vo.plan.PlanWoDetail;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划工单关系表表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
public interface BizPlanWoRelationMapper extends CommonMapper<BizPlanWoRelation> {

	List<PlanWoDetail> getPlanWoContentByWoId(@Param("woId") String woId);

	int updateByIds(@Param("list") List<String> pwrIds,@Param("userId") String userId);

	List<PlanWoVo> getPlanWoVoByWoId(@Param("woId") String id);
}
