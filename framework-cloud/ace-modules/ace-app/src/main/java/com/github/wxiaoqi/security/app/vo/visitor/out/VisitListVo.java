package com.github.wxiaoqi.security.app.vo.visitor.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class VisitListVo implements Serializable {
    private static final long serialVersionUID = 2405400733790836872L;

    @ApiModelProperty(value = "访客ID")
    private String id;
    @ApiModelProperty(value = "访客姓名")
    private String name;
    @ApiModelProperty(value = "到访时间")
    private String visitTime;
    @ApiModelProperty(value = "通行状态(1-正常2-已过期)")
    private String status;
}
