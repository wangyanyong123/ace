package com.github.wxiaoqi.security.api.vo.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseStore implements Serializable {

    @ApiModelProperty("时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）")
    private Integer timeSlot;
}
