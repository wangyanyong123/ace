package com.github.wxiaoqi.security.app.vo.visitor.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class VisitInfoVo implements Serializable {

    private static final long serialVersionUID = 1450544442111993929L;

    @ApiModelProperty(value = "访客姓名")
    private String name;
    @ApiModelProperty(value = "访客姓名")
    private String visitTime;
    @ApiModelProperty(value = "到访地址")
    private String visitAddr;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "二维码值")
    private String qrVal;
    @ApiModelProperty(value = "状态(1-正常2-已过期)")
    private String status;
    @ApiModelProperty(value = "访客人脸图片")
    private String visitPhoto;
}
