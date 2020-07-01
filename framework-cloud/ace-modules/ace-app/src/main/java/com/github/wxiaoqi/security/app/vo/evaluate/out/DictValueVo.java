package com.github.wxiaoqi.security.app.vo.evaluate.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictValueVo implements Serializable {

    @ApiModelProperty(value = "编码id")
    private String id;
    @ApiModelProperty(value ="值")
    private int val;
    @ApiModelProperty(value ="名称")
    private String name;
}
