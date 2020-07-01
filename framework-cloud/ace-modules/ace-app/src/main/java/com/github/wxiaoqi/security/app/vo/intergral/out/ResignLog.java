package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/9 15:19
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class ResignLog implements Serializable {

    @ApiModelProperty(value = "补签描述")
    private String resignDesc;
    @ApiModelProperty(value = "补签时间")
    private String resignDate;
    @ApiModelProperty(value = "补签结果")
    private String resignResult;
}
