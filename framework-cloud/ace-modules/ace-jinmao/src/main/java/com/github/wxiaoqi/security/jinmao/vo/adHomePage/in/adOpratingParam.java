package com.github.wxiaoqi.security.jinmao.vo.adHomePage.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class adOpratingParam implements Serializable {

    @ApiModelProperty(value = "广告id")
    private String id;
    @ApiModelProperty(value = "0-删除,2-发布,3-撤回")
    private String pStatus;
}
