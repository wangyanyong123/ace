package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultSkillVo implements Serializable {

    @ApiModelProperty(value = "技能id")
    private String id;
    @ApiModelProperty(value = "技能编码")
    private String skilCode;
    @ApiModelProperty(value = "技能名称")
    private String skilName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkilCode() {
        return skilCode;
    }

    public void setSkilCode(String skilCode) {
        this.skilCode = skilCode;
    }

    public String getSkilName() {
        return skilName;
    }

    public void setSkilName(String skilName) {
        this.skilName = skilName;
    }
}
