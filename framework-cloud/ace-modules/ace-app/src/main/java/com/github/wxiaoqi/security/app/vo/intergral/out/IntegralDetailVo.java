package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/9/17 14:24
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class IntegralDetailVo implements Serializable {
    private static final long serialVersionUID = -5359107586403121016L;

    @ApiModelProperty(value = "积分")
    private String integral;
    @ApiModelProperty(value = "积分流水名称")
    private String logName;
    @ApiModelProperty(value = "积分流水时间")
    private String logDate;
}
