package com.github.wxiaoqi.security.jinmao.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenant;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenantProject;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户(公司、商户)项目关联表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
@Service
public class BaseTenantProjectBiz extends BusinessBiz<BaseTenantProjectMapper, BaseTenantProject> {
    public List<BaseTenantProject> findByTenantType(String tenantType) {
        return this.mapper.selectByTenantType(tenantType);
    }

    public List<String> existedProjectIdList(String tenantType, String[] projectIds) {
        return this.mapper.existedProjectIdList(tenantType,projectIds);
    }

    public List<String> findProjectIdList(String tenantId) {
        return this.mapper.selectProjectIdList(tenantId);
    }
}
