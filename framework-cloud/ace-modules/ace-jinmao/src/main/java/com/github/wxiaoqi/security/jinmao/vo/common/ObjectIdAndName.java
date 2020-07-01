package com.github.wxiaoqi.security.jinmao.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目id 。name
 *
 * @author: guohao
 * @create: 2020-04-15 18:20
 **/
@Data
public class ObjectIdAndName {

    @ApiModelProperty(value = "项目id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String name;
}
