package com.github.wxiaoqi.security.jinmao.vo.group.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveGroupVo implements Serializable {

    @ApiModelProperty(value = "小组ID")
    private String id;
    @ApiModelProperty(value = "组名")
    private String name;
    @ApiModelProperty(value = "logo")
    private List<ImgInfo> logo;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "项目")
    private List<Map<String,String>> projectInfo;
    @ApiModelProperty(value = "分类")
    private List<Map<String,String>> classifyInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImgInfo> getLogo() {
        return logo;
    }

    public void setLogo(List<ImgInfo> logo) {
        this.logo = logo;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public List<Map<String, String>> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<Map<String, String>> projectInfo) {
        this.projectInfo = projectInfo;
    }

    public List<Map<String, String>> getClassifyInfo() {
        return classifyInfo;
    }

    public void setClassifyInfo(List<Map<String, String>> classifyInfo) {
        this.classifyInfo = classifyInfo;
    }
}
