package com.github.wxiaoqi.security.schedulewo.job;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.schedulewo.biz.BizWoBiz;
import com.github.wxiaoqi.security.schedulewo.common.WODispatchPool;
import com.github.wxiaoqi.security.schedulewo.constant.DBConstant;
import com.github.wxiaoqi.security.schedulewo.entity.*;
import com.github.wxiaoqi.security.schedulewo.mapper.BizFlowDispatchRuleMapper;
import com.github.wxiaoqi.security.schedulewo.mapper.BizFlowSkillsMapper;
import com.github.wxiaoqi.security.schedulewo.vo.BusDispatchRule;
import com.github.wxiaoqi.security.schedulewo.vo.MatchingCondition;
import com.github.wxiaoqi.security.schedulewo.vo.SrsWo;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
* @author xufeng
* @Description: 工单匹配
* @date 2015-6-4 下午7:02:06
* @version V1.0
*
 */
public class WoDispatchJob implements Job{

	private static Logger _log = LoggerFactory.getLogger(WoDispatchJob.class);

	private static BizFlowSkillsMapper bizFlowSkillsMapper;
	private static BizFlowDispatchRuleMapper bizFlowDispatchRuleMapper;


	private static BizWoBiz bizWoBiz;
//	private static SrsCompanyServiceDao srsCompanyServiceDao;
	private static volatile boolean canDispatch = true;
	private static final String GROUP_MATCHING = "GROUP_MATCHING";
	private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private boolean isFrist = true;

	@Override
	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {

		while (canDispatch) {
			try {
				SrsWo wo =  WODispatchPool.get();
				_log.debug("工单匹配.........");
				dispatch( wo);
			} catch (InterruptedException e) {
				_log.error("指派工单异常!" , e );
			}
		}

	}

	/**
	 * 启动匹配任务
	 * @param wo
	 */
	private void dispatch(SrsWo wo){
		init();
		final SrsWo srsWO = wo;
		threadPool.execute(
				new Runnable() {

					@Override
					public void run() {
						//派单、抢单处理
						String dealType = srsWO.getDealType();
						if(DBConstant.WoDealType.LEAFLET.toString().equals(dealType) || DBConstant.WoDealType.GRAB.toString().equals(dealType)
								|| DBConstant.WoDealType.APPOINT.toString().equals(dealType)){
							generalDispatch(srsWO);
						}else if(DBConstant.WoDealType.APPOINT.toString().equals(dealType)){//特定的服务资源
							//appointDispatch(srsWO);
						}else if(DBConstant.WoDealType.INTERFACE.toString().equals(dealType)){//接口
							//dispatchToInterface(srsWO);
						}
					}
				}
			);

	}


