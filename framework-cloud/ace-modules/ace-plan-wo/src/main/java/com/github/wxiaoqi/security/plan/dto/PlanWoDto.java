package com.github.wxiaoqi.security.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:41 2019/2/26
 * @Modified By:
 */
@Data
public class PlanWoDto implements Serializable {
	private static final long serialVersionUID = 3218088158647440714L;
	private String projectCode;
	private String taskId;
	private String taskCode;
	private String taskDescribe;
	private String woType;
	private String assignedTime;
	private String projectId;

	private List<PlanWoTrDto> planWoTrDtos;

	private List<PlanWoOptDetailDto> planWoOptDetailDtos;
}
