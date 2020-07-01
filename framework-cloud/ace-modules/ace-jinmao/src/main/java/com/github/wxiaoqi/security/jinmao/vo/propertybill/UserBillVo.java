package com.github.wxiaoqi.security.jinmao.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserBillVo  implements Serializable {

    private static final long serialVersionUID = -7029169089067950974L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "缴费单编码")
    private String subCode;
    @ApiModelProperty(value = "账单名称")
    private String title;
    @ApiModelProperty(value = "实际总金额")
    private BigDecimal actualCost;
    @ApiModelProperty(value = "实际总金额字符串")
    private String actualCostStr;
    @ApiModelProperty(value = "缴费状态")
    private String subStatus;
    @ApiModelProperty(value = "缴费状态描述")
    private String subStatusStr;
    @ApiModelProperty(value = "客户名称")
    private String name;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "应收ID")
    private String shouldId;
    @ApiModelProperty(value = "通知crm状态(0-未同步,1-已同步,2-同步失败)")
    private String noticeStatus;
    private String noticeStaStr;
    private String crmProjectCode;
    private String crmRoomCode;
    private String crmUserId;
    private String payDate;
    @ApiModelProperty(value = "支付方式(1-支付宝2-微信)")
    private String payType;
    private String payTypeStr;
    private String roomId;
    private String roomName;
    @ApiModelProperty(value = "1-未支付,2-已支付")
    private String payStatus;
    private String payStatusStr;
    private String shouldAmount;
    @ApiModelProperty(value = "是否开发票(0-不开发票,1-个人,2-公司)")
    private String invoiceType;
    @ApiModelProperty(value = "所属中心城市")
    private String centerCityName;
    @ApiModelProperty(value = "公司名称")
    private String companyName;



    public String getPayStatusStr() {
        String payStatusStr = "";
        if ("1".equals(payStatus)) {
            payStatusStr = "未支付";
        } else if ("2".equals(payStatus)) {
            payStatusStr = "已支付";
        }
        return payStatusStr;
    }


    public String getPayTypeStr() {
        String payTypeStr = "";
        if ("1".equals(payType)) {
            payTypeStr = "支付宝";
        } else if ("2".equals(payType)) {
            payTypeStr = "微信";
        }
        return payTypeStr;
    }


    public String getSubStatusStr(){
        String subStatusStr = "";
        if("0".equals(subStatus)){
            subStatusStr = "已下单";
        }else if("1".equals(subStatus)){
            subStatusStr = "处理中";
        }else if("2".equals(subStatus)){
            subStatusStr = "待支付";
        }else if("3".equals(subStatus)){
            subStatusStr = "已取消";
        }else if("4".equals(subStatus)){
            subStatusStr = "已完成";
        }else if("5".equals(subStatus)){
            subStatusStr = "待确认";
        }else if("6".equals(subStatus)){
            subStatusStr = "退款中";
        }else if("7".equals(subStatus)){
            subStatusStr = "退款完成";
        }
        return subStatusStr;
    }

    public String getNoticeStaStr() {
        String noticeStaStr = "";
        if ("0".equals(noticeStatus)) {
            noticeStaStr = "";
        } else if ("1".equals(noticeStatus)) {
            noticeStaStr = "已同步";
        } else if ("2".equals(noticeStatus)) {
            noticeStaStr = "同步失败";
        }
        return noticeStaStr;
    }

    public String getActualCostStr(){
        if(actualCost!=null){
            return (actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }else{
            return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }

    }
}
