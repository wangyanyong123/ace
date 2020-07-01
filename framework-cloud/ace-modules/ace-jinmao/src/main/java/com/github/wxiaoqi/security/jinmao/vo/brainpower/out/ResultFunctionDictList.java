package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultFunctionDictList implements Serializable {

    @ApiModelProperty(value = "功能编码")
    private String code;
    @ApiModelProperty(value = "功能点名称")
    private String functionPoint;

}
