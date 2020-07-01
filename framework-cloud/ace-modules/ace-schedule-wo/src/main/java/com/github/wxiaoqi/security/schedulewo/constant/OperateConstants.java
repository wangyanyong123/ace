
package com.github.wxiaoqi.security.schedulewo.constant;

import org.apache.commons.lang.StringUtils;

/**
 * 操作表通用常数
 * @author huangxl
 * @Description: 
 * @date 2016年7月7日
 * @versin V1.0
 */
public class OperateConstants {
	
	//订单主题过滤业务名称
	public static final String FILTER_BUSNAME_TO_SUBJECT = "FILTER_BUSNAME_TO_SUBJECT";
	
	/**
	 * 是否记账
	 * 0未记账,1已记账
	 * @author Administrator
	 *
	 */
	public enum AccountStatus {
		// 利用构造函数传参
	    NOACCOUNT("0"),ACCOUNT("1");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private AccountStatus(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 是否记录交易流水(0-不记录、1-记录)
	 * @author Administrator
	 *
	 */
	public enum RecordTranslog {
		// 利用构造函数传参
	    NO("0"),YES("1");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private RecordTranslog(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 创建工订单标志
	 * 1-不创建 、2-创建
	 */
	public enum CreateOrderType {
		// 利用构造函数传参
		NOTCREATE("1"), CREATE("2");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private CreateOrderType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}

	/**
	 * 操作类型(1-普通操作、2-支付回调、3-退款回调、4-发起退款流程、5-下单未支付的取消、6-退保证金、
	 * 8-工单受理、9-工单抢单、10-工单完成、11-工单关闭、12-订单确认、13-评价、14-团购成团、15-拒绝退款)
	 */
	public enum OperateType {
		// 利用构造函数传参
		COMMON("1"), PAYCALLBACK("2"), REFUNDCALLBACK("3"), REFUND("4"), CANCEL("5"), REFUNDBOND("6"),
		WOHANDLE ("8"), WOGRAB("9"), WOFINISH("10") ,WOCLOSE ("11"),REVIEW("12"),EVALUATE("13"),FINISHGROUP("14"),REFUSEREFUND("15");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private OperateType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 按钮颜色(0-默认，1-红色)
	 */
	public enum ButtonColour {
		// 利用构造函数传参
		DEFAULT("0"), RED("1");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ButtonColour(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 按钮类型(01-调接口、02-app支付页面、03-评价页面、04-app退货/售后页面、05-取消、06-工单关闭、07-工单完成、
	 * 08-物业管理确认放行、09-直邮配送、10-工单暂停、11-pc端接单、12-pc端转单)
	 */
	public enum ButtonType {
		// 利用构造函数传参
		DEFAULT("01"), APPPAY("02"), COMMENT("03"), CUSTOMERSERVICE ("04"), CANCLE("05"),WOCLOSE ("06"),WOFINISH("07"),WORELEASE("08"),SENDDELIVERY("09"),WOSUSPEND ("10"),PCWOACCEPT ("11"),PCWOTRUN ("12");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ButtonType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 按钮显示范围(1-PC和app端都显示,0-PC和app端都不显示，2-PC端不显示app端显示，3-PC端显示app端不显示)
	 */
	public enum ButtonShowFlag {
		// 利用构造函数传参
		ALLHIDE("0"), ALLSHOW("1"), APPSHOW("2"), PCSHOW("3");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ButtonShowFlag(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 是否直邮标志(0-不直邮、1-直邮)
	 */
	public enum DeliveryFlag {
		// 利用构造函数传参
		NO("0"), YES("1");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private DeliveryFlag(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 停车缴费订单类型（1-正常、2-超时15分钟内、3-超时15分钟后）
	 */
	public enum ParkingOrderType {
		// 利用构造函数传参
		NORMAL("1"), INFIFTEEN("2"), OUTFIFTEEN("3");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ParkingOrderType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 是否自营配送标识（1：自营配送 2：第三方配送）
	 */
	public enum DistributionFlag {
		// 利用构造函数传参
		SELFSUPPORT("1"), OTHERSUPPORT("2");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private DistributionFlag(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}

	/**
	 *  导入类型
	 */
	public enum ImportType {
		// 利用构造函数传参
		ALL("全量"), ADD("增量"),MODIFY("修改"),  MERGE("被合并"),  MERGEED("合并后"),SPLIT("被拆分"),SPLITED("拆分后"),
		//后续增加
		DELETE("删除"),BATMODIFY("批量修改");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ImportType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
		
		public static ImportType getByValue(String value){
		    for(ImportType sexEnum : ImportType.values()){
		      if(StringUtils.equalsIgnoreCase(value, sexEnum.toString())){
		        return sexEnum;
		      }
		    }
		    return null;
		  }
		
	}

	/**
	 *  导入业务(pub_land-宗地、pub_building-建筑物、pub_space-空间、pub_facilities-设施)
	 *  m2_pro_building, m2_pro_building_group, m2_pro_enclosed, m2_pro_parking, m2_pro_parking_lot, m2_pro_project, m2_pro_space_relations, m2_pro_unit, m2_pro_workplane
	 */
	public enum ImportBus {
		// 利用构造函数传参
		Land("Land"), building("Building"), space("Space"), facilities("Facilities"),
		/** 以下为管理 实体 */
		ProEnclosed("ProEnclosed"), // 安全区域
		ProProject("ProProject"), // 项目
		ProBuildingGroup("ProBuildingGroup"), // 组团
		
		ProBuilding("ProBuilding"), // 楼栋
		ProParkingLot("ProParkingLot"), // 停车场
		ProParking("ProParking"), // 停车位
		ProUnit("ProUnit"), // 房屋
		ProWorkplane("ProWorkplane"),// 作业面
		ProEquipment("ProEquipment"), // 设备
		;

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ImportBus(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}

		public static ImportBus getByValue(String value) {
			for (ImportBus sexEnum : ImportBus.values()) {
				if (StringUtils.equalsIgnoreCase(value, sexEnum.toString())) {
					return sexEnum;
				}
			}
			return null;
		}
	}
	
	public enum SolrType{
		PUB_QUERY("PUB"), // 安全区域
		PRO_QUERY("PRO"); // 项目
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private SolrType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	public enum WechatType{
		PAY("pay"), // 安全区域
		REFUND("Refund"); // 项目
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private WechatType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	public enum QueryType{
		PAYID("payId"), // 安全区域
		SUBCODE("subCode"); // 项目
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private QueryType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 接口调用类型
	 * 1表示新增，2表示修改
	 * @author zhongziyang 20171023
	 *
	 */
	public enum InvokeType{
		ADD("1"), // 新增
		UPDATE("2"); // 修改
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private InvokeType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 是否
	 * @author zhongziyang 20171102
	 *
	 */
	public enum Whether{
		NO("0"), // 否
		YES("1"); // 是
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private Whether(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 请求方式：post=POST请求, get=GET请求
	 * */
	public enum RequestType{
		// 利用构造函数传参
		GET("get"),POST("post");
		
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private RequestType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 收费方式：1=现金，2=代币，3=现金/代币
	 * */
	public enum ChargeType{
		// 利用构造函数传参
		CASH("1"),COIN("2"),CASHORCOIN("3");
		
		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ChargeType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	
	
}
