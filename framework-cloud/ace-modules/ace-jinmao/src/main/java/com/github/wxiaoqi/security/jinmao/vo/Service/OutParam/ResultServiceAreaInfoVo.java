package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultServiceAreaInfoVo implements Serializable {

    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "楼栋id")
    private String buildId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "楼栋名称")
    private String buildName;
    @ApiModelProperty(value = "地块id")
    private String blockId;
    @ApiModelProperty(value = "地块名称")
    private String blockName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
