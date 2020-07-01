package com.github.wxiaoqi.security.jinmao.vo.reservat.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateReservatParam implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String id;
    @ApiModelProperty(value = "操作(2-已取消,3-已联系,4-处理中(接单操作)")
    private String dealStatus;

}
