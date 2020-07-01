package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 
 * 
 * @author huangxl
 * @Date 2019-01-09 17:22:54
 */
@Setter
@Getter
public class BizFlowServiceBean implements Serializable {

	private static final long serialVersionUID = -688729451750647887L;
	//服务ID
	@ApiModelProperty(value = "操作ID")
    private String id;
	
	    //服务编码
		@ApiModelProperty(value = "操作ID")
    private String serviceCode;
	
	    //服务名称
		@ApiModelProperty(value = "操作ID")
    private String serviceName;
	
	    //服务接口类
		@ApiModelProperty(value = "操作ID")
    private String interfaceName;
	
	    //服务版本
		@ApiModelProperty(value = "操作ID")
    private String version;
	
	    //目标方法
		@ApiModelProperty(value = "操作ID")
    private String methodName;
	
	    //是否异步处理(1-否，2-是)
		@ApiModelProperty(value = "操作ID")
    private String asynFlag;
	
	    //是否忽略结果返回(1-否，2-是)
		@ApiModelProperty(value = "操作ID")
    private String ignoreResultFlag;
	
	    //是否事务处理(1-否，2-是)
		@ApiModelProperty(value = "操作ID")
    private String transactionFlag;
	
	    //说明
		@ApiModelProperty(value = "操作ID")
    private String description;
	

	    //spring加载类名
		@ApiModelProperty(value = "操作ID")
    private String beanName;
	
	    //使用连接器，支持本地和DUBBO(LOCAL、DUBBO)
		@ApiModelProperty(value = "操作ID")
    private String connector;
	
	    //按钮是否显示（0-无论返回结果如何都显示按钮，1-根据返回结果来决定是否显示按钮）
		@ApiModelProperty(value = "操作ID")
    private String buttonShowFlag;
	
}
