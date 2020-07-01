package com.github.wxiaoqi.security.external.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.external.entity.BizFacilities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 道闸表
 * 
 * @author zxl
 * @Date 2019-01-03 10:34:15
 */
public interface BizFacilitiesMapper extends CommonMapper<BizFacilities> {

	BizFacilities selectByCode(String facilitiesCode);

	List<String> getFacilitiesTypeByCode(String facilitiesCode);

	BizFacilities hasAuthority(@Param("machineCode") String machineCode, @Param("distinctList") List<String> distinctList);
}
