package com.github.wxiaoqi.security.jinmao.vo.coupon.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class updateStatus implements Serializable {

    private static final long serialVersionUID = 5662087954809961969L;
    @ApiModelProperty(value = "优惠券ID")
    private String id;
    @ApiModelProperty(value = "更新状态")
    private String status;
}
