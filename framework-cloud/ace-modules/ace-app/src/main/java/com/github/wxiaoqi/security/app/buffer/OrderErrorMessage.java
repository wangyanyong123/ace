package com.github.wxiaoqi.security.app.buffer;

import java.util.HashMap;
import java.util.Map;


/**
 * 订单错误信息
 * @author huangxl
 * @Description: 
 * @date 2016年8月19日
 * @versin V1.0
 */
public class OrderErrorMessage {
	
	
	public static Map<String, String> obj = null;
	
	static{
		obj = new HashMap<String, String>();
		
		/**
		 * 工单订单错误编码信息
		 */
		obj.put("ow000", "调用成功");
		obj.put("ow001", "您的网络似乎有问题，请稍后重试!");
		obj.put("ow002", "订阅ID不存在!");
		obj.put("ow003", "工单ID不存在!");
		obj.put("ow004", "当前订阅状态已过期!");
		obj.put("ow005", "当前工单状态已过期!");
		obj.put("ow006", "该业务不存在下单操作!");
		obj.put("ow007", "不存在该节点操作!");
		obj.put("ow008", "工单对应的订阅ID不存在!");
		obj.put("ow009", "创建工单信息失败!");
		obj.put("ow010", "不存在该订单节点!");
		obj.put("ow011", "不存在该工单节点!");
		obj.put("ow012", "数据不存在!");
		
		/**
		 * 淋浴间业务错误编码信息
		 */
		obj.put("ework002", "打扫时间未到,请稍后处理!");
	}
	
}
