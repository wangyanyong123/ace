package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountBook implements Serializable {
    private static final long serialVersionUID = 2972682182265592416L;

    private String payType;

    private String payAmount;

    private String chargeTime;

    private String payStatus;

}
