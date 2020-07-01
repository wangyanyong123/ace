package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 16:02
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SignInfo implements Serializable {
    private static final long serialVersionUID = -7162158498847691485L;

    @ApiModelProperty(value = "签到积分")
    private Integer integral;
    @ApiModelProperty(value = "连续签到天数")
    private Integer day;
    @ApiModelProperty(value = "签到类型(1-正常2-连续)")
    private String signType;


}
