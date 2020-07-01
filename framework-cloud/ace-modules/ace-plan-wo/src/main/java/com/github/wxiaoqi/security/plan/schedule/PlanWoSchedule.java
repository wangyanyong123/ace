package com.github.wxiaoqi.security.plan.schedule;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.plan.config.PlanWoConfig;
import com.github.wxiaoqi.security.plan.dto.*;
import com.github.wxiaoqi.security.plan.entity.PlanWoTimer;
import com.github.wxiaoqi.security.plan.feign.AppFeign;
import com.github.wxiaoqi.security.plan.mapper.PlanWoMapper;
import com.github.wxiaoqi.security.plan.mapper.PlanWoTimerMapper;
import com.github.wxiaoqi.security.plan.vo.PlanWoInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
@Component
@EnableScheduling
@Slf4j
public class PlanWoSchedule {

	@Autowired
	private PlanWoMapper planWoMapper;

	@Autowired
	private PlanWoTimerMapper woTimerMapper;

	@Autowired
	private AppFeign appFeign;

	@Autowired
	private PlanWoConfig planWoConfig;

	@Scheduled(cron = "0 10 3 * * ? ")
//	@Scheduled(cron = "0 10 3/1 * * ? ")
	public void job1(){
		PlanWoTimer planWoTimer = woTimerMapper.getLast();
		PlanWoTimer woTimer = new PlanWoTimer();
		woTimer.setId(UUIDUtils.generateUuid());
		woTimer.setCreateTime(new Date());
		woTimerMapper.save(woTimer);
		String createDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(planWoTimer != null){
			createDate = dateFormat.format(planWoTimer.getCreateTime());
		}
		List<String> projectCodes = planWoConfig.getProjectcode();
		if(projectCodes == null || projectCodes.size()<1){
			projectCodes = null;
		}
		log.info(">>>>>>>>>>>>>>>>>>>开始获取计划性工单:" + System.currentTimeMillis());
		List<PlanWoDto> planWoDtos = planWoMapper.getPlanWoListByDate(createDate,projectCodes);
		for (PlanWoDto planWoDto : planWoDtos){
			log.info(">>>>>>>>>>>>>>>>>>>开始获取计划工单信息:" + dateFormat.format(new Date()));
			List<PlanWoTrDto> planWoTrDtos = planWoMapper.getWoTrListByWoId(planWoDto.getTaskId());
			List<PlanWoRDto> planWoRDtos = planWoMapper.getPlanWoRListByWoId(planWoDto.getTaskId());
			List<PlanWoOptDetailDto> planWoOptDetailDtos = new ArrayList<>();
			for (PlanWoRDto planWoRDto : planWoRDtos){
				PlanWoOptDetailDto planWoOptDetailDto = new PlanWoOptDetailDto();
				planWoOptDetailDto.setRoomId(planWoRDto.getRoomId());
				planWoOptDetailDto.setRoomCode(planWoRDto.getRoomCode());
				if(StringUtils.isNotEmpty(planWoRDto.getEqId())){
					List<PlanWoEqDto> planWoEqDtos = planWoMapper.getPlanWoEqListByEqId(planWoRDto.getEqId());
					planWoOptDetailDto.setPlanWoEqDtos(planWoEqDtos);
				}
				if(StringUtils.isNotEmpty(planWoRDto.getPmpId())){
					List<PlanWoPmpDto> planWoPmpDtos = planWoMapper.getPlanWoPmpListByPmpId(planWoRDto.getPmpId());
					List<PlanWoPmpsDto> planWoPmpsDtos = planWoMapper.getPlanWoPmpsListByPmpId(planWoRDto.getPmpId());
					planWoOptDetailDto.setPlanWoPmpDtos(planWoPmpDtos);
					planWoOptDetailDto.setPlanWoPmpsDtos(planWoPmpsDtos);
				}
				planWoOptDetailDtos.add(planWoOptDetailDto);
			}
			planWoDto.setPlanWoOptDetailDtos(planWoOptDetailDtos);
			planWoDto.setPlanWoTrDtos(planWoTrDtos);
			log.info(">>>>>>>>>>>>>>>>>>>获取计划工单信息结束:" + dateFormat.format(new Date()));
			log.info(">>>>>>>>>>>>>>>>>>>获取到计划工单信息:" +JSONObject.toJSONString(planWoDto));
			PlanWoInVo planWoInVo = new PlanWoInVo();
			planWoInVo.setCrmWoCode(planWoDto.getTaskCode());
			planWoInVo.setWoType(planWoDto.getWoType());
			planWoInVo.setDescription(planWoDto.getTaskDescribe());
			planWoInVo.setProjectId(planWoDto.getProjectId());
			planWoInVo.setComeFrom("4");
			planWoInVo.setWoStatus("00");
			planWoInVo.setExpectedServiceTimeStr(planWoDto.getAssignedTime());
			planWoInVo.setDealType("1");
			planWoInVo.setPlanWoOptDetailDtos(planWoOptDetailDtos);
			planWoInVo.setPlanWoTrDtos(planWoTrDtos);
			try{
				log.info(">>>>>>>>>>>>>>>>>>>调用接口生成计划工单信息:" +JSONObject.toJSONString(planWoInVo));
				ObjectRestResponse objectRestResponse = appFeign.createWo(planWoInVo);
				log.info(">>>>>>>>>>>>>>>>>>>调用接口生成计划工单信息返回结果:" +JSONObject.toJSONString(objectRestResponse));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		log.info(">>>>>>>>>>>>>>>>>>>获取计划性工单结束:" + System.currentTimeMillis() + "  获取到的计划工单个数:" + planWoDtos.size());
	}
}
