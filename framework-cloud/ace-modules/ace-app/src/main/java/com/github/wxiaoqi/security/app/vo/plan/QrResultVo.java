package com.github.wxiaoqi.security.app.vo.plan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/15 11:44
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class QrResultVo implements Serializable {
    private static final long serialVersionUID = 8946020090789775262L;

    @ApiModelProperty(value = "空间或设备地址")
    private String addr;
    @ApiModelProperty(value = "房间Id")
    private String roomId;
    @ApiModelProperty(value = "空间编码")
    private String roomCode;
    @ApiModelProperty(value = "空间名称")
    private String roomName;
    @ApiModelProperty(value = "设备ID")
    private String eqId;
    @ApiModelProperty(value = "设备名称")
    private String eqName;
    @ApiModelProperty(value = "设备编码")
    private String eqCode;
    @ApiModelProperty(value = "是否公区(0-否1-是)")
    private String isPublic;

}
