package com.github.wxiaoqi.security.jinmao.vo.statement.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateParam implements Serializable {

    @ApiModelProperty(value = "1-确认账单,2-支付")
    private String status;
    @ApiModelProperty(value = "结算图片,状态为2则传")
    private List<ImgInfo> balanceImgList;
    @ApiModelProperty(value = "账单id")
    private String id;
}
