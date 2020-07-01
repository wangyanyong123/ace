package com.github.wxiaoqi.security.app.vo.smsnotice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SmsNotice implements Serializable {
    private static final long serialVersionUID = 4388470103933675957L;


    @ApiModelProperty(value = "业务类型（1 报修 2 住户审核 3 系统通知）")
    private String msgType;
    @ApiModelProperty(value = "业务ID")
    private String objectId;
    @ApiModelProperty(value = "是否跳转(1-跳转)")
    private String isJump;
    @ApiModelProperty(value = "手机通知跳转页")
    private String page;
    @ApiModelProperty(value = "消息标题")
    private String msgTitle;
    @ApiModelProperty(value = "消息内容")
    private String msgContent;

}
