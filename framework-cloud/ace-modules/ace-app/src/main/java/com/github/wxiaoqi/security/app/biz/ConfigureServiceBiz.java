package com.github.wxiaoqi.security.app.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.wxiaoqi.security.app.entity.BizFlowProcessOperate;
import com.github.wxiaoqi.security.app.entity.BizFlowService;
import com.github.wxiaoqi.security.app.handle.ServiceDispatch;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigureServiceBiz{

	/**
	 * 调用当前和操作后服务
	 * @param operate 操作
	 * @param paramMap 参数
//	 * @param comeParam 存储返回结果集
	 */
	public void doService(BizFlowProcessOperate operate , Map<String, Object> paramMap) {
		LinkedHashMap<String, Object> resultMapTemp = null;
		//1.当前操作
		List<BizFlowService> currServiceList = operate.getCurrServiceList();
		if(currServiceList!=null && currServiceList.size()>0){
			BizFlowService currService = currServiceList.get(0);
			Object result = ServiceDispatch.callService(currService, paramMap);
//			comeParam.setSelfServiceResult(result);
		}


		//2.操作后处理
		List<BizFlowService> afterServiceList = operate.getAfterServiceList();
		if(afterServiceList!=null && afterServiceList.size()>0){
			resultMapTemp = ServiceDispatch.callService(afterServiceList,paramMap);
			if(resultMapTemp!=null){
//				comeParam.setAfterServiceResult(resultMapTemp);
			}
		}
	}
	
	/**
	 * 包装参数列表
	 * @param paramList
	 * @return
	 */
	public Map<String, Object> packingParam(List<Object> paramList) {
		Map<String, Object> paramMap = null;
		if(paramList!=null && paramList.size()>0){
			paramMap = new HashMap<String, Object>();
			for (int i = 0; i < paramList.size(); i++) {
				Object object = paramList.get(i);
				if(object!=null){
					String className = object.getClass().getName();
					if("com.excegroup.rpc.RpcPassport".equals(className)){
						paramMap.put(className, object);
					}else if("java.lang.String".equals(className)){
						paramMap.put(className, object);
					}else if("java.util.HashMap".equals(className) || "java.util.Map".equals(className)
							|| "org.springframework.validation.support.BindingAwareModelMap".equals(className)){
						paramMap.put("java.util.Map", object);
					}
				}
			}
		}
		
		return paramMap;
	}
	

	/**
	 * 工/订单引擎处理操作前服务
	 * @param beforeServiceList
	 * @param paramList
	 * @return
	 */
	public ObjectRestResponse doBeforeService(List<BizFlowService> beforeServiceList, List<Object> paramList) {
		LinkedHashMap<String, Object> resultMapTemp = null;
		ObjectRestResponse resultMsg = new ObjectRestResponse();
		if(beforeServiceList!=null && beforeServiceList.size()>0){
			resultMapTemp = ServiceDispatch.callService(beforeServiceList,packingParam(paramList));
			if(resultMapTemp!=null){
				Iterator<String> iterator = resultMapTemp.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					ObjectRestResponse resultTemp = resultMapTemp.get(key)==null ? null : (ObjectRestResponse)resultMapTemp.get(key);
					if(resultTemp==null || resultTemp.getStatus()!=200){
						return resultTemp;
					}
				}
			}else{
				resultMsg.setStatus(101);
				resultMsg.setMessage("工单引擎操作前处理失败");
				return resultMsg;
			}
		}
		return resultMsg;
	}

}
