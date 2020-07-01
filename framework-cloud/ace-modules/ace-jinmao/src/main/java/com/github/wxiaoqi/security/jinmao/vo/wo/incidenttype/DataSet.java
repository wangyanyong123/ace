package com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class DataSet implements Serializable {
    private static final long serialVersionUID = -915173201855888749L;

    @ApiModelProperty(value = "柱形图类型")
    private String[] type;
    @ApiModelProperty(value = "柱形图信息")
    private List<Map<String,String>> source;
}
