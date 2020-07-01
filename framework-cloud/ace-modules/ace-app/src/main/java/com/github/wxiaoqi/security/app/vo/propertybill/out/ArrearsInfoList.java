package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArrearsInfoList implements Serializable {


    private static final long serialVersionUID = -776987015064501988L;
    private String FilingHouseNumber;
    private String FilingHouseName;
    private String AccountReceivableId;
    private String ChargeItem;
    private String ChargeTotal;
    private String ArrearsAmount;
    private String AccountPeriod;
    private String ChargeAmount;
    private String UnitPrice;
    private String ChargeArea;
    private Date ChargeDate;
    private String MemberShipName;
    private String ChargeStatus;

}
