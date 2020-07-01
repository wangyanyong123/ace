package com.github.wxiaoqi.security.schedulewo.job;

import java.util.Calendar;
import java.util.List;

import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.schedulewo.biz.BizWoBiz;
import com.github.wxiaoqi.security.schedulewo.common.WODispatchPool;
import com.github.wxiaoqi.security.schedulewo.constant.DBConstant;
import com.github.wxiaoqi.security.schedulewo.vo.SrsWo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;


/**
 *
* @author xufeng
* @Description: 工单表扫描
* @date 2015-6-4 下午3:50:49
* @version V1.0
*
 */
@Service
@Slf4j
public class ScanningWODBJob implements Job{

	private static int size = 100 ;//一次受理工单条数
	private static BizWoBiz bizWoBiz;
	private static SrsWo wo;

	static{
		wo = new SrsWo();
		wo.setStatus(DBConstant.DataStatus.EFFECTIVE.toString()); //有效数据
		wo.setWoStatus(DBConstant.WoStatus.WSL.toString());//未受理状态的工单
		wo.setSize(size);//一次受理工单条数
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		init();
		log.debug("扫描待接工单....");
		wo.setPlanStartTime(Calendar.getInstance().getTime());
		List<SrsWo> woList = bizWoBiz.updateAndGetWOList(wo);
		if( woList != null && woList.size() > 0){
			for (SrsWo srsWO : woList) {
				try {
					srsWO.setTurnWoFlag(false);
					WODispatchPool.add(srsWO);
				} catch (InterruptedException e) {
					log.error("添加待调度工单异常!" , e);
				}
			}
		}
	}

	private void init(){
		if(bizWoBiz == null){
			bizWoBiz = BeanUtils.getBean(BizWoBiz.class);
		}
	}
}
