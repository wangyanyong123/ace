package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUserTenantId;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * app服务端用户表租户表
 * 
 * @author huangxl
 * @Date 2019-05-07 14:23:06
 */
public interface BaseAppServerUserTenantIdMapper extends CommonMapper<BaseAppServerUserTenantId> {


    int getServerTenant(String id);

    int delServerTenant(@Param("tenantId") String tenantId,@Param("id")String id);
}
