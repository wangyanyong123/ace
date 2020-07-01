package com.github.wxiaoqi.security.app.vo.productcomment.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommitProductCommentIn extends BaseIn {

    private static final long serialVersionUID = -4528277526620197670L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @ApiModelProperty(value = "商品id")
    private String productId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论分值 0~5")
    private Integer commentScore;

    @ApiModelProperty(value = "图片地址，多个逗号分隔")
    private String imgUrl;

    @Override
    public void check() {
        Assert.hasLength(orderId,"订单id不能为空");
        Assert.hasLength(content,"评论内容不能为空");
        if(StringUtils.hasLength(orderDetailId)){
            Assert.hasLength(productId,"商品id不能为空");
        }
    }
}
