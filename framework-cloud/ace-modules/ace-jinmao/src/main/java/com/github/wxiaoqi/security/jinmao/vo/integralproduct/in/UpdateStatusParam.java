package com.github.wxiaoqi.security.jinmao.vo.integralproduct.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.EAN;

import java.io.Serializable;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class UpdateStatusParam implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "操作(2上架,3下架)")
    private String status;
    @ApiModelProperty(value = "推荐(0表示不推荐，1表示推荐)")
    private String isRecommend;
    @ApiModelProperty(value = "1-上下架操作,2-推荐操作")
    private String type;

    private List<String> projectId;
}
