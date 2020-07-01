package com.github.wxiaoqi.security.app.buffer;

import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.out.BizFlowServiceBean;
import com.github.wxiaoqi.security.api.vo.order.out.OperateButton;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 配置缓存器
 * @author huangxl
 * @Description: 
 * @date 2016年6月14日
 * @versin V1.0
 */
public class ConfigureBuffer {
	private static Logger _log = LoggerFactory.getLogger(ConfigureBuffer.class);
	
	public static ConfigureBuffer instance;
	
	private static BizBusinessMapper bizBusinessMapper = null;
	private static BizFlowMapper bizFlowMapper = null;
	private static BizFlowProcessMapper bizFlowProcessMapper = null;
	private static BizFlowProcessOperateMapper bizFlowProcessOperateMapper = null;
	private static BizBusinessSkillsMapper bizBusinessSkillsMapper = null;
	private static BizFlowServiceMapper bizFlowServiceMapper = null;



	//流程创建操作缓存
	private static Map<String,BizFlowProcessOperate> flowCreateOperateMap = null;
	//工序按钮操作缓存(去除设置不显示的按钮)客户APP端
	private static Map<String,List<OperateButton>> clientOperateButtonMap = null;
	//工序按钮操作缓存(去除设置不显示的按钮)服务APP/PC端
	private static Map<String,List<OperateButton>> serviceOperateButtonMap = null;

	//业务缓存
	private static Map<String, BizBusiness> businessMap = null;
	//工单工序缓存
	private static Map<String, BizFlowProcess> flowProcessMap = null;
	//操作缓存
	private static Map<String, BizFlowProcessOperate> operateMap = null;
	//业务技能缓存
	private static Map<String, List<BizBusinessSkills>> busSkillsMap = null;
	//服务缓存
	private static Map<String,BizFlowService> bizFlowServiceMap = null;


	private ConfigureBuffer(){}
	
	/**
	 * 获取当前对象实例
	 */
	public static synchronized ConfigureBuffer getInstance() {
		if(instance==null){
			//初始化Dao
			initDao();
			//初始化数据
			init();
			
			instance = new ConfigureBuffer();
		}
		return instance;
	}

	//根据业务ID获取业务
	public void reload(){
		instance = null;
		getInstance();
	}

	/**
	 * 初始化数据
	 */
	private static void init(){
		//初始化
		getOperateMap();
		getFlowProcessMap();
		getBusinessMap();
		getFlowSkillsMap();

		//
		getClientOperateButtonMap();
		getServiceOperateButtonMap();

		//打印缓存信息
		printLogInfo();
	}
	
	private static void initDao(){
		if(bizFlowMapper == null){
			bizFlowMapper = (BizFlowMapper) BeanUtils.getBean(BizFlowMapper.class);
		}
		if(bizBusinessMapper == null){
			bizBusinessMapper = (BizBusinessMapper)BeanUtils.getBean(BizBusinessMapper.class);
		}
		if(bizFlowProcessMapper == null){
			bizFlowProcessMapper = (BizFlowProcessMapper)BeanUtils.getBean(BizFlowProcessMapper.class);
		}
		if(bizFlowProcessOperateMapper == null){
			bizFlowProcessOperateMapper = (BizFlowProcessOperateMapper)BeanUtils.getBean(BizFlowProcessOperateMapper.class);
		}
		if(bizBusinessSkillsMapper == null){
			bizBusinessSkillsMapper = (BizBusinessSkillsMapper)BeanUtils.getBean(BizBusinessSkillsMapper.class);
		}
		if(bizFlowServiceMapper == null){
			bizFlowServiceMapper = (BizFlowServiceMapper)BeanUtils.getBean(BizFlowServiceMapper.class);
		}

	}
	
	private static void printLogInfo() {
		if(flowProcessMap!=null){
			_log.warn("流程工序缓存key:"+flowProcessMap.keySet().toString());
		}else{
			_log.warn("流程工序缓存为空");
		}
		if(operateMap!=null){
			_log.warn("流程操作缓存key:"+operateMap.keySet().toString());
		}else{
			_log.warn("流程操作缓存为空");
		}
	}

