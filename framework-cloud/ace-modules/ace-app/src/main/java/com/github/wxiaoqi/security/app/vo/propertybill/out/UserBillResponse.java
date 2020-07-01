package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBillResponse implements Serializable {
    private String code;
    private String describe;
    private String totalAmount;
    private List<ShouldBillOut> data;



}
