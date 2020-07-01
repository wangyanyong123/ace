package com.github.wxiaoqi.security.api.vo.buffer;


/**
 * 服务表通用常数
 * @author huangxl
 * @Description: 
 * @date 2016年8月19日
 * @versin V1.0
 */
public class CommServiceConstants {
	
	
	/**
	 * 是否忽略结果返回(1-否，2-是)
	 * @author Administrator
	 *
	 */
	public enum IgnoreResultFlag {
		// 利用构造函数传参
	    YES("2"),NO("1");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private IgnoreResultFlag(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
	/**
	 * 按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
	 * @author Administrator
	 *
	 */
	public enum ButtonShowFlag {
		// 利用构造函数传参
	    ALL("0"),PART ("1");

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
	 * 按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
	 * @author Administrator
	 *
	 */
	public enum RollingNewsType {
		// 利用构造函数传参
	    PersonalService("1"),EnterpriseService ("2");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private RollingNewsType(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}

	/**
	 * 连接器，支持本地和DUBBO(LOCAL、DUBBO)
	 * LOCAL本地连接,DUBBO连接
	 * @author Administrator
	 *
	 */
	public enum Connector {
		// 利用构造函数传参
		LOCAL("LOCAL"),DUBBO("DUBBO");

		// 定义私有变量
		private String nCode;

		// 构造函数，枚举类型只能为私有
		private Connector(String _nCode) {
			this.nCode = _nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}
	
}
