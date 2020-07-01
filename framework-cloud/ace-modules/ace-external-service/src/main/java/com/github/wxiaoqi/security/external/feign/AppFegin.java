package com.github.wxiaoqi.security.external.feign;

import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoTwoVo;
import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoVo;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:20 2018/12/25
 * @Modified By:
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface AppFegin {
	@RequestMapping(value = "/project/sysProjectInfo", method = RequestMethod.GET)
	ObjectRestResponse<List<SysProjectInfoVo>> sysProjectInfo();

	@RequestMapping(value = "/project/getSysProjectInfo", method = RequestMethod.GET)
	ObjectRestResponse<List<SysProjectInfoTwoVo>> getSysProjectInfo();
}
