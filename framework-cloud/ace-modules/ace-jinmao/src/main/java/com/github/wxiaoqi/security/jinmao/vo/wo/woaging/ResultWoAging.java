package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultWoAging implements Serializable {
    private static final long serialVersionUID = 5531047636775763928L;
    @ApiModelProperty(value = "表格数据")
    private List<AgingType> info;

    @ApiModelProperty(value = "下单到接单占比")
    private ResultAgingData createToReceiveData;
    @ApiModelProperty(value = "下单到完成占比")
    private ResultAgingData receiveToFinishData;
    @ApiModelProperty(value = "下单到完成占比")
    private ResultAgingData createToFinishData;
}
