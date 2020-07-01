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

@FeignClient(value = "ace-jinmao" , configuration = FeignApplyConfiguration.class)
public interface AdHomePageFegin {

	/**
	 * 获取app广告
	 * @return
	 */
	@RequestMapping(value = "web/sysHomepageAd/getAdAppHomePageList", method = RequestMethod.GET)
	ObjectRestResponse getAdAppHomePageList();

}
