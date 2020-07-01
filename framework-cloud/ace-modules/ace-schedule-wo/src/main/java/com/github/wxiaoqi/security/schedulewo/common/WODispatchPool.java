package com.github.wxiaoqi.security.schedulewo.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.wxiaoqi.security.schedulewo.vo.SrsWo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
* @author xufeng
* @Description: 工单池
* @date 2015-6-5 上午11:49:10
* @version V1.0
*
 */
public final class WODispatchPool {

	private static Logger logger = LoggerFactory.getLogger(WODispatchPool.class);
	//未分配工单池
    private static final LinkedBlockingQueue<SrsWo> unDispatchWolist = new LinkedBlockingQueue<SrsWo>(Integer.MAX_VALUE);
    //不需要调度的工单
    private static final Map<String, Object> invalidWoMap = new ConcurrentHashMap<String, Object>();

	public static void add(SrsWo wo) throws InterruptedException{
		unDispatchWolist.put(wo);
	}

	public static void remove(String woId){
		invalidWoMap.put(woId, null);
	}

	public static SrsWo get() throws InterruptedException{
		SrsWo wo = unDispatchWolist.take();

		while(invalidWoMap.get(wo.getId()) != null){
			invalidWoMap.remove(wo.getId());
			wo = unDispatchWolist.take();
		}
		logger.debug("获得工单数据................");
		return wo;
	}

	public static int getWoSize(){
		return unDispatchWolist.size();
	}

}
