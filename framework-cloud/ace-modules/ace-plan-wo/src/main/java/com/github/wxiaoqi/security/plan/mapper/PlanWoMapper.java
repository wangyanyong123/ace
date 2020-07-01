package com.github.wxiaoqi.security.plan.mapper;

import com.github.wxiaoqi.security.plan.annotation.DataSource;
import com.github.wxiaoqi.security.plan.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
@DataSource("slave1")
public interface PlanWoMapper {
	List<PlanWoDto> getPlanWoListByDate(@Param("createDate") String createDate, @Param("projectCodes")List<String> projectCodes);

	List<PlanWoTrDto> getWoTrListByWoId(String woId);

	List<PlanWoRDto> getPlanWoRListByWoId(String woId);

	List<PlanWoEqDto> getPlanWoEqListByEqId(String eqId);

	List<PlanWoPmpDto> getPlanWoPmpListByPmpId(String pmpId);

	List<PlanWoPmpsDto> getPlanWoPmpsListByPmpId(String eqId);
}
