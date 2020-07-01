package com.github.wxiaoqi.security.app.reservation.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
@Data
public class ReservationOrderOperationRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderId;

	private Integer stepStatus;

	private String currStep;

	private String description;

	private Date createTime;
}
