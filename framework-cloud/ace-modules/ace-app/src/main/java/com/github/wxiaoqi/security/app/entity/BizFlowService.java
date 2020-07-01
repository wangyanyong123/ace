package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author huangxl
 * @Date 2019-01-09 17:22:54
 */
@Table(name = "biz_flow_service")
public class BizFlowService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //服务ID
    @Id
    private String id;
	
	    //服务编码
    @Column(name = "service_Code")
    private String serviceCode;
	
	    //服务名称
    @Column(name = "service_Name")
    private String serviceName;
	
	    //服务接口类
    @Column(name = "interface_name")
    private String interfaceName;
	
	    //服务版本
    @Column(name = "version")
    private String version;
	
	    //目标方法
    @Column(name = "method_name")
    private String methodName;
	
	    //是否异步处理(1-否，2-是)
    @Column(name = "asyn_flag")
    private String asynFlag;
	
	    //是否忽略结果返回(1-否，2-是)
    @Column(name = "ignore_result_flag")
    private String ignoreResultFlag;
	
	    //是否事务处理(1-否，2-是)
    @Column(name = "transaction_flag")
    private String transactionFlag;
	
	    //说明
    @Column(name = "description")
    private String description;
	
	    //状态(0-删除、1-正常)
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
	
	    //spring加载类名
    @Column(name = "bean_Name")
    private String beanName;
	
	    //使用连接器，支持本地和DUBBO(LOCAL、DUBBO)
    @Column(name = "connector")
    private String connector;
	
	    //按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
    @Column(name = "button_show_flag")
    private String buttonShowFlag;
	

	/**
	 * 设置：服务ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：服务ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：服务编码
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * 获取：服务编码
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * 设置：服务名称
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * 获取：服务名称
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * 设置：服务接口类
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	/**
	 * 获取：服务接口类
	 */
	public String getInterfaceName() {
		return interfaceName;
	}
	/**
	 * 设置：服务版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：服务版本
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：目标方法
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：目标方法
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：是否异步处理(1-否，2-是)
	 */
	public void setAsynFlag(String asynFlag) {
		this.asynFlag = asynFlag;
	}
	/**
	 * 获取：是否异步处理(1-否，2-是)
	 */
	public String getAsynFlag() {
		return asynFlag;
	}
	/**
	 * 设置：是否忽略结果返回(1-否，2-是)
	 */
	public void setIgnoreResultFlag(String ignoreResultFlag) {
		this.ignoreResultFlag = ignoreResultFlag;
	}
	/**
	 * 获取：是否忽略结果返回(1-否，2-是)
	 */
	public String getIgnoreResultFlag() {
		return ignoreResultFlag;
	}
	/**
	 * 设置：是否事务处理(1-否，2-是)
	 */
	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}
	/**
	 * 获取：是否事务处理(1-否，2-是)
	 */
	public String getTransactionFlag() {
		return transactionFlag;
	}
	/**
	 * 设置：说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：说明
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：状态(0-删除、1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除、1-正常)
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
	/**
	 * 设置：spring加载类名
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	/**
	 * 获取：spring加载类名
	 */
	public String getBeanName() {
		return beanName;
	}
	/**
	 * 设置：使用连接器，支持本地和DUBBO(LOCAL、DUBBO)
	 */
	public void setConnector(String connector) {
		this.connector = connector;
	}
	/**
	 * 获取：使用连接器，支持本地和DUBBO(LOCAL、DUBBO)
	 */
	public String getConnector() {
		return connector;
	}
	/**
	 * 设置：按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
	 */
	public void setButtonShowFlag(String buttonShowFlag) {
		this.buttonShowFlag = buttonShowFlag;
	}
	/**
	 * 获取：按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
	 */
	public String getButtonShowFlag() {
		return buttonShowFlag;
	}
}
