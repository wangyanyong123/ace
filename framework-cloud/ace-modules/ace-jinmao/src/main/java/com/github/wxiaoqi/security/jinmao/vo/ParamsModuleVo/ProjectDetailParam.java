package com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo;

import java.io.Serializable;

public class ProjectDetailParam implements Serializable {

    private String projectId;

    private String system;

    private String moduleId;

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

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
