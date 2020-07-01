package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SrsWo implements Serializable {

	private static final long serialVersionUID = 4016520723859042657L;
	private String id;
	private String woCode;// 工单编码        
	private String woSetpId;//订阅工序   
	private String woSubject;//工单标题
	private String dealType;//处理类型（1：专人处理   2：抢单）        
	private String woStatus;//工单状态（0 未受理  1 已受理,2 已派出,3 已接受,4 已拒绝,5 处理中,6 暂停,7 已完成,8 已取消,9 已忽略）  
	private String description;// 描述     
	private String salaryType;// 计薪类型(个人、公司）  
	private String owner;//负责人
	private String status;// 状态  
	private String subId;// 订阅
	private String busStepId;// 业务工序 
	private Date planStartTime;//工单计划开始时间
	private String areaId;//区域id
	private String projectId;//项目ID
	private Date createTime;//创建时间
	private Date modifyTime;//更新时间
	private int size;//单次受理工单的条数
    private String subStepId;
    private String contactor;
    private String addr;
    private Date timeStamp;
    private String createBy;
    private String modifyBy;
    
	private double money;
	private String imgId;
	private int startNum = 0;
	private int endNum = 10;
	private String subscribeStatus;
	
	private String bussId ; 
	
	private String isNewCreate;
    private String processId;
    private String companyId;
    private String handleBy;
    private String acceptBy;
    
    //是否转单，ture:转单，false,普通
    private boolean turnWoFlag;
    
    private String subjectId;

    private String flowId;
	

}