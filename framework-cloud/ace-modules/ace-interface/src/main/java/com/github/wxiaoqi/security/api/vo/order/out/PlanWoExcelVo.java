package com.github.wxiaoqi.security.api.vo.order.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PlanWoExcelVo implements Serializable {

	private static final long serialVersionUID = -5834017398182494323L;

	private String title;

	private String woCode;

	private String woStatus;

	private String description;

	private String handleBy;

	private String comeFrom;

	private String comeFromStr;

	private String createTime;

	private String receiveWoTime;

	private String startProcessTime;

	private String finishWoTime;

	private String houseNames;

	private String categoryCode;

	public String getWoStatusStr(){
		String woStatusStr = "";
		if("00".equals(woStatus)){
			woStatusStr = "待系统受理";
		}else if("01".equals(woStatus)){
			woStatusStr = "待接受";
		}else if("02".equals(woStatus)){
			woStatusStr = "已接受";
		}else if("03".equals(woStatus)){
			woStatusStr = "处理中";
		}else if("04".equals(woStatus)){
			woStatusStr = "暂停";
		}else if("05".equals(woStatus)){
			woStatusStr = "已完成";
		}else if("06".equals(woStatus)){
			woStatusStr = "已取消";
		}else if("07".equals(woStatus)){
			woStatusStr = "已取消";
		}
		return woStatusStr;
	}
	//1-回家APP、2-金小茂APP、3-CRM系统、4-WEB端
	public String getComeFromStr() {
		String comeFromStr = "";
		if ("1".equals(comeFrom)) {
			comeFromStr = "回家APP";
		} else if ("2".equals(comeFrom)) {
			comeFromStr = "金小茂APP";
		} else if ("3".equals(comeFrom)) {
			comeFromStr = "CRM系统";
		}else if("4".equals(comeFrom)){
			comeFromStr = "FM";
		}
		return comeFromStr;
	}

}
