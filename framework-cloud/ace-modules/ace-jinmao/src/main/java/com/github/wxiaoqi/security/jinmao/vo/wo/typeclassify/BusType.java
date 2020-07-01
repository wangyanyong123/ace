package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BusType implements Serializable {
    private static final long serialVersionUID = -3486096430468498065L;

    @ApiModelProperty(value = "工单类型")
    private String busName;
    @ApiModelProperty(value = "分类详情")
    private List<ClassifyTypeCount> classifyInfo;
}
