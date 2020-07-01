package com.github.wxiaoqi.security.schedulewo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class SmsNoticeVo implements Serializable {
    private static final long serialVersionUID = 5218425066952198642L;

    @ApiModelProperty(value = "消息内容")
    /**
     * 数组对应的字段如下：
     * 1.业务类型（1-报修工单,2-住户审核,3-系统通知,4.投诉工单）
     * 2.是否跳转(0-跳转，1-跳转)
     * 3.手机通知跳转页
     * 4.消息标题
     * 5.消息内容
     */
    private String[] smsNotice;
    @ApiModelProperty(value = "业务ID")
    private String objectId;
    @ApiModelProperty(value = "接收者ID")
    private String receiverId;
    @ApiModelProperty(value = "接收参数")
    private Map<String,String> paramMap;

}
