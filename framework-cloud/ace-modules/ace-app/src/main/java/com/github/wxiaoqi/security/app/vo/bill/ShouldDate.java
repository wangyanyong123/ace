package com.github.wxiaoqi.security.app.vo.bill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShouldDate implements Serializable {
    private static final long serialVersionUID = -8014546444408802565L;
    @ApiModelProperty(value = "账期(yyyyMM)")
    private String date;
}
