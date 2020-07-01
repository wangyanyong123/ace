package com.github.wxiaoqi.security.app.axb.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 号码绑定
 */
@Data
public class AXBBinding implements Serializable {

    private String telA; //A号码
    private String telB; //B号码
    private String telX; //X号码
    private String areaCode; //"需要X号码所属区号
    private String record; //是否录音，1：录音；0：不录音
    private String expiration; //绑定失效时间（秒）
    private String customer; // 业务侧随传数据，可以是json和任意字符串
}
