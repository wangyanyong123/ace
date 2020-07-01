package com.github.wxiaoqi.security.api.vo.logistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 订单物流信息表
 *
 * @author wangyanyong
 * @Date 2020-04-24 16:49:37
 */
@Data
public class OrderLogisticsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="缺少参数orderId")
    @ApiModelProperty(value = "订单ID" ,required = true)
    private String orderId;

    @NotEmpty(message="缺少参数expressCompanyId")
    @ApiModelProperty(value = "快递公司ID" ,required = true)
    private String expressCompanyId;

    @NotEmpty(message="缺少参数logisticsNo")
    @ApiModelProperty(value = "快递单号" ,required = true)
    private String logisticsNo;

    @ApiModelProperty(value = "备注")
    private String remark;

}
