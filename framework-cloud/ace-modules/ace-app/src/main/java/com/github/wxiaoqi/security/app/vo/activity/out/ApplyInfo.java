package com.github.wxiaoqi.security.app.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ApplyInfo implements Serializable {

    @ApiModelProperty(value = "报名id")
    private String id;
    @ApiModelProperty(value = "订单id")
    private String subId;
    @ApiModelProperty(value = "报名状态(0=未报名，1=报名成功，2-取消报名)")
    private String applyStatus;
    @ApiModelProperty(value = "支付状态(1=待支付，2=已支付，3=退款中，4-退款完成, 5-已取消)")
    private String payStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }
}
