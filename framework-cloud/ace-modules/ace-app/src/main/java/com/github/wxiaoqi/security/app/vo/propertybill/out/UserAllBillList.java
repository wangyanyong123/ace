package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAllBillList implements Serializable {
    private static final long serialVersionUID = 6900262900147732135L;

    private String shouldDate;

    private String year;

    private List<ShouldBillOut> shouldDateList;

    private String totalAmount;

    private String mouth;
}