	//服务缓存初始化
	private static Map<String,BizFlowService> getBizFlowServiceMap(){
		Map<String,BizFlowService> bizFlowServiceTempMap = null;
		List<BizFlowService> BizFlowServiceList = bizFlowServiceMapper.selectBizFlowServiceList(new HashMap<String,Object>());
		if(BizFlowServiceList!=null && BizFlowServiceList.size()>0){
			BizFlowService bizFlowService = null;
			bizFlowServiceTempMap = new HashMap<String, BizFlowService>();
			for (int i = 0; i < BizFlowServiceList.size(); i++) {
				bizFlowService = BizFlowServiceList.get(i);
				bizFlowServiceTempMap.put(bizFlowService.getId(), bizFlowService);
			}
		}
		if(bizFlowServiceMap==null){
			bizFlowServiceMap = bizFlowServiceTempMap;
		}
		return bizFlowServiceMap;
	}

	//业务缓存初始化
	private static Map<String,BizBusiness> getBusinessMap(){
		Map<String,BizBusiness> businessMapTemp = null;
		List<BizBusiness> bizBusinessList = bizBusinessMapper.selectAllBusinessList(new HashMap<String,Object>());
		if(bizBusinessList!=null && bizBusinessList.size()>0){
			BizBusiness bizBusiness = null;
			businessMapTemp = new HashMap<String, BizBusiness>();
			for (int i = 0; i < bizBusinessList.size(); i++) {
				bizBusiness = bizBusinessList.get(i);
				//key=工序ID
				businessMapTemp.put(bizBusiness.getId(), bizBusiness);
			}
		}
		if(businessMap==null){
			businessMap = businessMapTemp;
		}
		return businessMap;
	}

	//业务技能缓存初始化
	private static Map<String,List<BizBusinessSkills>> getFlowSkillsMap(){
		Map<String,List<BizBusinessSkills>> flowSkillsListMapTemp = null;
		List<BizBusinessSkills> busSkillsList = bizBusinessSkillsMapper.selectBusSkillsList(new HashMap<String,Object>());
		if(busSkillsList!=null && busSkillsList.size()>0){
			BizBusinessSkills bizBusinessSkills = null;
			flowSkillsListMapTemp = new HashMap<String, List<BizBusinessSkills>>();
			for (int i = 0; i < busSkillsList.size(); i++) {
				bizBusinessSkills = busSkillsList.get(i);
				List<BizBusinessSkills> skillList = flowSkillsListMapTemp.get(bizBusinessSkills.getBusId());
				if(skillList==null){
					skillList = new ArrayList<>();
				}
				skillList.add(bizBusinessSkills);
				flowSkillsListMapTemp.put(bizBusinessSkills.getBusId(), skillList);
			}
		}
		if(busSkillsMap==null){
			busSkillsMap = flowSkillsListMapTemp;
		}
		return busSkillsMap;
	}

	//订单工序缓存初始化
	private static Map<String,BizFlowProcess> getFlowProcessMap(){
		Map<String,BizFlowProcess> orderProcessTempMap = null;
		List<BizFlowProcess> orderProcessList = bizFlowProcessMapper.selectFlowProcessList(new HashMap<String,Object>());
		if(orderProcessList!=null && orderProcessList.size()>0){
			BizFlowProcess orderProcess = null;
			orderProcessTempMap = new HashMap<String, BizFlowProcess>();
			for (int i = 0; i < orderProcessList.size(); i++) {
				orderProcess = orderProcessList.get(i);
				//key=工序ID
				orderProcessTempMap.put(orderProcess.getId(), orderProcess);
			}
		}
		if(flowProcessMap==null){
			flowProcessMap = orderProcessTempMap;
		}
		return flowProcessMap;
	}

