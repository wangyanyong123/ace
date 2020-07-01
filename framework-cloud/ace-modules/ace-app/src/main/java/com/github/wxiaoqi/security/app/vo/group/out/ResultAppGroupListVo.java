package com.github.wxiaoqi.security.app.vo.group.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultAppGroupListVo implements Serializable {

    private static final long serialVersionUID = 2426718580311099568L;
    @ApiModelProperty(value = "分类名称")
    private String classifyName;
    private List<ResultGroupListVo> groupListVo;

}
