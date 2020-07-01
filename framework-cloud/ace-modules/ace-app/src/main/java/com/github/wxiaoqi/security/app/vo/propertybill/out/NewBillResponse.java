package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewBillResponse implements Serializable {

    private String code;
    private String msg;
    private BillsInfo data;

}
