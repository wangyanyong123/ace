package com.github.wxiaoqi.security.admin.vo.dict.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveDictParam implements Serializable {

    @ApiModelProperty(value = "编码id(若是顶节点需要填写)")
    private String id;
    @ApiModelProperty(value = "父编码id(若是顶节点不需要传)")
    private String pid;
    @ApiModelProperty(value ="值")
    private String val;
    @ApiModelProperty(value ="名称")
    private String name;
    @ApiModelProperty(value ="英文名称")
    private String enName;
    @ApiModelProperty(value ="排序")
    private int viewSort;




}
