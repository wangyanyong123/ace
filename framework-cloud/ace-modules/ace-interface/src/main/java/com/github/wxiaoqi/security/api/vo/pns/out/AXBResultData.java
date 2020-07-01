package com.github.wxiaoqi.security.api.vo.pns.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 平台返回的结果中的Data参数
 */
@Data
@ApiModel(description= "平台返回的结果中的Data参数")
public class AXBResultData implements Serializable{
        @ApiModelProperty(value = "绑定ID")
        private String bindId;
        @ApiModelProperty(value = "X号码")
        private String telX;
}
