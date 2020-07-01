package com.github.wxiaoqi.security.app.axb.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 话单
 */
@Data
@ApiModel(description= "话单")
public class AXBCallbackCall implements Serializable {
    @ApiModelProperty(value = "通话Id")
    private String callId; //	通话Id
    @ApiModelProperty(value = "绑定Id，和绑定接口返回的bindId一致")
    private String bindId; // 绑定Id，和绑定接口返回的bindId一致
    @ApiModelProperty(value = "主叫号码 格式：手机或固话座机")
    private String ani; // 主叫号码 格式：手机或固话座机
    @ApiModelProperty(value = "被叫号码 格式：手机或固话座机")
    private String dnis; // 被叫号码 格式：手机或固话座机
    @ApiModelProperty(value = "X号码")
    private String telX; // X号码
    @ApiModelProperty(value = "AXB、AX等")
    private String modeType; // AXB、AX等
    @ApiModelProperty(value = "通话时长，单位：秒")
    private Integer talkingTimeLen; // 通话时长，单位：秒
    @ApiModelProperty(value = "拨打时间 格式： yyyy-MM-dd hh:mm:ss")
    private String startTime; // 	拨打时间 格式： yyyy-MM-dd hh:mm:ss
    @ApiModelProperty(value = "通话时间 格式： yyyy-MM-dd hh:mm:ss")
    private String talkingTime; // 通话时间 格式： yyyy-MM-dd hh:mm:ss
    @ApiModelProperty(value = "挂断时间 格式： yyyy-MM-dd hh:mm:ss")
    private String endTime; // 挂断时间 格式： yyyy-MM-dd hh:mm:ss
    @ApiModelProperty(value = "挂机结束方 (0表示平台释放,1表示主叫，2表示被叫)")
    private Integer endType; // 挂机结束方 (0表示平台释放,1表示主叫，2表示被叫)
    @ApiModelProperty(value = "挂机状态原因")
    private Integer endState; //	挂机状态原因
    @ApiModelProperty(value = "通话录音地址")
    private String recUrl; //	通话录音地址
    @ApiModelProperty(value = "业务侧随传数据，可以是json和任意字符串")
    private String customer; // 	业务侧随传数据，可以是json和任意字符串
}
