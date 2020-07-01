package com.github.wxiaoqi.security.app.vo.propertybill.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserPropertyBill implements Serializable {
    private static final long serialVersionUID = -619757908938783396L;

    private String item;

    private String shouldAmount;

    private String shouldDate;

    private String payStatus;

    private String year;

    private String mouth;

    private String itemStr;

    public String getItemStr(){
        String itemStr = "";
        if("1".equals(item)){
            itemStr = "物业管理费";
        }else if("2".equals(item)){
            itemStr = "车位费";
        }
        return itemStr;
    }
}
