package com.github.wxiaoqi.security.app.axb.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 外呼转接删除
 */
@Data
@ApiModel(description= "外呼转接删除")
public class AXBOuttransferDelete implements Serializable {
    @ApiModelProperty(value = "X号码")
    private String telX; //X号码
    @ApiModelProperty(value = "C号码")
    private String telC; //C号码
}
