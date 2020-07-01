package com.github.wxiaoqi.security.schedulewo.constant;

/**
 * 
* @author xufeng 
* @Description: 数据库字段值常量类
* @date 2015-6-4 上午11:26:34 
* @version V1.0  
*
 */
public final class DBConstant {
	
	
	/**
	* @Description://数据状态 0.无效 1.有效
	* @date 2015-6-5 下午4:38:32 
	* @version V1.0  
	 */
	public enum DataStatus{
		INVALID("0"),EFFECTIVE("1");
		private final String va;
		private DataStatus(String value){
			va = value;
		}
		@Override
		public String toString() {
			return va;
		}
	}
	
	/**
	* @Description: //工单状态（00 未受理  01 已受理,02 已派出,03 已接受,04 处理中,05 暂停,06 已完成,07 已取消，08 已评论）  
	* @date 2015-6-5 下午4:38:09 
	* @version V1.0  
	 */
	public  enum WoStatus{
		WSL("00"),YSL("01"),YPC("02"),YJS("03"),CLZ("04"),ZT("05"),YWC("06"),YQX("07"),YPL("08");
		private final String va;
		private WoStatus(String value){
			va = value;
		}
		@Override
		public String toString() {
			return va;
		}
		
	}
	
	/**
	* @Description: dealType;//处理类型处理类型（1：派单   2：抢单  3.人工处理 4.接口处理 5.指定服务资源）
	* @date 2015-6-5 下午4:35:29 
	* @version V1.0  
	 */
	public enum WoDealType{
		LEAFLET("1"),GRAB("2"),MANUALHANDLING("3"),INTERFACE("4"),APPOINT("5");
		private final String va;
		private WoDealType(String value){
			va = value;
		}
		@Override
		public String toString() {
			return va;
		}
		
	}
	
	/**
	 * 
	* @author xufeng 
	* @Description: 二维码类型:1.临时,2.正式,3.访客
	* @date 2015-7-29 上午11:23:17 
	* @version V1.0  
	*
	 */
	public enum QrType{
		temporary("1"),formal("2"),visitor("3");
		private final String va;
		private QrType(String value){
			va = value;
		}
		public boolean equals(String type){
			if(va.equals(type)){
				return true;
			}
			return false;
		}
		@Override
		public String toString() {
			return va;
		}
	}
}
