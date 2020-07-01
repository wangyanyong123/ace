package com.github.wxiaoqi.security.app.vo.classify.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类展示实体
 *
 * @author: guohao
 * @create: 2020-04-16 19:05
 **/
@Data
public class ClassifyVo {

    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "分类级别")
    private Integer level;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "分类编码")
    private String classifyCode;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

}
