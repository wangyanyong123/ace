package com.github.wxiaoqi.security.external.biz;

import com.github.wxiaoqi.security.external.entity.BizFacilities;
import com.github.wxiaoqi.security.external.mapper.BizFacilitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;


/**
 * 道闸表
 *
 * @author zxl
 * @Date 2019-01-03 10:34:15
 */
@Service
public class BizFacilitiesBiz extends BusinessBiz<BizFacilitiesMapper,BizFacilities> {
	@Autowired
	private BizFacilitiesMapper facilitiesMapper;

	public BizFacilities selectByCode(String facilitiesCode) {
		return facilitiesMapper.selectByCode(facilitiesCode);
	}

	public List<String> getFacilitiesTypeByCode(String facilitiesCode) {
		return facilitiesMapper.getFacilitiesTypeByCode(facilitiesCode);
	}

	public BizFacilities hasAuthority(String machineCode, List<String> distinctList) {
		return facilitiesMapper.hasAuthority(machineCode,distinctList);
	}
}