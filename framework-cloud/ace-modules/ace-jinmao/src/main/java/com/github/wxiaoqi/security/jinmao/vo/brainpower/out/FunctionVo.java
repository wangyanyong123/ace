package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FunctionVo implements Serializable {

    @ApiModelProperty(value = "功能点id")
    private String id;
    @ApiModelProperty(value = "功能编码")
    private String code;
    @ApiModelProperty(value = "功能点")
    private String functionPoint;
    @ApiModelProperty(value = "功能点内容")
    private String description;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "状态")
    private String enableStatus;
    @ApiModelProperty(value = "是否置底")
    private String isShow;

}