	/**
	 *
	 * 抢单、指派、特定服务资源处理
	 */
	private void  generalDispatch(SrsWo srsWO){
//		List<BizFlowSkills> skillsList = bizFlowSkillsMapper.getSkillsByFlowId(srsWO.getFlowId());

//		if(skillsList == null && skillsList.size()==0){
//			_log.error("没有查到相关工单需要技能,转成人工调度!工单ID为：{}." , srsWO.getId());
//			SrsWo wo = new SrsWo();
//			wo.setId(srsWO.getId());
//			wo.setDealType(DBConstant.WoDealType.MANUALHANDLING.toString());
//			bizWoBiz.updateWoDealType(wo);
//			return;
//		}


		//团购，不需要发送推送消息
//		if(ConfigHolder.getProperty("business.groupBuy.id", "").equals(srsWO.getBussId())){
//			return;
//		}

		//查询公司的服务资源
//		SrsCompanyService record = new SrsCompanyService();
//		record.setProcessId(srsWO.getBusStepId());
//		record.setProjectId(srsWO.getProjectId());
//		if(!StringUtils.isEmpty(srsWO.getAreaId())){
//			record.setBuildingId(srsWO.getAreaId());
//		}
//		if(!StringUtils.isEmpty(srsWO.getCompanyId())){
//			record.setCompanyId(srsWO.getCompanyId());
//		}
//		List<SrsCompanyService> serviceList = srsCompanyServiceDao.selectByParam(record);
//		List<String> appointSrsIds = new ArrayList<String>();
//		if(serviceList!=null && serviceList.size()>0){
//			for (int i = 0; i < serviceList.size(); i++) {
//				appointSrsIds.add(serviceList.get(i).getServiceId());
//			}
//		}else{
//			_log.error("没有配置服务资源,转成人工调度!");
//			SrsWo wo = new SrsWo();
//			wo.setId(srsWO.getId());
//			wo.setDealType(DBConstant.WoDealType.MANUALHANDLING.toString());
//			bizWoBiz.updateWoDealType(wo);
//			return;
//		}

		BusDispatchRule rule = bizFlowDispatchRuleMapper.getDispatchRuleByFlowId(srsWO.getFlowId());
		_log.info("调度规则：Crontab={},Minutes={},DispatchTimes={}" , rule.getCrontab(), rule.getMinutes(),rule.getDispatchTime());

		MatchingCondition matchingCondition = new MatchingCondition();
		batchSet(matchingCondition, srsWO);

		JobDetail jobDetail = JobBuilder.newJob(MatchingSrsJob.class).withIdentity(srsWO.getId() , GROUP_MATCHING).build();
		jobDetail.getJobDataMap().put(MatchingSrsJob.MATCHING_CONDITION, matchingCondition);
		jobDetail.getJobDataMap().put(MatchingSrsJob.DISPATCH_RULE, rule);
		jobDetail.getJobDataMap().put(MatchingSrsJob.DISPATCH_TIME, 0);

		Trigger trigger = null;
		if(rule.getMinutes()>0){
			trigger = TriggerBuilder.newTrigger()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(rule.getMinutes()).withRepeatCount(rule.getDispatchTime()))
					.withIdentity(srsWO.getId() , GROUP_MATCHING)
					.startNow()
					.build();//调度每隔n分钟，连续调用n次
		}else{
			trigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule(rule.getCrontab()))
					.withIdentity(srsWO.getId() , GROUP_MATCHING)
					.startNow()
					.build();//调度cron表达式  "*/5 * * * * ?"
		}
		GlobalSchedule.scheduleDispatchWOJob(jobDetail, trigger);

		//busStepService.getWoStatusAndAddWoStep(DBConstant.WoStatus.YPC.toString() , srsWO.getId() , busStep.getId() , "已派出" , null , null);
		//runOnetiems(matchingCondition);
	}

	private void runOnetiems(MatchingCondition matchingCondition){
		try {
			JobDetail jobDetail = JobBuilder.newJob(MatchingSrsJob.class).
					withIdentity(UUID.randomUUID().toString() , GROUP_MATCHING).build();
			BusDispatchRule rule = new BusDispatchRule("0/1 * * * * ?" , -1 ,0);
			jobDetail.getJobDataMap().put(MatchingSrsJob.MATCHING_CONDITION, matchingCondition);
			jobDetail.getJobDataMap().put(MatchingSrsJob.DISPATCH_RULE, rule);
			jobDetail.getJobDataMap().put(MatchingSrsJob.DISPATCH_TIME, 0);
			GlobalSchedule.scheduleDispatchWOJob(jobDetail, TriggerBuilder.newTrigger().build());
		} catch (Exception e) {
			_log.error("匹配工单异常!" , e);
		}

	}







	private void batchSet(MatchingCondition matchingCondition , SrsWo srsWO ){
		matchingCondition.setWoId(srsWO.getId());
		matchingCondition.setFlowId(srsWO.getBussId());//技能
		matchingCondition.setDealType(srsWO.getDealType());
		matchingCondition.setProjectId(srsWO.getProjectId());
		matchingCondition.setLandId(srsWO.getAreaId());
		matchingCondition.setBuildId(srsWO.getAreaId());
		matchingCondition.setWoTitle(srsWO.getWoSubject());//主题
		matchingCondition.setWoDesc(srsWO.getDescription());//描述
		matchingCondition.setSkillList(null);
	}


	private void init(){
		if(isFrist){
			bizFlowSkillsMapper = BeanUtils.getBean(BizFlowSkillsMapper.class);
			bizFlowDispatchRuleMapper   = BeanUtils.getBean(BizFlowDispatchRuleMapper.class);
//			srsCompanyServiceDao   = Utils.getSpringBean(SrsCompanyServiceDao.class);
//			orderEngineBussiness   = Utils.getSpringBean(OrderEngineBussiness.class);
			isFrist = false;
		}
	}

	public static void start(){
		canDispatch = true ;
	}

	public static void stop(){
		canDispatch = false ;
	}
}
