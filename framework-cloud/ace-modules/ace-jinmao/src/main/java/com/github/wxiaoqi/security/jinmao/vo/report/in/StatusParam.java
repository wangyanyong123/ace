package com.github.wxiaoqi.security.jinmao.vo.report.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusParam implements Serializable {

    @ApiModelProperty(value = "0:禁止评论、1:允许评论")
    private String status;
    @ApiModelProperty(value = "被举报人Id'")
    private String beUserId;
}
