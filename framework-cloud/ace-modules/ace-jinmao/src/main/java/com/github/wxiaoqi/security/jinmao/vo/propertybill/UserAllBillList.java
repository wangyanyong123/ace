package com.github.wxiaoqi.security.jinmao.vo.propertybill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UserAllBillList implements Serializable {
    private static final long serialVersionUID = 6900262900147732135L;

    private String shouldDate;

    private String year;
    private String mouth;

    private List<BillDateVo> shouldDateList;

    private BigDecimal shouldAmount;

    @ApiModelProperty(value = "收费金额")
    private String totalAmount;

    public String getTotalAmount() {
        if (shouldAmount != null) {
            return (shouldAmount.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        } else {
            return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
        }
    }
}
