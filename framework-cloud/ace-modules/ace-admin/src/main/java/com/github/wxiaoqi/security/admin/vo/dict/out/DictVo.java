package com.github.wxiaoqi.security.admin.vo.dict.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictVo implements Serializable {

    @ApiModelProperty(value = "编码id")
    private String id;
    @ApiModelProperty(value = "父编码id")
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
