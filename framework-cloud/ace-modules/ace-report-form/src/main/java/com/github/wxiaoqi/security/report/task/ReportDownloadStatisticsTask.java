package com.github.wxiaoqi.security.report.task;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.report.biz.ReportDownloadStatisticsBiz;
import com.github.wxiaoqi.security.report.biz.ReportRegisterStatisticsBiz;
import com.github.wxiaoqi.security.report.feign.SyncWorkStateFeign;
import com.github.wxiaoqi.security.report.mapper.BizWoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:26 2019/1/29
 * @Modified By:
 */
@Component
@EnableScheduling
@Slf4j
public class ReportDownloadStatisticsTask {
	@Autowired
	private ReportDownloadStatisticsBiz statisticsBiz;

	@Autowired
	private SyncWorkStateFeign syncWorkStateFeign;

	@Autowired
	private ReportRegisterStatisticsBiz reportRegisterStatisticsBiz;

	@Autowired
	private BizWoMapper bizWoMapper;

	public static boolean syncWorkFlag = true;


//	@Scheduled(cron = "0 0/5 * * * ? ")
	@Scheduled(cron = "0 0 0/1 * * ? ")
	public void job1(){
		log.info("统计今天的app下载认证量:" + new Date());
		statisticsBiz.TodayDownloadAndAuthStatistics();
	}

//	@Scheduled(cron = "0 35 10 * * ?")
	@Scheduled(cron = "1 0 0 * * ?")
	public void job2(){
		log.info("统计昨天的app下载认证量:" + new Date());
		statisticsBiz.YesterdayDownloadAndAuthStatistics();
	}

	@Scheduled(cron = "0 10 0 * * ?")
	public void job3(){
		log.info("统计昨天的楼栋认证用户量:" + new Date());
		reportRegisterStatisticsBiz.buildRegisterStatistics();
	}

	//去除同步工单到CRM的任务，转到调度引擎系统同步了
	//@Scheduled(cron = "*/30 * * * * ?")
	public void syncWorkJob() {
		if(syncWorkFlag){
			log.info("开始同步工单任务"+ DateUtils.formatDateTime(new Date()));
			syncWorkFlag = false;
			Config config = ConfigService.getConfig("ace-app");
			//获取可同步项目Code
			String projectIds = config.getProperty("crm.projectIds", "BJ-YAJMY");
			//获取是否开启同步定时任务
			String isSyncTask = config.getProperty("crm.isSyncTask", "1");
			if (isSyncTask!=null && "1".equals(isSyncTask)) {
				log.info("开启同步工单任务");
				if (projectIds != null) {
					String[] projectCode = projectIds.split(",");
					if (projectCode.length > 0) {
						List<String> projectList = Stream.of(projectCode).collect(Collectors.toList());
						List<String> noSyncWoIds = bizWoMapper.getNoSyncWoIds(projectList);
						//获取未同步/同步失败工单
						if (noSyncWoIds!=null && noSyncWoIds.size() > 0) {
							for (String woId : noSyncWoIds) {
								try {
									//同步工单
									syncWorkStateFeign.syncWoToCRMNoUserLogin(woId);
								} catch (Exception e) {
									log.info("同步工单异常",e);
								}
							}
						}
					}
				}
			}
			syncWorkFlag = true;
			log.info("结束同步工单任务"+ DateUtils.formatDateTime(new Date()));

		}

	}
}
