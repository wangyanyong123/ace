package com.github.wxiaoqi.security.schedulewo.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.schedulewo.vo.DoOperateByTypeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ace-app" , configuration = FeignApplyConfiguration.class)
public interface OrderEngineFegin {


	/**
	 * 工单操作
	 * @param doOperateByTypeVo 根据指定操作类型操作工单
	 * @return
	 */
	@RequestMapping(value = "/orderEngine/doOperateByType" ,method = RequestMethod.POST)
	public ObjectRestResponse doOperateByType(@RequestBody DoOperateByTypeVo doOperateByTypeVo);

	/**
	 * 获取可指派/转单人员列表
	 * @param woId
	 * @return
	 */
	@RequestMapping(value = "/woService/getAccpetWoUserListNoToken" ,method = RequestMethod.GET)
	public ObjectRestResponse getAccpetWoUserListNoToken(@RequestParam(value ="woId") String woId);

}