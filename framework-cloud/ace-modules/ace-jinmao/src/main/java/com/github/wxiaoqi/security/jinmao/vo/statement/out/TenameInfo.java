package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TenameInfo implements Serializable {

    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系电话")
    private String contactTel;
}
