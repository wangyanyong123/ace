package com.github.wxiaoqi.security.jinmao.vo.reservat.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateStatusParam implements Serializable {


    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "操作(1.申请发布,2发布,3撤回,4驳回)")
    private String status;
}
