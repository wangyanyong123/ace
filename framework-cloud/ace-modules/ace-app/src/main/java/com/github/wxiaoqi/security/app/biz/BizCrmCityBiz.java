package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizCrmCity;
import com.github.wxiaoqi.security.app.mapper.BizCrmCityMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.app.vo.city.out.CityInfoVo;
import com.github.wxiaoqi.security.app.vo.city.out.CityProjectInfoVo;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.MunicipalityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 城市表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmCityBiz extends BusinessBiz<BizCrmCityMapper,BizCrmCity> {
	@Resource
	private BizCrmCityMapper crmCityMapper;

	@Resource
	private BizCrmProjectMapper crmProjectMapper;

	public void updateCity(List<BizCrmCity> cityList) {
		if(null != cityList && cityList.size() > 0){
			List<String> cityIds = cityList.stream().map(BizCrmCity::getCityId).distinct().collect(toList());
			List<String> needUpdateCityIds = new ArrayList<>();
			List<BizCrmCity> updateCityList = new ArrayList<>();
			List<BizCrmCity> addCityList = new ArrayList<>();
			List<BizCrmCity> crmCityList = crmCityMapper.getByIds(cityIds);
			if(null != crmCityList && crmCityList.size() > 0){
				List<String> oldCityIds = crmCityList.stream().map(BizCrmCity::getCityId).distinct().collect(toList());
				needUpdateCityIds = cityIds.stream().filter(oldCityIds::contains).collect(toList());
			}

			if(needUpdateCityIds.size() > 0){
				Iterator<BizCrmCity> it = cityList.iterator();
				while(it.hasNext()){
					BizCrmCity x = it.next();
					if(needUpdateCityIds.contains(x.getCityId())){
						updateCityList.add(x);
						it.remove();
					}else {
						addCityList.add(x);
					}
				}
			}else {
				addCityList = cityList;
			}
			if(updateCityList.size() > 0){
				log.info("批量更新城市信息"+updateCityList.size()+"条");
				int num = 0;
				for (BizCrmCity updateCity:updateCityList) {
					updateCity.setModifyTime(new Date());
					if(crmCityMapper.updateByPrimaryKeySelective(updateCity)>0){
						num++;
					}
				}
				log.info("成功更新城市信息"+num+"条");
			}
			if(addCityList.size() > 0){
				log.info("批量插入城市信息"+updateCityList.size()+"条");
				int num = 0;
				for (BizCrmCity addCity:addCityList) {
					addCity.setCreateTime(new Date());
					addCity.setStatus("1");
					if(crmCityMapper.insertSelective(addCity)>0){
						num++;
					}
				}
				log.info("成功插入城市信息"+num+"条");
			}
		}
	}

	public ObjectRestResponse<List<CityInfoVo>> getCityList(String cityName) {
		return ObjectRestResponse.ok(crmCityMapper.getCityListByName(cityName));
	}

	public ObjectRestResponse<List<CityProjectInfoVo>> getCityProjectList(String cityId) {
		int num = crmCityMapper.isNeedExclude(BaseContextHandler.getUserID());
		List<CityProjectInfoVo> cityProjectInfoVos = new ArrayList<>();
		if(num > 0 ){
			cityProjectInfoVos = crmCityMapper.getCityListById(cityId);
		}else {
			cityProjectInfoVos = crmCityMapper.getCityListExcludeXNById(cityId);
		}
		for (CityProjectInfoVo cityProjectInfoVo:cityProjectInfoVos) {
			List<ProjectInfoVo> projectInfoVos = crmProjectMapper.getProjectListByCityId(cityProjectInfoVo.getCityId());
			cityProjectInfoVo.setProjectInfoVos(projectInfoVos);
		}
		return ObjectRestResponse.ok(cityProjectInfoVos);
	}

}