	//操作缓存初始化
	private static Map<String,BizFlowProcessOperate> getOperateMap(){
		Map<String,BizFlowProcessOperate> operateTempMap = null;
		Map<String,BizFlowProcessOperate> flowCreateOperateTempMap = null;

		List<BizFlowProcessOperate> operateList = bizFlowProcessOperateMapper.selectOperateList(new HashMap<String,Object>());
		if(operateList!=null && operateList.size()>0){
			BizFlowProcessOperate operate = null;
			operateTempMap = new LinkedHashMap<String, BizFlowProcessOperate>();
			flowCreateOperateTempMap = new LinkedHashMap<String, BizFlowProcessOperate>();
			for (int i = 0; i < operateList.size(); i++) {
				operate = operateList.get(i);

				//获取对应的服务
				operate.setBeforeServiceList(getBizFlowServiceListByIds(operate.getBeforeService()));
				operate.setCurrServiceList(getBizFlowServiceListByIds(operate.getCurrService()));
				operate.setAfterServiceList(getBizFlowServiceListByIds(operate.getAfterService()));
				operate.setExceptionServiceList(getBizFlowServiceListByIds(operate.getExceptionService()));

				//key=操作ID
				operateTempMap.put(operate.getId(), operate);
				//流程创建操作缓存
				if(OperateConstants.CreateOrderType.CREATE.toString().equals(operate.getCreateFlag())){
					flowCreateOperateTempMap.put(operate.getFlowId(),operate);
				}
			}
		}
		if(operateMap==null){
			operateMap = operateTempMap;
		}
		if(flowCreateOperateMap==null){
			flowCreateOperateMap = flowCreateOperateTempMap;
		}
		return operateMap;
	}

	//工序按钮操作缓存,去除设置不显示的按钮，获取app端显示按钮
	private static Map<String,List<OperateButton>> getClientOperateButtonMap(){
		clientOperateButtonMap = new HashMap<String, List<OperateButton>>();
		Iterator<String> iterator = operateMap.keySet().iterator();
		BizFlowProcessOperate operate = null;
		List<OperateButton> buttonList = null;
		OperateButton button = null;
		while (iterator.hasNext()) {
			String key = iterator.next();
			operate = operateMap.get(key);
			if(!StringUtils.isEmpty(operate.getShowFlag())
					&& (OperateConstants.ButtonShowFlag.ALLSHOW.toString().equals(operate.getShowFlag())
					|| OperateConstants.ButtonShowFlag.ClientSHOW.toString().equals(operate.getShowFlag()))){
				String processId = operate.getProcessId();
				buttonList = clientOperateButtonMap.get(processId);
				if(buttonList==null){
					buttonList = new ArrayList<OperateButton>();
					clientOperateButtonMap.put(processId, buttonList);
				}
				button = new OperateButton();
				button.setOperateId(operate.getId());
				button.setOperateName(operate.getOperateName());
				button.setButtonColour(operate.getButtonColour());
				button.setButtonType(operate.getButtonType());
				button.setOperateType(operate.getOperateType());
				List<BizFlowServiceBean> bizFlowServiceBeanList = new ArrayList<>();
				List<BizFlowService> bizFlowServiceList = operate.getBeforeServiceList();
				if(bizFlowServiceList!=null && bizFlowServiceList.size()>0){
					for(BizFlowService bizFlowService : bizFlowServiceList){
						BizFlowServiceBean bizFlowServiceBean= new BizFlowServiceBean();
						org.springframework.beans.BeanUtils.copyProperties(bizFlowService,bizFlowServiceBean);
						bizFlowServiceBeanList.add(bizFlowServiceBean);
					}
				}
				button.setBeforeServiceList(bizFlowServiceBeanList);
				buttonList.add(button);
			}
		}
		return clientOperateButtonMap;
	}
	
