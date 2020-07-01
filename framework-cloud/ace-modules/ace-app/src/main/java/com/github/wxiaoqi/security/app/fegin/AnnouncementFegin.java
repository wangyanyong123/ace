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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-jinmao" , configuration = FeignApplyConfiguration.class)
public interface AnnouncementFegin {

	/**
	 * 查询物业公告列表
	 * @param projectId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/ann/getAppAnnouncementList", method = RequestMethod.GET)
	ObjectRestResponse getAppAnnouncementList(@RequestParam("projectId")String projectId, @RequestParam("page")Integer page,
											  @RequestParam("limit") Integer limit);

	/**
	 * 查询物业公告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/ann/getAppAnnouncementInfo/{id}", method = RequestMethod.GET)
	ObjectRestResponse getAppAnnouncementInfo(@PathVariable String id);
}
