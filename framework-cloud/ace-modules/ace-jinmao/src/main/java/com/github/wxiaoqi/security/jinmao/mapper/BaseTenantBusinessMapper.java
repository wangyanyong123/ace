package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BaseTenantBusiness;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2018-12-06 11:38:06
 */
public interface BaseTenantBusinessMapper extends CommonMapper<BaseTenantBusiness> {

    String selectBusinessByTenantId(String tenantId);

    int deleteBusinessByMerchantId(String tenantId);

    List<String> selectBusinessNamesById(String tenantId);
}
