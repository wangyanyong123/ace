package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

/**
 * 
* @author huangxl
* @Description: 调度规则
* @date 2018-12-05 10:24:31
* @version V1.0  
*
 */
@Data
public class BusDispatchRule {

	private String crontab;//cron表达式  调度
	private int minutes;//执行间隔时间（单位：分钟）
	private int dispatchTime;//匹配次数 调度

	public BusDispatchRule() {
		super();
	}

	public BusDispatchRule(String crontab,int minutes,int dispatchTime) {
			this.crontab = crontab;
			this.minutes = minutes;
			this.dispatchTime = dispatchTime;
	}
}
