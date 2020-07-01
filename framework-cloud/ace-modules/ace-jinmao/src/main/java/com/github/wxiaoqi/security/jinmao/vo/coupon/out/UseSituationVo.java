package com.github.wxiaoqi.security.jinmao.vo.coupon.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class UseSituationVo implements Serializable {
    private static final long serialVersionUID = 1918881151335958899L;

    @ApiModelProperty(value = "优惠券编码")
    private String couponCode;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户电话")
    private String phone;
    @ApiModelProperty(value = "订单号")
    private String subCode;
    @ApiModelProperty(value = "使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)")
    private String useStatus;
    private String useStatusStr;

    public String getUseStatusStr() {
        String useStatusStr = "";
        if ("0".equals(useStatus)) {
            useStatusStr = "未领取";
        }else if ("1".equals(useStatus)) {
            useStatusStr = "已领取";
        }else if("2".equals(useStatus)) {
            useStatusStr = "已使用";
        }else if ("3".equals(useStatus)) {
            useStatusStr = "已退款";
        }else if ("4".equals(useStatus)) {
            useStatusStr = "已过期";
        }
        return useStatusStr;
    }
}
