package com.xxl.job.admin.controller;

import com.xxl.job.admin.controller.annotation.PermissionLimit;
import com.xxl.job.admin.core.exception.XxlJobException;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */

@RequestMapping("/feign/jobinfo")
@RestController
public class JobFeignController {

	@Resource
	private XxlJobService xxlJobService;

	@PermissionLimit(limit = false)
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public ReturnT<String> add(@RequestBody XxlJobInfo jobInfo) {
		ReturnT<String> add = xxlJobService.add(jobInfo);
		if(!add.isSuccess()){
			throw  new XxlJobException(add.getMsg());
		}
		return add;
	}

	@PermissionLimit(limit = false)
	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(XxlJobInfo jobInfo) {
		ReturnT<String> update = xxlJobService.update(jobInfo);
		if(!update.isSuccess()){
			throw  new XxlJobException(update.getMsg());
		}
		return update;
	}

}
