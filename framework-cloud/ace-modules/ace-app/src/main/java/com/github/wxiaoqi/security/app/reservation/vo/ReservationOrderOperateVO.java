package com.github.wxiaoqi.security.app.reservation.vo;

import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 预约服务订单表
 * 
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@Data
public class ReservationOrderOperateVO implements Serializable {
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号orderId")
    private String orderId;
    @ApiModelProperty(value = "操作ID")
    private String operateId;
//    @ApiModelProperty(value = "日志内容")
//    private TransactionLogBean transactionLogBean;
//    @ApiModelProperty(value = "指派/转派用户Id")
//    private String handleBy;
    @ApiModelProperty(value = "当前用户Id")
    private String userId;

//    @ApiModelProperty(value = "快递公司(商品订单)")
//    private String expressCompany;
//    @ApiModelProperty(value = "快递单号(商品订单)")
//    private String expressNum;
}
