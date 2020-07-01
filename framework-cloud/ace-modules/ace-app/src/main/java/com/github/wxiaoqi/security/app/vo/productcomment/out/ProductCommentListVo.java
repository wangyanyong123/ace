package com.github.wxiaoqi.security.app.vo.productcomment.out;

import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class ProductCommentListVo implements Serializable {
    private static final long serialVersionUID = -7601515727291749782L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "商品id")
    private String productId;

    @ApiModelProperty(value = "评分")
    private int commentScore;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论图片")
    private String imgUrl;
    @ApiModelProperty(value = "客户昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String userHeadUrl;
    @ApiModelProperty(value = "客户id")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public List<String> getImgUrlList(){
        if(StringUtils.isNotEmpty(imgUrl)){
            return Arrays.asList(imgUrl.split(","));
        }else{
            return Collections.emptyList();
        }
    }

}
