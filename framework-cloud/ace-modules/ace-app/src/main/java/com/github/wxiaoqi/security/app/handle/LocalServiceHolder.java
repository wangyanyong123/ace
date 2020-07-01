package com.github.wxiaoqi.security.app.handle;

import com.github.wxiaoqi.security.api.vo.buffer.CommServiceConstants;
import com.github.wxiaoqi.security.app.buffer.ServiceException;
import com.github.wxiaoqi.security.app.entity.BizFlowService;
import com.github.wxiaoqi.security.common.util.BeanUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 本地服务
 * @author huangxl
 * @Description: 
 * @date 2016年6月14日
 * @versin V1.0
 */
@SuppressWarnings("rawtypes")
public class LocalServiceHolder {

	private static Map<String, Object> services = new HashMap<String, Object>();


	/**
	 * 添加服务
	 * 
	 * @param className
	 *            ：服务类名(带包名)
	 * @param className
	 *            ：引用远程服务对象
	 * @return
	 */
	private static boolean addService(String className) {
		Object object = BeanUtils.getBean(className);
		if (object == null) {
			return false;
		}
		services.put(className, object);
		return true;
	}

	private static Object getService(String className) {
		return services.get(className);
	}

	/**
	 * 获取执行结果
	 * 
	 * @param serviceName
	 *            ：服务类名
	 * @param methodName
	 *            ： 方法名
	 * @param params
	 *            ：参数
	 * @return 执行结果
	 */
	private static Object invokeMethod(String serviceName, String methodName, Map<String, Object> params) {
		Object object = LocalServiceHolder.getService(serviceName);
		if (object == null) {
			throw new ServiceException("找不到请求的服务[" + serviceName + "]，请联系管理员");
		}
		return Reflections.invokeMethodByName(object, methodName, params);
	}
	
	/**
	 * 调用服务
	 * @param bizFlowService
	 * @param accepts
	 * @return
	 */
	protected static Object doService(BizFlowService bizFlowService, Object accepts) {
		if (accepts == null) {
			accepts = new HashMap<String, Object>();
		}
		try {
			Object service = LocalServiceHolder.getService(bizFlowService.getBeanName());
			if(service==null){
				LocalServiceHolder.addService(bizFlowService.getBeanName());
			}
			@SuppressWarnings("unchecked")
			Object result = LocalServiceHolder.invokeMethod(bizFlowService.getBeanName(),bizFlowService.getMethodName() ,
					(Map)accepts);
			if(CommServiceConstants.IgnoreResultFlag.NO.toString().equals(bizFlowService.getIgnoreResultFlag())){
				return result;
			}else{
				return "";
			}
		} catch (Exception e) {
			e.getMessage();
			throw new ServiceException("接口错误"+e.getMessage());
		}
	}

}
