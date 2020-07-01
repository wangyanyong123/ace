package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import java.io.Serializable;

public class ResultProjectIdVo implements Serializable {

    private String tenantId;

    private String projectId;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
