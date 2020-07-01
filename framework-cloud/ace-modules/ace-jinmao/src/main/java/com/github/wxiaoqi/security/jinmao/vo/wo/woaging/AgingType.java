package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AgingType implements Serializable {
    private static final long serialVersionUID = -3279135459152532409L;
    @ApiModelProperty(value = "小区")
    private String projectName;
    @ApiModelProperty(value = "表格详情")
    private List<WoAgingCount> woAgingInfo;
}
