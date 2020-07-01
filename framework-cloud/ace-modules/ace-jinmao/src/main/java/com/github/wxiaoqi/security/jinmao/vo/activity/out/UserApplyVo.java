package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserApplyVo implements Serializable {

    private String userId;
    private String userName;

    private String userTel;

    private String applyTime;
    @ApiModelProperty(value = "签到类型：1=未签到，2=已签到")
    private String signType;
}
