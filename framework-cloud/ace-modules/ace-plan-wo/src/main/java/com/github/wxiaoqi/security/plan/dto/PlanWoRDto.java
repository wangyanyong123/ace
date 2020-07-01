package com.github.wxiaoqi.security.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:29 2019/2/26
 * @Modified By:
 */
@Data
public class PlanWoRDto implements Serializable {
	private static final long serialVersionUID = 285564674021393189L;
	private String roomCode;
	private String roomId;
	private String eqId;
	private String pmpId;
}
