package com.github.wxiaoqi.security.api.vo.face;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UnitIdsVo implements Serializable {
    @ApiModelProperty("单元ID")
    private String unitId;
}
