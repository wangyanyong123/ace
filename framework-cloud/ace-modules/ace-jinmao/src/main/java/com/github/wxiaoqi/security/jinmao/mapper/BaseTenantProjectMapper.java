package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenantProject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 租户(公司、商户)项目关联表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
public interface BaseTenantProjectMapper extends CommonMapper<BaseTenantProject> {

    /**
     * 根据商户id查询项目
     * @param tenantId
     * @return
     */
    String selectProjectByTenantId(String tenantId);

    String selectProjectNameByTenantId(String tenantId);
    /**
     * 根据商户ID删除项目
     * @param tenantId
     * @return
     */
    int deleteProjectByMerchantId(String tenantId);

    /**
     * 根据ID查询关联项目
     * @param tenantId
     * @return
     */
    List<String> selectProjectNamesById(String tenantId);

    List<BaseTenantProject> selectByTenantType(@Param("tenantType") String tenantType);

    List<String> existedProjectIdList(@Param("tenantType") String tenantType, @Param("projectIds") String[] projectIds);

    List<String> selectProjectIdList(@Param("tenantId") String tenantId);

    int deleteByTenantId(@Param("tenantId") String tenantId, @Param("projectIdList") List<String> projectIdList,
                         @Param("modifyBy") String modifyBy, @Param("modifyTime") Date modifyTime);
}
