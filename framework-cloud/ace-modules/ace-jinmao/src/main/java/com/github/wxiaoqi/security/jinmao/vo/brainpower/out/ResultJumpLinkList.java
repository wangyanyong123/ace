package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultJumpLinkList implements Serializable {

    @ApiModelProperty(value = "链接页面编码（定义配置）")
    private String jumpCode;
    @ApiModelProperty(value = "跳转链接页面")
    private String jumpLink;
}
