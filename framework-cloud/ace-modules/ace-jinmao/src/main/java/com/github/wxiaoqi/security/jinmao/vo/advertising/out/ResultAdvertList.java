package com.github.wxiaoqi.security.jinmao.vo.advertising.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAdvertList implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "跳转业务(1-app内部2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "排序")
    private String viewSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getViewSort() {
        return viewSort;
    }

    public void setViewSort(String viewSort) {
        this.viewSort = viewSort;
    }

    public String getSkipBus() {
        return skipBus;
    }

    public void setSkipBus(String skipBus) {
        this.skipBus = skipBus;
    }
}
