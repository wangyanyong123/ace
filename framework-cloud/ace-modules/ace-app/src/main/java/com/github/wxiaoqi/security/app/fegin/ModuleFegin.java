package com.github.wxiaoqi.security.app.fegin;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:08 2018/11/21
 * @Modified By:
 */

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-jinmao" , configuration = FeignApplyConfiguration.class)
public interface ModuleFegin {

	/**
	 * 获取该项目下的App模块
	 * @return
	 */
	@RequestMapping(value = "/web/sysModuleApp/getModules",method = RequestMethod.GET)
	ObjectRestResponse getAppModules(@RequestParam("projectId")String projectId, @RequestParam("system") String system);
}
