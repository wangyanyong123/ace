package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.api.vo.household.*;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:58 2018/12/5
 * @Modified By:
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface HouseHoldFeign {

	@RequestMapping(value = "/city/getCityProjectList", method = RequestMethod.GET)
	ObjectRestResponse<List<CityProjectInfoVo>> getCityProjectList();

	@RequestMapping(value = "/house/getHouseInfoTree", method = RequestMethod.GET)
	TableResultResponse<HouseInfoTree> getHouseInfoTree(@RequestParam("projectId")String projectId);

	@RequestMapping(value = "/userHouse/getUserInfoByHouseIdWeb", method = RequestMethod.GET)
	TableResultResponse<UserInfo> getUserInfoByHouseIdWeb(@RequestParam("houseId")String houseId, @RequestParam("searchVal")String searchVal,
													   @RequestParam("page")Integer page, @RequestParam("limit")Integer limit);

	@RequestMapping(value = "/city/getCityList", method = RequestMethod.GET)
	ObjectRestResponse<List<CityInfoVo>> getCityList(@RequestParam(value = "cityName" ,required = false) String cityName);

	@RequestMapping(value = "/project/getProjectList", method = RequestMethod.GET)
	ObjectRestResponse<List<ProjectInfoVo>> getProjectList(@RequestParam(value = "cityId") String cityId);

}
