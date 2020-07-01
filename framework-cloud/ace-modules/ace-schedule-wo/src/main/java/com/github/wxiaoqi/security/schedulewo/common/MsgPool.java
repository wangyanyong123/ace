package com.github.wxiaoqi.security.schedulewo.common;

import java.util.concurrent.LinkedBlockingQueue;


/**
* @author xufeng 
* @Description: 消息池
* @date 2015-6-9 下午4:46:49 
* @version V1.0  
*
 */
public class MsgPool {
	
	//为分配工单池
    private static final LinkedBlockingQueue<MsgInfo> msgList = new LinkedBlockingQueue<MsgInfo>(Integer.MAX_VALUE);
    
    public static void add(MsgInfo msg) throws InterruptedException{
    	msgList.put(msg);
	}
	
	public static MsgInfo get() throws InterruptedException{
		return msgList.take();
	}
	
	public static int getWoSize(){
		return msgList.size();
	}
}
