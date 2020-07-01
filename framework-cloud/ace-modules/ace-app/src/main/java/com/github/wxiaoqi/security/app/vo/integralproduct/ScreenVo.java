package com.github.wxiaoqi.security.app.vo.integralproduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/2
 */
@Data
public class ScreenVo implements Serializable {

    @ApiModelProperty(value = "筛选id")
    private String id;
    @ApiModelProperty(value = "起始积分(积分筛选传该字段)")
    private String startIntegral;
    @ApiModelProperty(value = "截止积分(积分筛选传该字段)")
    private String endIntegral;
    @ApiModelProperty(value = "筛选范围")
    private String integralStr;
}
