package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WoAgingData implements Serializable {
    private static final long serialVersionUID = -5144670516334005653L;
    @ApiModelProperty(value = "时效名称")
    private String name;
    @ApiModelProperty(value = "对应数量")
    private String value;
}
