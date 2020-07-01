package com.github.wxiaoqi.security.app.vo.postage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PostageVo implements Serializable {
    private static final long serialVersionUID = -770030837933777976L;

    @ApiModelProperty(value = "起算金额")
    private BigDecimal startAmount;
    @ApiModelProperty(value = "截止金额")
    private BigDecimal endAmount;
    @ApiModelProperty(value = "邮费")
    private BigDecimal postage;


}
