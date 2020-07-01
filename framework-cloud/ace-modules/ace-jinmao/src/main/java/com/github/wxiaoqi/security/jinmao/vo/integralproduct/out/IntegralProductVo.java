package com.github.wxiaoqi.security.jinmao.vo.integralproduct.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class IntegralProductVo implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    private String productNum;
    private String productClassify;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "申请时间")
    private String publishTime;
    @ApiModelProperty(value = "业务状态(1-待发布,2-已发布3-已下架）")
    private String busStatus;
    @ApiModelProperty(value = "服务范围")
    private String projectName;
    @ApiModelProperty(value = "已兑换数")
    private int cashNum;
    @ApiModelProperty(value = "商品是否推荐，0表示不推荐，1表示推荐")
    private String isRecommend;


}
