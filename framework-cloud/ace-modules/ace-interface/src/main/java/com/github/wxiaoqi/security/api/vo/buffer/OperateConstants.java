
package com.github.wxiaoqi.security.api.vo.buffer;

/**
 * 操作表通用常数
 * @author huangxl
 * @Description:
 * @date 2016年7月7日
 * @versin V1.0
 */
public class OperateConstants {


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
	 * 8-工单受理、9-工单抢单、10-工单完成、11-工单关闭、12-订单确认、13-评价、14-团购成团、15-拒绝退款、18-工单暂停、19-工单恢复、
	 * 20-到岗、21-已收货的申请售后、22-填写快递单、23-等待签收快递、24-未收货的申请售后、25-签收后退款中)
	 */
	public enum OperateType {
		// 利用构造函数传参
		COMMON("1"), PAYCALLBACK("2"), REFUNDCALLBACK("3"), REFUND("4"), CANCEL("5"), REFUNDBOND("6"),
		WOHANDLE ("8"), WOGRAB("9"), WOFINISH("10") ,WOCLOSE ("11"),REVIEW("12"),EVALUATE("13"),FINISHGROUP("14"),REFUSEREFUND("15"),
		STOP("18"),RESTART("19"),ARRIVE_ON_TIME("20"),APPLY_AFTER("21"),OUT_EXPRESS("22"),DEAL_EXPRESS("23"),UN_APPLY_AFTER("24"),
		SIGN_REFUNDBOND("25");
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
	 * 按钮显示范围(1-客户端显示,2-服务端显示，3-客户端和服务端都不显示，4-客户端和服务端都显示)
	 */
	public enum ButtonShowFlag {
		// 利用构造函数传参
		ClientSHOW("1"), ServiceSHOW("2"),ALLHIDE("3"), ALLSHOW("4");

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
	 * 客户端类型
	 * 1-客户端APP 、2-服务端APP、3-WEB后台
	 */
	public enum ClientType {
		// 利用构造函数传参
		CLIENT_APP("1"), SERVER_APP("2") , SERVER_WEB("3");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private ClientType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}


}
