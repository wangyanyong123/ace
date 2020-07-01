package com.github.wxiaoqi.security.app.vo.product.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PostageInfoVo implements Serializable {
    private static final long serialVersionUID = -9135422525958990657L;

    @ApiModelProperty(value = "起算金额")
    private String startAmount;
    @ApiModelProperty(value = "截止金额(-1表示只算起算金额)")
    private String endAmount;
    @ApiModelProperty(value = "邮费金额")
    private String postage;

    public String getStartAmount() {
        if (startAmount.indexOf(".") > 0) {
            // 去掉多余的0
            startAmount = startAmount.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            startAmount = startAmount.replaceAll("[.]$", "");
        }
        return startAmount;
    }

    public String getEndAmount() {
        if (endAmount.indexOf(".") > 0) {
            // 去掉多余的0
            endAmount = endAmount.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            endAmount = endAmount.replaceAll("[.]$", "");
        }
        return endAmount;
    }

    public String getPostage() {
        if (postage.indexOf(".") > 0) {
            // 去掉多余的0
            postage = postage.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            postage = postage.replaceAll("[.]$", "");
        }
        return postage;
    }


}
