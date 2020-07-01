package com.github.wxiaoqi.security.jinmao.vo.productorder.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryProductOrderIn extends BaseQueryIn {
    private static final long serialVersionUID = -2959235075312870419L;

    @ApiModelProperty(value = "订单类型 1：普通订单；2：团购订单。3：秒杀订单")
     private Integer orderType;

    @ApiModelProperty(value = "商户id")
     private String tenantId;

    @ApiModelProperty(value = "项目id")
     private List<String> projectId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
     private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
     private Date endDate;

    @ApiModelProperty(value = "订单编号/标题/客户名称")
     private  String searchVal;

    @ApiModelProperty(value = "订单状态 5：待支付，6：拼团中， 10：待发货" +
            "20：待签收 ，30待评价35：已完成；40：退款中 ；45：已取消；50：退款完成")
     private Integer queryOrderStatus;

    @ApiModelProperty(value = "订单状态 5：待支付，6：拼团中， 10：待发货，15：部分发货" +
            "20：待签收 ，35：已完成 ；45：已关闭；")
     private Integer orderStatus;

    @ApiModelProperty(value = "退款状态：0：无退款，10：退款中，15:部分退款 20：已退款")
     private Integer refundStatus;

    @ApiModelProperty(value = "是否评论 0：未评论，1.已评论")
     private Integer commentStatus;


    @Override
    protected void doCheck() {
        initStatusByQueryStatus();

    }

    private void initStatusByQueryStatus(){
        if(queryOrderStatus == null){
            return ;
        }
        if(AceDictionary.ORDER_STATUS_APPLY_REFUND.equals(queryOrderStatus)){
            //退款中
            refundStatus = AceDictionary.ORDER_REFUND_STATUS_APPLY;
        }else if(AceDictionary.ORDER_STATUS_REFUND_COM.equals(queryOrderStatus)){
            //退款完成
            refundStatus = AceDictionary.ORDER_REFUND_STATUS_COM;
        }
        else if(AceDictionary.ORDER_STATUS_W_COMMENT.equals(queryOrderStatus)) {
            //待评价
            orderStatus = AceDictionary.ORDER_REFUND_STATUS_COM;
            commentStatus = AceDictionary.PRODUCT_COMMENT_NONE;
        }else{
            orderStatus = queryOrderStatus;
        }
    }
}
