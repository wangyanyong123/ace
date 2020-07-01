package com.github.wxiaoqi.security.jinmao.vo.wo.date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultDateData implements Serializable {
    private static final long serialVersionUID = -857827930844560836L;

    @ApiModelProperty(value = "折线图信息")
    private List source;
}
