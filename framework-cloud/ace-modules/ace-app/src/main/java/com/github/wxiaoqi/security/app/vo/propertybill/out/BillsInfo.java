package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BillsInfo implements Serializable {

    List<ArrearsInfoList> ArrearsInfoList;

    List<MemberHouseList> MemberHouseList;
}
