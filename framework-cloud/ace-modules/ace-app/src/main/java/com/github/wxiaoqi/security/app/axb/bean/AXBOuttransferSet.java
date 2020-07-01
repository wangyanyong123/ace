package com.github.wxiaoqi.security.app.axb.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 外呼转接设置
 */
@Data
@ApiModel(description= "外呼转接设置")
public class AXBOuttransferSet implements Serializable {
    @ApiModelProperty(value = "X号码")
    private String telX; //X号码
    @ApiModelProperty(value = "B号码")
    private String telC; //C号码
    @ApiModelProperty(value = "是否录音，1：录音；0：不录音")
    private String record; //是否录音，1：录音；0：不录音
    @ApiModelProperty(value = "业务侧随传数据，可以是json和任意字符串")
    private String customer; // 业务侧随传数据，可以是json和任意字符串
}
