package com.github.wxiaoqi.security.common.constant;


import java.util.HashMap;

/**
 * 业务常量类
 * @author huangxl
 *
 */
public class MsgNoticeConstant {

	public static HashMap<String,String[]> msgNoticeMap = null;

	//投诉工单状态变化消息提醒
	public static final String  WO_CMPLAIN = "WO_CMPLAIN";
	//报修工单状态变化消息提醒
	public static final String  WO_REPAIR = "WO_REPAIR";

	public static final String HOUSE_AUDIT_P = "HOUSE_AUDIT_P";
	public static final String HOUSE_AUDIT_F = "HOUSE_AUDIT_F";

	public static final String COMMODITY_REFUND = "COMMODITY_REFUND";

	public static final String COMMODITY_REFUND_P = "COMMODITY_REFUND_P";

	public static final String COMMODITY_REFUND_F = "COMMODITY_REFUND_F";

	public static final String COMMODITY_REFUND_C = "COMMODITY_REFUND_C";

	public static final String MISSED_ORDER = "MISSED_ORDER";

	public static final String UN_CONFIREMED = "UN_CONFIREMED";

    //活动退款消息通知
	public static final String ACTIVITY_REFUND = "ACTIVITY_REFUND";

	public static final String ACTIVITY_REFUND_P = "ACTIVITY_REFUND_P";

	public static final String ACTIVITY_REFUND_F = "ACTIVITY_REFUND_F";

	//好友申请、审核

	public static final String FRIEND_APPLY = "FRIEND_APPLY";

	public static final String FRIEND_APPLY_S = "FRIEND_APPLY_S";

	public static final String FRIEND_APPLY_F = "FRIEND_APPLY_F";

	static {
		if(msgNoticeMap==null){
			msgNoticeMap = new HashMap<String,String[]>();
			/**
			 * 数组对应的字段如下：
			 * 1.业务类型（1-报修,2-住户审核,3-系统通知,4.投诉,5.订单,8.活动,9-好友申请 10-好友审核）
			 * 2.是否跳转(0-跳转，1-跳转)
			 * 3.手机通知跳转页
			 * 4.消息标题
			 * 5.消息内容
			 */
			//投诉工单状态变化消息提醒
			msgNoticeMap.put(WO_CMPLAIN,new String[]{"4","1","PN0001","您投诉的工单#(status)",""});
			//报修工单状态变化消息提醒
			msgNoticeMap.put(WO_REPAIR,new String[]{"1","1","PN0001","您报修的工单#(status)",""});

			msgNoticeMap.put(HOUSE_AUDIT_P,new String[]{"2","1","","通过","您申请成为#(roominfo)的#(identity)已经审核通过"});

			msgNoticeMap.put(HOUSE_AUDIT_F,new String[]{"2","1","","拒绝","您申请成为#(roominfo)的#(identity)被拒绝"});

			msgNoticeMap.put(FRIEND_APPLY,new String[]{"9","1","","您有新的好友申请","用户#{friend}申请成为您的好友，请及时处理。"});

			msgNoticeMap.put(FRIEND_APPLY_S,new String[]{"10","1","","通过","用户#{friend}已审核通过您的好友申请，#{mine}已成为您的好友啦。"});
			msgNoticeMap.put(FRIEND_APPLY_F,new String[]{"10","1","","拒绝","用户#{friend}已审核拒绝您的好友申请。"});


			msgNoticeMap.put(COMMODITY_REFUND,new String[]{"5","1","PN0002","退款正在审核中","您的退款正在审核中"});
			msgNoticeMap.put(COMMODITY_REFUND_P,new String[]{"5","1","PN0002","退款审核成功","您的退款审核成功"});
			msgNoticeMap.put(COMMODITY_REFUND_F,new String[]{"5","1","PN0002","退款审核被拒绝","您的退款审核被拒绝"});
			msgNoticeMap.put(COMMODITY_REFUND_C,new String[]{"5","1","PN0002","订单已被关闭，支付金额已退回您的账号","您的订单已被关闭，支付金额已退回您的账号"});

			msgNoticeMap.put(MISSED_ORDER,new String[]{"6","1","","系统自动取消订单","您的订单【#(title)】由于在#(time)分钟内未完成支付，已被系统自动取消"});
			msgNoticeMap.put(UN_CONFIREMED,new String[]{"7","1","","系统自动确认订单","您的订单【#(title)】由于在#(time)分钟内未确认完成，已被系统自动确认"});

			msgNoticeMap.put(ACTIVITY_REFUND,new String[]{"8","1","PN0003","退款正在审核中","您的退款正在审核中"});
			msgNoticeMap.put(ACTIVITY_REFUND_P,new String[]{"8","1","PN0003","退款审核成功","您的退款审核成功"});
			msgNoticeMap.put(ACTIVITY_REFUND_F,new String[]{"8","1","PN0003","退款审核被拒绝","您的退款审核被拒绝"});
		}
	}
}
