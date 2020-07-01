package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class QRVo implements Serializable {

    @ApiModelProperty(value = "二维码尺寸(1-80*80,2-120*120,3-150*150,4-300*300,5-500*500)")
    private String size;
    @ApiModelProperty(value = "二维码路径")
    private String qrUrl;
}
