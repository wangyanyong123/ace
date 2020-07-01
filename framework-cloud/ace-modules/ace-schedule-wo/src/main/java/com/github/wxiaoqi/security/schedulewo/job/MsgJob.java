package com.github.wxiaoqi.security.schedulewo.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.wxiaoqi.security.schedulewo.common.MsgInfo;
import com.github.wxiaoqi.security.schedulewo.common.MsgPool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
* @author xufeng
* @Description: 消息
* @date 2015-6-9 下午4:54:40
* @version V1.0
*
 */
public class MsgJob implements Job{

	private final Logger logger = LoggerFactory.getLogger(MsgJob.class);
//	private MobilePushService mobilePushService;
	private static boolean canSend = true;
	private ExecutorService executor = Executors.newFixedThreadPool(2);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("发送消息.....");
		init();
		try {
			MsgInfo msg = MsgPool.get();
			while(canSend){
				push(msg);
				msg = MsgPool.get();
			}
		} catch (InterruptedException e) {
			logger.error("发送消息调度异常!" , e);
		}

	}

	private void push(MsgInfo msgInfo){
		final MsgInfo m = msgInfo;
		executor.execute(
				new Runnable() {
					@Override
					public void run() {
//						logger.info(JacksonJsonUtil.beanToJsonTryExc(m));
//						mobilePushService.push( m);
					}
				}
			);
	}

	private void init(){

		//mobilePushService = new MobilePushServiceImpl();
	}
}
