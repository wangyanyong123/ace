package com.github.wxiaoqi.security.jinmao.vo.statement.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddParam implements Serializable {

    @ApiModelProperty(value = "账单id")
    private String id;
    @ApiModelProperty(value = "商户id")
    private String tenantId;
}
