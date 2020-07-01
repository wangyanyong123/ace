package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultAgingData implements Serializable {

    private static final long serialVersionUID = 670699065717823948L;

    @ApiModelProperty(value = "饼图信息")
    private List<WoAgingData> source;
}
