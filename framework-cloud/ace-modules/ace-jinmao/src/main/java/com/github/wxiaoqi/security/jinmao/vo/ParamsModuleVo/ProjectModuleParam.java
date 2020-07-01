package com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo;

import java.io.Serializable;

public class ProjectModuleParam implements Serializable {

    private String projectId;

    private String system;

    private String[] projectModulesInfo;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String[] getProjectModulesInfo() {
        return projectModulesInfo;
    }

    public void setProjectModulesInfo(String[] projectModulesInfo) {
        this.projectModulesInfo = projectModulesInfo;
    }
}
