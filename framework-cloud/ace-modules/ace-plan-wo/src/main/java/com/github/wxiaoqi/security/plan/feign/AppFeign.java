package com.github.wxiaoqi.security.plan.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.plan.vo.PlanWoInVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lzx
 * @create 2019/2/28
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface AppFeign {

    @RequestMapping(value = "/planWo/createWo",method = RequestMethod.POST)
	ObjectRestResponse createWo(@RequestBody PlanWoInVo createWoInVo);

    }
