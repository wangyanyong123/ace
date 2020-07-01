package com.github.wxiaoqi.security.app.handle;

import com.github.wxiaoqi.security.api.vo.buffer.CommServiceConstants;
import com.github.wxiaoqi.security.app.entity.BizFlowService;
import com.github.wxiaoqi.security.common.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 服务分发
 * @author huangxl
 * @Description: 
 * @date 2016年7月4日
 * @versin V1.0
 */
public class ServiceDispatch {

	/**
	 * 调用一组服务
	 * @param dubboServices
	 * @param paramMap
	 * @return
	 */
	public static LinkedHashMap<String,Object> callService(List<BizFlowService> dubboServices, Object paramMap) {
		LinkedHashMap<String,Object> resultMap = null;
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		if(dubboServices!=null && dubboServices.size()>0){
			resultMap = new LinkedHashMap<String,Object>();
			BizFlowService dubboService = null;
			Object resut = null;
			for (int i = 0; i < dubboServices.size(); i++) {
				dubboService = dubboServices.get(i);
				resut = callService(dubboService,paramMap);
				//不忽略结果且不返回为空
				if(CommServiceConstants.IgnoreResultFlag.NO.toString().equals(dubboService.getIgnoreResultFlag())
						&& resut!=null){
					resultMap.put(dubboService.getServiceCode(), resut);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 调用一个服务
	 * @param bizFlowService
	 * @param paramMap
	 * @return
	 */
	public static Object callService(BizFlowService bizFlowService, Object paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		if(bizFlowService!=null){
			if(CommServiceConstants.Connector.LOCAL.toString().equals(bizFlowService.getConnector())){
				return LocalServiceHolder.doService(bizFlowService,paramMap);
			}else if(CommServiceConstants.Connector.DUBBO.toString().equals(bizFlowService.getConnector())){
//				return DubboServiceHolder.doService(bizFlowService,paramMap);
			}
		}
		return null;
	}

}
