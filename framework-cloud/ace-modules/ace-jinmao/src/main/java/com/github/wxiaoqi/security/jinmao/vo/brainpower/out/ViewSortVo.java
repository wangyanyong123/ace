package com.github.wxiaoqi.security.jinmao.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ViewSortVo implements Serializable {

    @ApiModelProperty(value = "功能点id")
    private String id;
    @ApiModelProperty(value = "功能点")
    private String functionPoint;
    private String viewSort;
}
