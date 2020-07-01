package com.github.wxiaoqi.security.app.vo.postage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PostageInfo implements Serializable {
    private static final long serialVersionUID = 2666796842996570400L;

    @ApiModelProperty(value = "邮费")
    private BigDecimal postage;
    @ApiModelProperty(value = "商家ID")
    private String companyId;
    @ApiModelProperty(value = "邮费规则")
    private List<String> postageRules;

}
