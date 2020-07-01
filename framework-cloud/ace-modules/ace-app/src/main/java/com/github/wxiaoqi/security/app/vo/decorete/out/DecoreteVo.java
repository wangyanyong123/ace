package com.github.wxiaoqi.security.app.vo.decorete.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DecoreteVo implements Serializable {

    @ApiModelProperty(value = "装修监理id")
    private String id;
    @ApiModelProperty(value = "推广图")
    private String promoImge;
    @ApiModelProperty(value = "服务价格")
    private String servicePrice;
    @ApiModelProperty(value = "原价")
    private String costPrice;
    @ApiModelProperty(value = "图文详情")
    private String serviceIntro;
}
