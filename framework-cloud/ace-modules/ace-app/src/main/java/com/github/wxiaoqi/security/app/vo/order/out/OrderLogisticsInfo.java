package com.github.wxiaoqi.security.app.vo.order.out;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrderLogisticsInfo implements Serializable {

    private static final long serialVersionUID = 2492381979867320533L;
    private String logisticsCode;

    private String logisticsName;

    private String logisticsNo;

    private Date sendTime;
}
