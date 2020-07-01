package com.github.wxiaoqi.security.jinmao.vo.reservation.order.out;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 预约服务订单详情
 * 
 */
@Data
public class ReservationOrderDetailInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	//产品名称
	private String productName;

	//规格
	private String specName;

	//图片id,多张图片逗号分隔
	private String specImg;

	//数量
	private Integer quantity;

	//单价
	private BigDecimal salesPrice;

	//单位
	private String unit;
}
