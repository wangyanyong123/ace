package com.github.wxiaoqi.security.schedulewo.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
* @author xufeng
* @Description: 全局调度
* @date 2015-6-5 下午4:16:36
* @version V1.0
*
 */
public class GlobalSchedule {

	private static Logger logger = LoggerFactory.getLogger(GlobalSchedule.class);
	private static Scheduler woDispatchScheduler;
	private static Map<String, JobKey> jokKeyMap = new ConcurrentHashMap<String, JobKey>();
	static{
		try {
			woDispatchScheduler =  StdSchedulerFactory.getDefaultScheduler();
			woDispatchScheduler.start();
		} catch (SchedulerException e) {
			logger.error("初始化工单调度异常!" , e );
		}
	}

	/**
	 * 工单派遣
	 * @param jobDetail
	 * @param trigger
	 * @return
	 */
	public static boolean scheduleDispatchWOJob(JobDetail jobDetail , Trigger trigger){
		boolean flag = false;
		try {
			woDispatchScheduler.scheduleJob(jobDetail, trigger);
			JobKey jobKey = jobDetail.getKey();
			jokKeyMap.put(jobKey.getName() + "-" + jobKey.getGroup(), jobKey);
			flag = true;
		} catch (SchedulerException e) {
			logger.error("调度异常!", e);
		}
		return flag;
	}

	/**
	 * 删除任务
	 * @param jobkey
	 * @return
	 */
	public static boolean deleteJob(JobKey jobkey){
		boolean flag = false;
		try {
			woDispatchScheduler.deleteJob(jobkey);
			flag = true;
		} catch (SchedulerException e) {
			logger.error("删除job异常!", e);
		}
		return flag;
	}

	/**
	 * 暂定任务
	 * @param jobkey
	 * @return
	 */
	public static boolean pauseJob(JobKey jobkey){
		boolean flag = false;
		try {
			woDispatchScheduler.pauseJob(jobkey);
			flag = true;
		} catch (SchedulerException e) {
			logger.error("暂停job异常!", e);
		}
		return flag;
	}

	/**
	 * 恢复任务
	 * @param jobkey
	 * @return
	 */
	public static boolean resumeJob(JobKey jobkey){
		boolean flag = false;
		try {
			woDispatchScheduler.resumeJob(jobkey);
			flag = true;
		} catch (SchedulerException e) {
			logger.error("恢复job异常!", e);
		}
		return flag;
	}

	/**
	 * 暂停调度中所有的job任务
	 * @return
	 */
	public static boolean pauseAll(){
		boolean flag = false;
		try {
			woDispatchScheduler.pauseAll();
			flag = true;
		} catch (SchedulerException e) {
			logger.error("恢复job异常!", e);
		}
		return flag;
	}

	/**
	 * 恢复调度中所有的job的任务
	 * @return
	 */
	public static boolean resumeAll(){
		boolean flag = false;
		try {
			woDispatchScheduler.resumeAll();
			flag = true;
		} catch (SchedulerException e) {
			logger.error("恢复job异常!", e);
		}
		return flag;
	}

	  /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     */
    public static void deleteJob(String jobName , String groupName) {
        try {
        	woDispatchScheduler.deleteJob(jokKeyMap.get(jobName + "-" + groupName));// 删除任务
        	jokKeyMap.remove(jobName + "-" + groupName);
        } catch (Exception e) {
			logger.error("删除job异常!", e);
        }
    }
}
