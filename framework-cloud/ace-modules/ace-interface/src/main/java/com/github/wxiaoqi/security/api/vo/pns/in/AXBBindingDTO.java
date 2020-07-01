package com.github.wxiaoqi.security.api.vo.pns.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 号码绑定
 */
@Data
@ApiModel(description= "号码绑定")
public class AXBBindingDTO implements Serializable {

    @NotEmpty(message="缺少参数telA")
    @ApiModelProperty("A号码：主叫号码")
    private String telA; //A号码
    @NotEmpty(message="缺少参数id")
    @ApiModelProperty("订单表(biz_subscribe)ID")
    private String id;
    @ApiModelProperty("B号码：被叫号码")
    private String telB; //B号码
    @ApiModelProperty("X号码")
    private String telX; //X号码
    @ApiModelProperty("需要X号码所属区号")
    private String areaCode; //"需要X号码所属区号
    @NotEmpty(message="缺少参数customer:FAT-测试环境;PRO-生产环境.")
    @ApiModelProperty("FAT-测试环境;PRO-生产环境")
    private String customer; // 业务侧随传数据，可以是json和任意字符串
}
