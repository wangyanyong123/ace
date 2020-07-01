package com.github.wxiaoqi.security.jinmao.vo.integralproduct.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class SaveSpecInfo implements Serializable {

    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格积分")
    private String specIntegral;
    @ApiModelProperty(value = "规格单位")
    private String unit;
}
