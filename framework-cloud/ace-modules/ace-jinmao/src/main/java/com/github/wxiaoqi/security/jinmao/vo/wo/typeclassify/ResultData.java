package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ResultData implements Serializable {
    private static final long serialVersionUID = 495596349493954315L;

    @ApiModelProperty(value = "name:分类名称value:分类数量")
    private List<Map<String,String>> source;

}
