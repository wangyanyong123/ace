package com.github.wxiaoqi.security.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:54 2019/2/27
 * @Modified By:
 */
@Data
public class PlanWoOptDetailDto implements Serializable {
	private static final long serialVersionUID = -6345563467183485779L;
	private String roomId;
	private String roomCode;
	private List<PlanWoEqDto> planWoEqDtos;
	private List<PlanWoPmpDto> planWoPmpDtos;
	private List<PlanWoPmpsDto> planWoPmpsDtos;
}
