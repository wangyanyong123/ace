package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmCity;
import com.github.wxiaoqi.security.app.vo.city.out.CityInfoVo;
import com.github.wxiaoqi.security.app.vo.city.out.CityProjectInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmCityMapper extends CommonMapper<BizCrmCity> {

	List<BizCrmCity> getByIds(@Param("ids")List<String> cityIds);

	List<CityInfoVo> getCityListByName(@Param("cityName")String cityName);

	List<CityProjectInfoVo> getCityListById(@Param("cityId")String cityId);

	int isNeedExclude(@Param("userId") String userId);

	List<CityProjectInfoVo> getCityListExcludeXNById(@Param("cityId")String cityId);
}
