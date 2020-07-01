package com.github.wxiaoqi.security.plan.mapper;

import com.github.wxiaoqi.security.plan.annotation.DataSource;
import com.github.wxiaoqi.security.plan.entity.PlanWoTimer;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:53 2019/2/27
 * @Modified By:
 */
@DataSource
public interface PlanWoTimerMapper {

	PlanWoTimer getLast();

	int save(PlanWoTimer planWoTimer);
}
