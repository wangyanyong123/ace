package com.github.wxiaoqi.security.jinmao.vo.brainpower.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UpdateIsShowParam implements Serializable {

    @ApiModelProperty(value = "0-取消置底，1-置底,2-发布，3-撤回")
    private String status;
    @ApiModelProperty(value = "功能点id/问题id")
    private String id;

    private List<Map<String,String>> sortInfo;
}
