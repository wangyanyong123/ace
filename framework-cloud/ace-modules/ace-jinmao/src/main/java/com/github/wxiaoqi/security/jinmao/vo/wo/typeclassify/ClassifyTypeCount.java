package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClassifyTypeCount implements Serializable {

    private static final long serialVersionUID = 3513968787892715055L;

    @ApiModelProperty(value = "工单类型")
    private String type;
    @ApiModelProperty(value = "分类名称")
    private String name;
    @ApiModelProperty(value = "总和")
    private String sum;
}
