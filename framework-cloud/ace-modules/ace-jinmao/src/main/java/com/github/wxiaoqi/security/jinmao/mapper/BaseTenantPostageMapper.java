package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenantPostage;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

/**
 * 商户邮费关联表
 * 
 * @author huangxl
 * @Date 2019-04-28 16:27:46
 */
public interface BaseTenantPostageMapper extends CommonMapper<BaseTenantPostage> {

    int deletePostage(String tenantId);
	
}
