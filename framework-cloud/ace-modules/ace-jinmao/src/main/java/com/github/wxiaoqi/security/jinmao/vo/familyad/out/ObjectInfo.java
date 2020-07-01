package com.github.wxiaoqi.security.jinmao.vo.familyad.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/15
 */
@Data
public class ObjectInfo implements Serializable {

    @ApiModelProperty(value = "业务对象id")
    private String id;
    @ApiModelProperty(value = "业务对象名称")
    private String name;


}
