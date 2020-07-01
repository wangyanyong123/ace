package com.github.wxiaoqi.security.jinmao.vo.CoverageStat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoverageStatVo implements Serializable {

    @ApiModelProperty(value = "项目编码")
    private String projectCode;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "累计认证用户数")
    private int sumUserNum;
    @ApiModelProperty(value = "累计认证户数")
    private int sumHouseNum;
    @ApiModelProperty(value = "新增认证用户数")
    private int addUserNum;
    @ApiModelProperty(value = "新增认证户数")
    private int addHouseNum;
    @ApiModelProperty(value = "统计日期")
    private String day;
}
