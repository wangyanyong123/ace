package com.github.wxiaoqi.security.external.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ParcelParam implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String telphone;
    @ApiModelProperty(value = "消息内容")
    private String message;
}
