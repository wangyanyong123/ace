package com.github.wxiaoqi.security.app.vo.plan;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:10 2019/3/1
 * @Modified By:
 */
@Data
public class PlanWoDetail implements Serializable {
	private static final long serialVersionUID = 8677545270519313958L;
	private String id;
	private String woId;
	private String pmpId;
	private String houseCode;
	private String houseName;
	private String isComplete;
	private String eqId;
	private String eqCode;
}
