package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 个人积分规则配置表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@Table(name = "biz_integral_rule")
public class BizIntegralRule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //积分规则类型(数据字典中维护)(暂时不用)
    @Column(name = "rule_type")
    private String ruleType;
	
	    //积分规则编码(累积以A开始，消费以C开始，如C001）
    @Column(name = "rule_code")
    private String ruleCode;
	
	    //积分规则名称
    @Column(name = "rule_name")
    private String ruleName;
	
	    //积分规则说明
    @Column(name = "rule_desc")
    private String ruleDesc;
	
	    //积分值
    @Column(name = "credits_value")
    private Integer creditsValue;
	
	    //积分日上限(-1:不限制)
    @Column(name = "credits_upper_day")
    private Integer creditsUpperDay;
	
	    //积分月上限(-1:不限制)
    @Column(name = "credits_upper_month")
    private Integer creditsUpperMonth;
	
	    //积分总上限(-1:不限制)
    @Column(name = "credits_upper_total")
    private Integer creditsUpperTotal;
	
	    //启用状态(1-草稿，2-已启用，3-已停用)
    @Column(name = "rule_status")
    private String ruleStatus;
	
	    //适用维度(1-个人，2-小组)
    @Column(name = "type")
    private String type;
	
	    //生效时间(暂时不用)
    @Column(name = "eff_time")
    private Date effTime;
	
	    //失效时间(暂时不用)
    @Column(name = "lose_time")
    private Date loseTime;
	
	    //会员级别(暂时不用)
    @Column(name = "member_grade")
    private String memberGrade;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;
	

	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：积分规则类型(数据字典中维护)(暂时不用)
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	/**
	 * 获取：积分规则类型(数据字典中维护)(暂时不用)
	 */
	public String getRuleType() {
		return ruleType;
	}
	/**
	 * 设置：积分规则编码(累积以A开始，消费以C开始，如C001）
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	/**
	 * 获取：积分规则编码(累积以A开始，消费以C开始，如C001）
	 */
	public String getRuleCode() {
		return ruleCode;
	}
	/**
	 * 设置：积分规则名称
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	/**
	 * 获取：积分规则名称
	 */
	public String getRuleName() {
		return ruleName;
	}
	/**
	 * 设置：积分规则说明
	 */
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	/**
	 * 获取：积分规则说明
	 */
	public String getRuleDesc() {
		return ruleDesc;
	}
	/**
	 * 设置：积分值
	 */
	public void setCreditsValue(Integer creditsValue) {
		this.creditsValue = creditsValue;
	}
	/**
	 * 获取：积分值
	 */
	public Integer getCreditsValue() {
		return creditsValue;
	}
	/**
	 * 设置：积分日上限(-1:不限制)
	 */
	public void setCreditsUpperDay(Integer creditsUpperDay) {
		this.creditsUpperDay = creditsUpperDay;
	}
	/**
	 * 获取：积分日上限(-1:不限制)
	 */
	public Integer getCreditsUpperDay() {
		return creditsUpperDay;
	}
	/**
	 * 设置：积分月上限(-1:不限制)
	 */
	public void setCreditsUpperMonth(Integer creditsUpperMonth) {
		this.creditsUpperMonth = creditsUpperMonth;
	}
	/**
	 * 获取：积分月上限(-1:不限制)
	 */
	public Integer getCreditsUpperMonth() {
		return creditsUpperMonth;
	}
	/**
	 * 设置：积分总上限(-1:不限制)
	 */
	public void setCreditsUpperTotal(Integer creditsUpperTotal) {
		this.creditsUpperTotal = creditsUpperTotal;
	}
	/**
	 * 获取：积分总上限(-1:不限制)
	 */
	public Integer getCreditsUpperTotal() {
		return creditsUpperTotal;
	}
	/**
	 * 设置：启用状态(1-草稿，2-已启用，3-已停用)
	 */
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	/**
	 * 获取：启用状态(1-草稿，2-已启用，3-已停用)
	 */
	public String getRuleStatus() {
		return ruleStatus;
	}
	/**
	 * 设置：适用维度(1-个人，2-小组)
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：适用维度(1-个人，2-小组)
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：生效时间(暂时不用)
	 */
	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}
	/**
	 * 获取：生效时间(暂时不用)
	 */
	public Date getEffTime() {
		return effTime;
	}
	/**
	 * 设置：失效时间(暂时不用)
	 */
	public void setLoseTime(Date loseTime) {
		this.loseTime = loseTime;
	}
	/**
	 * 获取：失效时间(暂时不用)
	 */
	public Date getLoseTime() {
		return loseTime;
	}
	/**
	 * 设置：会员级别(暂时不用)
	 */
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	/**
	 * 获取：会员级别(暂时不用)
	 */
	public String getMemberGrade() {
		return memberGrade;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：时间戳
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：时间戳
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改人
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：修改日期
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改日期
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
}
