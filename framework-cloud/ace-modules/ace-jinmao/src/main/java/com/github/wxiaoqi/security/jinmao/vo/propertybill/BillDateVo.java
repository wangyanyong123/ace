package com.github.wxiaoqi.security.jinmao.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BillDateVo implements Serializable {
    private static final long serialVersionUID = -5246108101148510574L;

    @ApiModelProperty(value = "所属账期")
    private String shouldDate;
    @ApiModelProperty(value = "收费科目(1-物业管理费,2-车位费)")
    private String item;
    private String itemStr;
    @ApiModelProperty(value = "收费金额")
    private BigDecimal shouldAmount;
    @ApiModelProperty(value = "账期id")
    private String shouldId;
    private String shouldAmountStr;
    @ApiModelProperty(value = "通知crm状态(0-未同步,1-已同步,2-同步失败)")
    private String noticeStatus;
    private String noticeStaStr;
    private String year;
    private String mouth;


    public String getShouldAmountStr() {
        if (shouldAmount != null) {
            return (shouldAmount.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        } else {
            return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }
    }

    public String getItemStr() {
        String itemStr = "";
        if ("1".equals(item)) {
            itemStr = "物业管理费";
        } else if ("2".equals(item)) {
            itemStr = "车位费";
        }
        return itemStr;
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
