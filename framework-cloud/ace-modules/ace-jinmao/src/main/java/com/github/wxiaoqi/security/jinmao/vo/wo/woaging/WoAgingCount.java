package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WoAgingCount implements Serializable {
    private static final long serialVersionUID = 4116416499464788314L;

    @ApiModelProperty(value = "城市")
    private String cityName;
    @ApiModelProperty(value = "小区")
    private String projectName;
    @ApiModelProperty(value = "工单类型")
    private String type;
    @ApiModelProperty(value = "5分钟内")
    private String lessThanFive;
    @ApiModelProperty(value = "5到15分钟")
    private String fiveToFifteen;
    @ApiModelProperty(value = "15到30分钟")
    private String fifToThirty;
    @ApiModelProperty(value = "30到60分钟")
    private String thdToSixty;
    @ApiModelProperty(value = "1到2小时")
    private String oneToTwo;
    @ApiModelProperty(value = "大于2小时")
    private String moreThanTwo;
    @ApiModelProperty(value = "小区ID")
    private String projectId;
}
