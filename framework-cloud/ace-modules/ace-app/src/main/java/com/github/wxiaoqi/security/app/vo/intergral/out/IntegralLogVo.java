package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/9/17 14:22
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class IntegralLogVo implements Serializable {
    private static final long serialVersionUID = -2299327835982871292L;

    @ApiModelProperty(value = "当前积分")
    private Integer curIntegral;

    @ApiModelProperty(value = "积分日志")
    private List<IntegralDetailVo> integralLog;
}
