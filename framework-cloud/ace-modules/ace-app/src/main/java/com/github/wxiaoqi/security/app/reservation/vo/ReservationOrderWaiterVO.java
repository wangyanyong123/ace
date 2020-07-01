package com.github.wxiaoqi.security.app.reservation.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 22:28:30
 */
@Data
public class ReservationOrderWaiterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	    //
    private String orderId;

	    //服务人员联系电话
    private String waiterTel;
	    //服务人员联系名称
    private String waiterName;
	    //备注
    private String remark;
	

}