	//工序按钮操作缓存,去除设置不显示的按钮，获取PC端显示按钮
	private static Map<String,List<OperateButton>> getServiceOperateButtonMap(){
		serviceOperateButtonMap = new HashMap<String, List<OperateButton>>();
		Iterator<String> iterator = operateMap.keySet().iterator();
		BizFlowProcessOperate operate = null;
		List<OperateButton> buttonList = null;
		OperateButton button = null;
		while (iterator.hasNext()) {
			String key = iterator.next();
			operate = operateMap.get(key);
			if(!StringUtils.isEmpty(operate.getShowFlag())
					&& (OperateConstants.ButtonShowFlag.ALLSHOW.toString().equals(operate.getShowFlag())
					|| OperateConstants.ButtonShowFlag.ServiceSHOW.toString().equals(operate.getShowFlag()))){
				String processId = operate.getProcessId();
				buttonList = serviceOperateButtonMap.get(processId);
				if(buttonList==null){
					buttonList = new ArrayList<OperateButton>();
					serviceOperateButtonMap.put(processId, buttonList);
				}
				
				button = new OperateButton();
				button.setOperateId(operate.getId());
				button.setOperateName(operate.getOperateName());
				button.setButtonColour(operate.getButtonColour());
				button.setButtonType(operate.getButtonType());
				button.setOperateType(operate.getOperateType());
				List<BizFlowServiceBean> bizFlowServiceBeanList = new ArrayList<>();
				List<BizFlowService> bizFlowServiceList = operate.getBeforeServiceList();
				if(bizFlowServiceList!=null && bizFlowServiceList.size()>0){
					for(BizFlowService bizFlowService : bizFlowServiceList){
						BizFlowServiceBean bizFlowServiceBean= new BizFlowServiceBean();
						org.springframework.beans.BeanUtils.copyProperties(bizFlowService,bizFlowServiceBean);
						bizFlowServiceBeanList.add(bizFlowServiceBean);
					}
				}
				button.setBeforeServiceList(bizFlowServiceBeanList);
				buttonList.add(button);
			}
		}
		return serviceOperateButtonMap;
	}

	/**
	 * 根据服务ids查询服务
	 * @param serviceIds
	 * @return
	 */
	private static List<BizFlowService> getBizFlowServiceListByIds(String serviceIds) {
		if(bizFlowServiceMap==null){
			getBizFlowServiceMap();
		}

		List<BizFlowService> BizFlowServiceList = null;
		if(bizFlowServiceMap!=null){
			if(StringUtils.isNotEmpty(serviceIds)){
				String[] arr = serviceIds.split(",");
				if(arr!=null && arr.length>0){
					BizFlowServiceList = new ArrayList<BizFlowService>();
					for (int j = 0; j < arr.length; j++) {
						BizFlowService BizFlowService = bizFlowServiceMap.get(arr[j]);
						if(BizFlowService!=null){
							BizFlowServiceList.add(BizFlowService);
						}
					}
				}
			}
		}
		return BizFlowServiceList;
	}


	//根据操作ID获取操作
	public BizFlowProcessOperate getOperateById(String operateId){
		if(operateMap==null || operateMap.get(operateId)==null){
			getOperateMap();
		}
		if(operateMap==null){
			return null;
		}
		return operateMap.get(operateId);
	}

	//根据业务ID获取业务
	public BizBusiness getBusinessById(String busId){
		if(businessMap==null || businessMap.get(busId)==null){
			getBusinessMap();
		}
		if(businessMap==null){
			return null;
		}
		return businessMap.get(busId);
	}

	//根据业务ID获取业务所需技能
	public List<BizBusinessSkills> geBusSkillsByBusId(String busId){
		if(busSkillsMap==null || busSkillsMap.get(busId)==null){
			getFlowSkillsMap();
		}
		if(busSkillsMap==null){
			return null;
		}
		return busSkillsMap.get(busId);
	}

	/**
	 * 根据工序ID获取客户端APP操作按钮（）
	 * @param processId
	 * @return
	 */
	public List<OperateButton> getClientButtonByProcessId(String processId){
		return clientOperateButtonMap.get(processId);
	}

	/**
	 * 根据工序ID获取服务端APP操作按钮（）
	 * @param processId
	 * @return
	 */
	public List<OperateButton> getServiceButtonByProcessId(String processId){
		return serviceOperateButtonMap.get(processId);
	}

	/**
	 * 根据业务ID获取流程创建操作按钮（）
	 * @param busId
	 * @return
	 */
	public BizFlowProcessOperate getCreateOperateByBusId(String busId){
		if(businessMap==null || businessMap.get(busId)==null){
			getBusinessMap();
		}
		if(businessMap!=null){
			BizBusiness bizBusiness = businessMap.get(busId);
			if(bizBusiness!=null){
				if(flowCreateOperateMap==null || flowCreateOperateMap.size()==0){
					getOperateMap();
				}
				return flowCreateOperateMap.get(bizBusiness.getFlowId());
			}
		}
		return null;
	}

}
