package com.github.wxiaoqi.job.feign;


import com.github.wxiaoqi.job.feign.fallback.AceXxlJobFeignFallbackFactory;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ace-job-admin"
		,configuration = FeignApplyConfiguration.class
		,fallbackFactory = AceXxlJobFeignFallbackFactory.class)
public interface AceXxlJobFeign {

	/**
	 * 新增任务
	 */
	@RequestMapping(value = "/feign/jobinfo/add", method = RequestMethod.POST)
	ReturnT<String> add(@RequestBody XxlJobInfo jobInfo);

}
