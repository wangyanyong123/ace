package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultClassifyType implements Serializable {

    private static final long serialVersionUID = -7624521605299845504L;

    @ApiModelProperty(value = "表格数据")
    private List<BusType> info;

    @ApiModelProperty(value = "业务数量占比")
    private ResultData busData;
    @ApiModelProperty(value = "报修分类数量占比")
    private ResultData repairData;
    @ApiModelProperty(value = "投诉分类数量占比")
    private ResultData cmplainData;
    @ApiModelProperty(value = "计划分类数量占比")
    private ResultData planData;
}
