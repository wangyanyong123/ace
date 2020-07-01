package com.github.wxiaoqi.security.jinmao.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BillDetailVo implements Serializable {
    private static final long serialVersionUID = -7934622940870465433L;

    @ApiModelProperty(value = "缴费单id")
    private String subId;
    @ApiModelProperty(value = "缴费单编码")
    private String subCode;
    @ApiModelProperty(value = "账单名称")
    private String title;
    @ApiModelProperty(value = "实际总金额")
    private BigDecimal actualCost;
    private String actualCostStr;
    @ApiModelProperty(value = "缴费状态")
    private String subStatus;
    @ApiModelProperty(value = "缴费状态描述")
    private String subStatusStr;
    @ApiModelProperty(value = "客户名称")
    private String name;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "时间")
    private String createTime;
    @ApiModelProperty(value = "账单明细")
    private List<BillDateVo> billFee;
    private String roomId;
    private String roomName;
    private String payDate;
    @ApiModelProperty(value = "0-未同步,1-已同步,2-同步失败")
    private String noticeStatus;
    private String noticeStaStr;
    @ApiModelProperty(value = "1-未支付,2-已支付")
    private String payStatus;
    private String payStatusStr;
    @ApiModelProperty(value = "支付方式(1-支付宝2-微信)")
    private String payType;
    private String payTypeStr;
    @ApiModelProperty(value = "发票类型(0-不开发票,1-个人,2-公司)")
    private String invoiceType;

    private String type;


    private List<UserAllBillList> userAllBillLists;


    private String invoiceName;
    @ApiModelProperty(value = "单位地址")
    private String unitAddr;
    @ApiModelProperty(value = "开户银行")
    private String bankName;
    @ApiModelProperty(value = "银行账户")
    private String bankNum;
    @ApiModelProperty(value = "电话号码")
    private String telphone;
    @ApiModelProperty(value = "税号")
    private String dutyNum;

    @ApiModelProperty(value = "所属中心城市")
    private String centerCityName;
    @ApiModelProperty(value = "公司名称")
    private String companyName;


    public String getPayTypeStr() {
        String payTypeStr = "";
        if ("1".equals(payType)) {
            payTypeStr = "支付宝";
        } else if ("2".equals(payType)) {
            payTypeStr = "微信";
        }
        return payTypeStr;
    }

    public String getType() {
        String type = "否";
        if ("1".equals(invoiceType)) {
            type = "是";
        } else if ("2".equals(invoiceType)) {
            type = "是";
        }
        return type;
    }

    public String getPayStatusStr() {
        String payStatusStr = "";
        if ("1".equals(payStatus)) {
            payStatusStr = "未支付";
        } else if ("2".equals(payStatus)) {
            payStatusStr = "已支付";
        }
        return payStatusStr;
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

    public String getActualCostStr(){
        if(actualCost!=null){
            return (actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }else{
            return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }

    }

    public String getNoticeStaStr() {
        String noticeStaStr = "";
        if ("0".equals(noticeStatus)) {
            noticeStaStr = "未同步";
        } else if ("1".equals(noticeStatus)) {
            noticeStaStr = "已同步";
        } else if ("2".equals(noticeStatus)) {
            noticeStaStr = "同步失败";
        }
        return noticeStaStr;
    }
}
