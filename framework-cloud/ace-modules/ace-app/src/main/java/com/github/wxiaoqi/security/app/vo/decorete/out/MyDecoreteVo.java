package com.github.wxiaoqi.security.app.vo.decorete.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyDecoreteVo implements Serializable {

    @ApiModelProperty(value = "装修申请id")
    private String id;
    @ApiModelProperty(value = "服务名称")
    private String address;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "工单状态")
    private String decoreteStatus;
    @ApiModelProperty(value = "工单id")
    private String woId;
}
