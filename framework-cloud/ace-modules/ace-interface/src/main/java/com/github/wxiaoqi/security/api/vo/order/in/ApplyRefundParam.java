package com.github.wxiaoqi.security.api.vo.order.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;
import schemasMicrosoftComOfficeOffice.STInsetMode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ApplyRefundParam extends BaseIn {
    @ApiModelProperty(value = "业务类型 0：旧业务 ，1：商品订单，2：服务订单")
    private int busType=0;
    @ApiModelProperty(value = "订单id")
    private String subId;
    @ApiModelProperty(value = "商户Id")
    private String tenantId;
    @ApiModelProperty(value = "发起人类型 1、买家，2、商业人员")
    private String userType;
    @ApiModelProperty(value = "是否发货")
    private boolean isSend;

    @ApiModelProperty(value = "订单编码")
    private String subCode;

    @ApiModelProperty(value = "订单标题")
    private String subTitle;

    @ApiModelProperty(value = "订单创建时间")
    private Date subCreateTime;

    @ApiModelProperty(value = "订单所属用户")
    private String userId;

    @ApiModelProperty(value = "申请金额")
    private BigDecimal applyPrice;

    @ApiModelProperty(value = "实际支付id")
    private String actualId;
    @ApiModelProperty(value = "项目id")
    private String projectId;

    @Override
    public void check() {

        Assert.hasLength(subId,"单据id为空");
        Assert.hasLength(subCode,"单据编码为空");
        Assert.hasLength(subTitle,"单据标题为空");
        Assert.notNull(subCreateTime,"单据创建时间为空");
        Assert.hasLength(userId,"单据所属用户id为空");
        Assert.hasLength(tenantId,"商户id为空");
        Assert.hasLength(userType,"发起人类型 为空");
        Assert.hasLength(actualId,"实际支付id为空");
        Assert.notNull(applyPrice,"申请金额为空");

    }
}
