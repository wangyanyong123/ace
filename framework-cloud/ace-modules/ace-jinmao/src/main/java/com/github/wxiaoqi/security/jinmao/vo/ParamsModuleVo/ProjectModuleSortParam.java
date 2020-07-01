package com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProjectModuleSortParam implements Serializable {

    private String projectId;

    private List<Map<String,String>> moduleSortInfo;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<Map<String, String>> getModuleSortInfo() {
        return moduleSortInfo;
    }

    public void setModuleSortInfo(List<Map<String, String>> moduleSortInfo) {
        this.moduleSortInfo = moduleSortInfo;
    }
}
