package com.github.wxiaoqi.security.jinmao.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PlanWoListVo implements Serializable {

	private static final long serialVersionUID = 6342963316185272772L;

	private String title;

    private String woCode;

    private String woStatus;

    private String woStatusStr;

    private String description;

    private String handleBy;

    private String createTime;

    private String receiveWoTime;

    private String startProcessTime;

    private String finishWoTime;

    private String comeFrom;

    private String comeFromStr;

    private String houseNames;

    private String categoryCode;
}
