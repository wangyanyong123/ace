package com.github.wxiaoqi.security.app.biz;


import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.dict.DictThreeVal;
import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.api.vo.order.in.SearchSubIncidentInWeb;
import com.github.wxiaoqi.security.api.vo.order.in.SearchWoSaloon;
import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteInfo;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.in.WoInVo;
import com.github.wxiaoqi.security.app.vo.plan.*;
import com.github.wxiaoqi.security.app.vo.plan.in.PlanWoParam;
import com.github.wxiaoqi.security.app.vo.plan.in.PlanWoParamVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工单
 *
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Service
@Slf4j
public class BizWoBiz extends BusinessBiz<BizWoMapper,BizWo> {

    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private BaseAppServerUserBiz appServerUserBiz;
    @Autowired
    private BaseAppServerUserMapper appServerUserMapper;
    @Autowired
    private BizWoSkillsMapper bizWoSkillsMapper;
    @Autowired
    private BizCrmHouseMapper bizCrmHouseMapper;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private ToolFegin toolFegin;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private BaseAppClientUserMapper baseAppClientUserMapper;

    @Autowired
	private BizPlanWoRelationMapper planWoRelationMapper;
    @Autowired
	private BizPlanWoPmpMapper planWoPmpMapper;
    @Autowired
	private BizPlanWoPmpsMapper planWoPmpsMapper;
    @Autowired
	private BizPlanWoEqMapper planWoEqMapper;
    @Autowired
	private BizPlanWoPmpsOpMapper planWoPmpsOpMapper;
    @Autowired
    private BizDecoreteApplyMapper bizDecoreteApplyMapper;

    public ObjectRestResponse createWoOrder(WoInVo woInVo) throws Exception {
        return createWoOrder(woInVo,null);
    }

    public ObjectRestResponse createWoOrder(WoInVo woInVo, CreateWoInVo createWoInVo) throws Exception {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        Map<String,String> woMap = new HashMap<>();
        String userId = BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID();
        BizFlowProcessOperate operate = ConfigureBuffer.getInstance().getCreateOperateByBusId(woInVo.getBusId());
        BizBusiness bizBusiness = ConfigureBuffer.getInstance().getBusinessById(woInVo.getBusId());
        UserVo userVo = baseAppClientUserBiz.getUserNameById(userId,"c");

        //1.生成工单
        BizWo bizWo = new BizWo();
        String woId = UUIDUtils.generateUuid();
        BeanUtils.copyProperties(woInVo, bizWo);
        bizWo.setId(woId);
//        String woCode = "W" + DateTimeUtil.shortDateString();
//        ObjectRestResponse objectRestResponse = codeFeign.getCode("WorkOrder", woCode, "6", "0");
//        log.info("生成工单编码处理结果："+objectRestResponse.toString());
//        if(objectRestResponse.getStatus()==200){
//            bizWo.setWoCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
//        }

        bizWo.setTitle(bizBusiness.getBusName()+"("+bizWo.getThreeCategoryName()+")");

        if(StringUtils.isNotEmpty(woInVo.getContactName()) || StringUtils.isNotEmpty(woInVo.getContactTel())){
            //服务端代客报修
            UserVo userVoTemp = baseAppClientUserMapper.getUserNameByTelOrName(woInVo.getContactTel(),woInVo.getContactName());
            if(userVoTemp!=null){
                bizWo.setContactUserId(userVoTemp.getId());
                bizWo.setContactName(userVoTemp.getName());
                bizWo.setContactTel(userVoTemp.getMobilePhone());
            }else{
                bizWo.setContactUserId("");
                bizWo.setContactName(woInVo.getContactName());
                bizWo.setContactTel(woInVo.getContactTel());
            }
        }else{
            bizWo.setContactUserId(userId);
            bizWo.setContactName(userVo.getName());
            bizWo.setContactTel(userVo.getMobilePhone());
        }
        bizWo.setPublishUserId(userId);
        bizWo.setPublishName(userVo.getName());
        bizWo.setPublishTel(userVo.getMobilePhone());
        //设置项目信息
        HouseAllInfoVo houseAllInfoVo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(woInVo.getRoomId());
        if(houseAllInfoVo!=null){
            bizWo.setProjectId(houseAllInfoVo.getProjectId());
            bizWo.setLandId(houseAllInfoVo.getBlockId());
            bizWo.setBuildId(houseAllInfoVo.getBuildId());
            bizWo.setUnitId(houseAllInfoVo.getUnitId());
            bizWo.setAddr(houseAllInfoVo.getHouseName());
            bizWo.setRoomId(houseAllInfoVo.getHouseId());
            bizWo.setCrmProjectCode(houseAllInfoVo.getCrmProjectCode());
            bizWo.setCrmRoomCode(houseAllInfoVo.getCrmHouseCode());
        }else{
            List<String> projectIds = new ArrayList<>();
            projectIds.add(woInVo.getProjectId());
            List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
            if(bizCrmProjectBizList!=null && bizCrmProjectBizList.size()>0){
                bizWo.setProjectId(bizCrmProjectBizList.get(0).getProjectId());
                bizWo.setCrmProjectCode(bizCrmProjectBizList.get(0).getProjectCode());
            }
        }

		String woCode = this.createWoPrefixCode(bizWo.getCrmProjectCode(), bizWo.getIncidentType());
		bizWo.setWoCode(woCode);

        //投诉报修查询对应分类
        if ("cmplain".equals(bizWo.getIncidentType())) {
            DictThreeVal dictThreeVal = bizWoMapper.getBizDictTwoVal(bizWo.getThreeCategoryCode(),"cmplain");
            if(dictThreeVal!=null){
                bizWo.setOneCategoryCode(dictThreeVal.getOneCategoryCode());
                bizWo.setOneCategoryName(dictThreeVal.getOneCategoryName());
                bizWo.setTwoCategoryCode(dictThreeVal.getTwoCategoryCode());
                bizWo.setTwoCategoryName(dictThreeVal.getTwoCategoryName());
                bizWo.setThreeCategoryCode(dictThreeVal.getThreeCategoryCode());
                bizWo.setThreeCategoryName(dictThreeVal.getThreeCategoryName());
            }
        }else if ("repair".equals(bizWo.getIncidentType())){
            DictThreeVal dictThreeVal = bizWoMapper.getBizDictThreeVal(bizWo.getThreeCategoryCode(),"repair");
            if(dictThreeVal!=null){
                bizWo.setOneCategoryCode(dictThreeVal.getOneCategoryCode());
                bizWo.setOneCategoryName(dictThreeVal.getOneCategoryName());
                bizWo.setTwoCategoryCode(dictThreeVal.getTwoCategoryCode());
                bizWo.setTwoCategoryName(dictThreeVal.getTwoCategoryName());
                bizWo.setThreeCategoryCode(dictThreeVal.getThreeCategoryCode());
                bizWo.setThreeCategoryName(dictThreeVal.getThreeCategoryName());
            }
        }

        //生成投诉报修CRM工单编码
        if ("cmplain".equals(bizWo.getIncidentType()) || "repair".equals(bizWo.getIncidentType())) {
            String crmWoCode = bizWo.getCrmWoCode();
            if(StringUtils.isEmpty(bizWo.getCrmWoCode())){
                crmWoCode = this.createCrmWoCode(bizWo.getCrmProjectCode(), bizWo.getIncidentType());
            }
            bizWo.setCrmWoCode(crmWoCode);
            woMap.put("crmWoCode", crmWoCode);
        }else{
            bizWo.setWoCode(woInVo.getWoCode());
            bizWo.setCrmWoCode(woInVo.getWoCode());
            bizWo.setContactName(woInVo.getContactName());
            bizWo.setContactTel(woInVo.getContactTel());
            bizWo.setAddr(woInVo.getAddr());
        }

        bizWo.setStatus("1");
        bizWo.setTimeStamp(DateTimeUtil.getLocalTime());
        bizWo.setCreateTime(DateTimeUtil.getLocalTime());
        bizWo.setCreateBy(userId);
        bizWoMapper.insertSelective(bizWo);

        BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
        BeanUtils.copyProperties(bizWo, bizSubscribeWo);
        bizSubscribeWo.setCode(bizWo.getWoCode());
        bizSubscribeWo.setFlowId(operate.getFlowId());
        bizSubscribeWo.setBusId(bizBusiness.getId());
        bizSubscribeWo.setBusName(bizBusiness.getBusName());
        bizSubscribeWo.setSubscribeStatus(operate.getNextSubStatus());
        bizSubscribeWo.setWoStatus(operate.getNextWoStatus());
        bizSubscribeWo.setProcessId(operate.getSuccNextStep());
        bizSubscribeWo.setUserId(userId);
        bizSubscribeWo.setWoType(bizBusiness.getWoType());
        bizSubscribeWo.setCreateType(bizBusiness.getCreateType());
        bizSubscribeWo.setDealType(bizBusiness.getDealType());
        if(StringUtils.isNotEmpty(woInVo.getExpectedServiceTimeStr())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(woInVo.getExpectedServiceTimeStr());
            //判断是否小于当前时间
            if(date.getTime()<DateTimeUtil.getLocalTime().getTime()){
                bizSubscribeWo.setExpectedServiceTime(new Date());
            }else{
                bizSubscribeWo.setExpectedServiceTime(date);
            }
        }
        if(StringUtils.isNotEmpty(woInVo.getImgId())){
			ObjectRestResponse objectRestResponse = toolFegin.moveAppUploadUrlPaths(woInVo.getImgId(), DocPathConstant.ORDERWO);
            if(objectRestResponse.getStatus()==200){
                bizSubscribeWo.setImgId(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
            }
        }

        //设置项目所属公司
        if(StringUtils.isEmpty(woInVo.getCompanyId())){
            String companyId = bizWoMapper.getCompanyIdByProjectId(bizWo.getProjectId());
            bizSubscribeWo.setCompanyId(companyId);
        }else{
            bizSubscribeWo.setCompanyId(woInVo.getCompanyId());
        }
        bizSubscribeWo.setStatus("1");
        bizSubscribeWo.setTimeStamp(DateTimeUtil.getLocalTime());
        bizSubscribeWo.setCreateTime(DateTimeUtil.getLocalTime());
        bizSubscribeWo.setCreateBy(userId);
        bizSubscribeWoMapper.insertSelective(bizSubscribeWo);

        List<BizBusinessSkills> skillList = ConfigureBuffer.getInstance().geBusSkillsByBusId(bizBusiness.getId());
        if(skillList!=null && skillList.size()>0){
            //2.插入工单技能表
            BizWoSkills bizWoSkills = null;
            for(BizBusinessSkills busSkill : skillList){
                bizWoSkills = new BizWoSkills();
                bizWoSkills.setId(UUIDUtils.generateUuid());
                bizWoSkills.setWoId(woId);
                bizWoSkills.setSkillCode(busSkill.getSkillCode());
                bizWoSkills.setSkillName(busSkill.getSkillName());
                bizWoSkills.setTimeStamp(DateTimeUtil.getLocalTime());
                bizWoSkills.setCreateTime(DateTimeUtil.getLocalTime());
                bizWoSkills.setCreateBy(userId);
                bizWoSkillsMapper.insertSelective(bizWoSkills);
            }
        }

        //3.生成流水日志
        TransactionLogBean transactionLogBean = new TransactionLogBean();
        transactionLogBean.setCurrStep(operate.getTranslogStepName());
        transactionLogBean.setDesc(operate.getTranslogStepDesc());
        bizTransactionLogBiz.insertTransactionLog(woId,transactionLogBean);

        woMap.put("woId",woId);
        restResponse.setData(woMap);

        if(createWoInVo!=null) {
            createWoInVo.setContactTel(bizWo.getContactTel());
            createWoInVo.setContactName(bizWo.getContactName());
            createWoInVo.setContactUserId(bizWo.getContactUserId());
            createWoInVo.setImgId(bizSubscribeWo.getImgId());
            createWoInVo.setProjectId(bizWo.getCrmProjectCode());
            createWoInVo.setValet(bizWo.getValet());
            createWoInVo.setId(bizWo.getId());
        }
        return restResponse;
    }

	public ObjectRestResponse createPlanWoOrder(PlanWoInVo planWoInVo) throws Exception {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		Map<String,String> woMap = new HashMap<>();
		String userId = BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID();
		BizFlowProcessOperate operate = ConfigureBuffer.getInstance().getCreateOperateByBusId(BusinessConstant.getPlanWo());
		BizBusiness bizBusiness = ConfigureBuffer.getInstance().getBusinessById(BusinessConstant.getPlanWo());

		//1.生成工单
		BizWo bizWo = new BizWo();
		String woId = UUIDUtils.generateUuid();
		BeanUtils.copyProperties(planWoInVo, bizWo);
		bizWo.setId(woId);
//		String woCode = "P" + DateTimeUtil.shortDateString();
//		ObjectRestResponse objectRestResponse = codeFeign.getCode("WorkOrder", woCode, "6", "0");
//		log.info("生成计划工单编码处理结果："+objectRestResponse.toString());
//		if(objectRestResponse.getStatus()==200){
//			bizWo.setWoCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
//		}

		bizWo.setTitle(bizBusiness.getBusName());

		bizWo.setPublishUserId(userId);
		//设置项目信息
		List<String> projectIds = new ArrayList<>();
		projectIds.add(planWoInVo.getProjectId());
		List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
		if(bizCrmProjectBizList!=null && bizCrmProjectBizList.size()>0){
			bizWo.setProjectId(bizCrmProjectBizList.get(0).getProjectId());
			bizWo.setCrmProjectCode(bizCrmProjectBizList.get(0).getProjectCode());
		}
		bizWo.setIncidentType("plan");

		String woCode = this.createWoPrefixCode(bizWo.getCrmProjectCode(), bizWo.getIncidentType());
		bizWo.setWoCode(woCode);

		//生成投诉报修CRM工单编码
		woMap.put("crmWoCode", bizWo.getCrmWoCode());
		if(StringUtils.isNotEmpty(planWoInVo.getWoType())){
			bizWo.setThreeCategoryCode(planWoInVo.getWoType());
			if("EQPM".equals(planWoInVo.getWoType())){
				bizWo.setThreeCategoryName("设备设施");
			}else if("HSPM".equals(planWoInVo.getWoType())){
				bizWo.setThreeCategoryName("空间");
			}
		}
		bizWo.setStatus("1");
		bizWo.setTimeStamp(DateTimeUtil.getLocalTime());
		bizWo.setCreateTime(DateTimeUtil.getLocalTime());
		bizWo.setCreateBy(userId);
		bizWoMapper.insertSelective(bizWo);

		BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
		BeanUtils.copyProperties(bizWo, bizSubscribeWo);
		bizSubscribeWo.setCode(bizWo.getWoCode());
		bizSubscribeWo.setFlowId(operate.getFlowId());
		bizSubscribeWo.setBusId(bizBusiness.getId());
		bizSubscribeWo.setBusName(bizBusiness.getBusName());
		bizSubscribeWo.setSubscribeStatus(operate.getNextSubStatus());
		bizSubscribeWo.setWoStatus(operate.getNextWoStatus());
		bizSubscribeWo.setProcessId(operate.getSuccNextStep());
		bizSubscribeWo.setUserId(userId);
		bizSubscribeWo.setWoType(bizBusiness.getWoType());
		bizSubscribeWo.setCreateType(bizBusiness.getCreateType());
		bizSubscribeWo.setDealType(bizBusiness.getDealType());
		if(StringUtils.isNotEmpty(planWoInVo.getExpectedServiceTimeStr())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = sdf.parse(planWoInVo.getExpectedServiceTimeStr());
			//判断是否小于当前时间
			if(date.getTime()<DateTimeUtil.getLocalTime().getTime()){
				bizSubscribeWo.setExpectedServiceTime(new Date());
			}else{
				bizSubscribeWo.setExpectedServiceTime(date);
			}
		}

		//设置项目所属公司
		String companyId = bizWoMapper.getCompanyIdByProjectId(bizWo.getProjectId());
		bizSubscribeWo.setCompanyId(companyId);
		bizSubscribeWo.setStatus("1");
		bizSubscribeWo.setTimeStamp(DateTimeUtil.getLocalTime());
		bizSubscribeWo.setCreateTime(DateTimeUtil.getLocalTime());
		bizSubscribeWo.setCreateBy(userId);
		bizSubscribeWoMapper.insertSelective(bizSubscribeWo);

		List<PlanWoTrDto> planWoTrDtos = planWoInVo.getPlanWoTrDtos();
		if(planWoTrDtos!=null && planWoTrDtos.size()>0){
			//2.插入工单技能表
			BizWoSkills bizWoSkills = null;
			for(PlanWoTrDto planWoTrDto : planWoTrDtos){
				bizWoSkills = new BizWoSkills();
				bizWoSkills.setId(UUIDUtils.generateUuid());
				bizWoSkills.setWoId(woId);
				bizWoSkills.setSkillCode(planWoTrDto.getTrId());
				bizWoSkills.setSkillName(planWoTrDto.getTrDesc());
				bizWoSkills.setTimeStamp(DateTimeUtil.getLocalTime());
				bizWoSkills.setCreateTime(DateTimeUtil.getLocalTime());
				bizWoSkills.setCreateBy(userId);
				bizWoSkillsMapper.insertSelective(bizWoSkills);
			}
		}

		List<PlanWoOptDetailDto> planWoOptDetailDtos = planWoInVo.getPlanWoOptDetailDtos();
		if(planWoOptDetailDtos!=null && planWoOptDetailDtos.size()>0){
			BizPlanWoRelation woRelation = null;
			for(PlanWoOptDetailDto woOptDetailDto : planWoOptDetailDtos){
				woRelation = new BizPlanWoRelation();
				woRelation.setId(UUIDUtils.generateUuid());
				woRelation.setWoId(woId);
				woRelation.setCreateTime(DateTimeUtil.getLocalTime());
				woRelation.setCreateBy(userId);
				woRelation.setRoomId(woOptDetailDto.getRoomId());
				List<PlanWoPmpDto> planWoPmpDtos = woOptDetailDto.getPlanWoPmpDtos();
				if(planWoPmpDtos!=null && planWoPmpDtos.size()>0){
					woRelation.setPmpId(planWoPmpDtos.get(0).getPmpId());
					BizPlanWoPmp woPmp = null;
					for(PlanWoPmpDto woPmpDto : planWoPmpDtos){
						woPmp = new BizPlanWoPmp();
						BeanUtils.copyProperties(woPmpDto,woPmp);
						List<BizPlanWoPmp> planWoPmps = planWoPmpMapper.select(woPmp);
						if(planWoPmps!=null &&planWoPmps.size()>0){
							continue;
						}else {
							woPmp.setId(UUIDUtils.generateUuid());
							woPmp.setCreateTime(DateTimeUtil.getLocalTime());
							woPmp.setCreateBy(userId);
							planWoPmpMapper.insertSelective(woPmp);
						}
					}
				}
				List<PlanWoPmpsDto> planWoPmpsDtos = woOptDetailDto.getPlanWoPmpsDtos();
				if(planWoPmpsDtos!=null && planWoPmpsDtos.size()>0){
					BizPlanWoPmps woPmps = null;
					for(PlanWoPmpsDto woPmpsDto : planWoPmpsDtos){
						woPmps = new BizPlanWoPmps();
						BeanUtils.copyProperties(woPmpsDto,woPmps);
						List<BizPlanWoPmps> planWoPmpsList = planWoPmpsMapper.select(woPmps);
						if(planWoPmpsList!=null &&planWoPmpsList.size()>0){
							continue;
						}else {
							woPmps.setId(UUIDUtils.generateUuid());
							woPmps.setCreateTime(DateTimeUtil.getLocalTime());
							woPmps.setCreateBy(userId);
							planWoPmpsMapper.insertSelective(woPmps);
						}
					}
				}
				List<PlanWoEqDto> planWoEqDtos = woOptDetailDto.getPlanWoEqDtos();
				if(planWoEqDtos!=null && planWoEqDtos.size()>0){
					woRelation.setEqId(planWoEqDtos.get(0).getEqId());
					BizPlanWoEq woEq = null;
					for(PlanWoEqDto woEqDto : planWoEqDtos){
						woEq = new BizPlanWoEq();
						BeanUtils.copyProperties(woEqDto,woEq);
						List<BizPlanWoEq> planWoEqs = planWoEqMapper.select(woEq);
						if(planWoEqs!=null &&planWoEqs.size()>0){
							continue;
						}else {
							woEq.setId(UUIDUtils.generateUuid());
							woEq.setCreateTime(DateTimeUtil.getLocalTime());
							woEq.setCreateBy(userId);
							planWoEqMapper.insertSelective(woEq);
						}
					}
				}
				planWoRelationMapper.insertSelective(woRelation);
			}
		}

		//3.生成流水日志
		TransactionLogBean transactionLogBean = new TransactionLogBean();
		transactionLogBean.setCurrStep(operate.getTranslogStepName());
		transactionLogBean.setDesc(operate.getTranslogStepDesc());
		bizTransactionLogBiz.insertTransactionLog(woId,transactionLogBean);

		woMap.put("woId",woId);
		restResponse.setData(woMap);
		return restResponse;
	}

    public ObjectRestResponse<WoDetailOutVo> getWoDetail(String id,String clientType, String isPlanWO) throws Exception {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        WoDetailOutVo woDetailOutVo = new WoDetailOutVo();
        if(StringUtils.isNotEmpty(isPlanWO)){
        	if(!"1".equals(isPlanWO)){
        		isPlanWO = null;
			}
		}
        //根据工单id查询工单类型
        String wotype = bizWoMapper.selectWoTypeById(id);

        //1.获取工单详情
        WoVo detail = bizSubscribeWoMapper.getWoDetail(id);
		if("1".equals(isPlanWO)){
			detail.setIsPlanWO("1");
			detail.setPlanWoVos(planWoRelationMapper.getPlanWoVoByWoId(id));
		}else {
			detail.setIsPlanWO("0");
		}
		if("4".equals(wotype)){
           String resrrevaName = bizWoMapper.seelctResrrevaNameById(id);
            detail.setName(resrrevaName);
        }
        if("5".equals(wotype)){
            MyDecoreteInfo myDecoreteInfo = bizDecoreteApplyMapper.selectUserDecoreteInfo(id);
            if(myDecoreteInfo != null){
                detail.setCost(myDecoreteInfo.getCost());
                detail.setAddress(myDecoreteInfo.getAddress());
                detail.setCoveredArea(myDecoreteInfo.getCoveredArea());
                detail.setDecoreteStage(myDecoreteInfo.getDecoreteStage());
            }
        }
        //2.获取工单操作按钮
        List<OperateButton> operateButtonList = null;
        if(OperateConstants.ClientType.CLIENT_APP.toString().equals(clientType)){
            operateButtonList = ConfigureBuffer.getInstance().getClientButtonByProcessId(detail.getProcessId());
        }else if(OperateConstants.ClientType.SERVER_APP.toString().equals(clientType)){
            operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(detail.getProcessId());

            //判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
            String woStatusStr = detail.getWoStatus() == null ? "" : (String) detail.getWoStatus();
            String userId = BaseContextHandler.getUserID();
            if(StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleBy())){
                detail.setIsTurn("1");
            }else{
                detail.setIsTurn("0");
            }
        }
        woDetailOutVo.setDetail(detail);
        if(operateButtonList==null || operateButtonList.size()==0){
            operateButtonList = new ArrayList<>();
        }
        woDetailOutVo.setOperateButtonList(operateButtonList);

        //3.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if(transactionLogList==null && transactionLogList.size()==0){
            transactionLogList = new ArrayList<>();
        }
        woDetailOutVo.setTransactionLogList(transactionLogList);

        restResponse.data(woDetailOutVo);
        return restResponse;
    }

    public ObjectRestResponse getWoListByBusId(String busId, int page, int limit) throws Exception {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }


        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("page",startIndex);
        paramMap.put("limit",limit);
        paramMap.put("busId",busId);
        String userId = BaseContextHandler.getUserID();
        UserVo userVo = baseAppClientUserBiz.getUserNameById(userId);
        if(userVo==null){
            userVo = appServerUserMapper.getUserNameById(userId);
            if(userVo!=null){
                //服务端app用户
                paramMap.put("publishUserId", userId);
            }
        }else{
            //客户端app用户
            paramMap.put("userId", userId);
        }
       if(userVo==null){
           restResponse.setStatus(102);
           restResponse.setMessage("用户ID有误");
           return restResponse;
       }

        List<WoListVo> woList = bizWoMapper.selectWoListByUserId(paramMap);
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }

        restResponse.setData(woList);
        return restResponse;
    }

    public List<WoListVo> selectMyWoList(String userId, SearchWoSaloon searchWoSaloon){
        int page = searchWoSaloon.getPage();
        int limit = searchWoSaloon.getLimit();
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<WoListVo> woList = null;
        Map<String,Object> paramMap = new HashMap<>();
        List<String> woStatusList = new ArrayList<>();
        if("99".equals(searchWoSaloon.getWoStatus())){
            woStatusList.add("03");
            woStatusList.add("05");
            woStatusList.add("07");
            woStatusList.add("04");
        }else{
            woStatusList.add(searchWoSaloon.getWoStatus());
            if(StringUtils.isEmpty(searchWoSaloon.getWoType())
                    || (StringUtils.isNotEmpty(searchWoSaloon.getPosition()) && "1".equals(searchWoSaloon.getPosition()))){
                woStatusList.add("04");
            }
        }
        paramMap.put("woStatusList",woStatusList);
        paramMap.put("handleBy",userId);
        paramMap.put("woType",searchWoSaloon.getWoType());
        paramMap.put("page",startIndex);
        paramMap.put("limit",limit);
        paramMap.put("projectId", searchWoSaloon.getProjectId());
        woList = bizWoMapper.selectMyWoList(paramMap);
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }else{
            for (WoListVo vo :woList){
                MyDecoreteInfo myDecoreteInfo = bizDecoreteApplyMapper.selectUserDecoreteInfo(vo.getId());
                if(myDecoreteInfo != null){
                    vo.setCost(myDecoreteInfo.getCost());
                    vo.setContactTel(myDecoreteInfo.getContactTel());
                    vo.setAddress(myDecoreteInfo.getAddress());
                    vo.setCoveredArea(myDecoreteInfo.getCoveredArea());
                    vo.setDecoreteStage(myDecoreteInfo.getDecoreteStage());
                }
            }
        }
        return woList;
    }

    public List<WoListVo> getWaitWoList(String userId, SearchWoSaloon searchWoSaloon){
        //1.获取待接工单列表
        //人员：客服、物业人员、商业人员
        //工单类型：投诉报修工单、计划性工单、商业工单
        BaseAppServerUser appServerUser = appServerUserBiz.getUserById(userId);

        List<String> woIdList = new ArrayList<>();
        if("1".equals(appServerUser.getIsCustomer())){
            //1.客服人员
            //匹配规则：所属项目公司
            //工单类型：投诉报修工单、计划性工单
            if(StringUtils.isNotEmpty(searchWoSaloon.getProjectId())){
                woIdList = bizWoMapper.getWaitWoByCompanyId(null,searchWoSaloon.getProjectId());
            }else{
                woIdList = bizWoMapper.getWaitWoByCompanyId(appServerUser.getTenantId(),null);
            }
        }
        if("1".equals(appServerUser.getIsService())){
            //2.物业人员
            //匹配规则：所属项目公司、服务范围、技能
            //工单类型：投诉报修工单、计划性工单

            //2.1查询用户所拥有的技能列表
            List<String> userSkilllist = appServerUserMapper.selectAppUserSkills(userId);

            //2.2组装工单所属技能列表
            List<Map<String,String>> woBySkillsList = bizWoMapper.getWaitWoByPropertyUserId(userId,searchWoSaloon.getProjectId());
            Map<String,List<String>> woSkillListMap = new HashMap<>();
            for(Map<String,String> objectMap: woBySkillsList){
                String woId = objectMap.get("woId");
                String skillId = objectMap.get("skillId");
                List<String> skills = woSkillListMap.get(woId);
                if(skills==null){
                    skills = new ArrayList<>();
                    woSkillListMap.put(woId,skills);
                }
                skills.add(skillId);
            }
            //2.3筛选符合用户技能的工单
            List<String> containWoList = StringUtils.getTargetContainList(userSkilllist, woSkillListMap);
            if(containWoList!=null && containWoList.size()>0){
                woIdList.addAll(containWoList);
            }
        }
        if("1".equals(appServerUser.getIsBusiness())){
            //3.商业人员
            //匹配规则：所属商业公司
            //工单类型：商业工单
            woIdList = bizWoMapper.getWaitWoByCompanyId(appServerUser.getTenantId(),null);
        }
        //4.去掉重复的工单
        woIdList = StringUtils.removeDuplicate(woIdList);

        List<WoListVo> woList = null;
        if(woIdList!=null && woIdList.size()>0){
            int page = searchWoSaloon.getPage();
            int limit = searchWoSaloon.getLimit();
            if (page<1) {
                page = 1;
            }
            if (limit<1) {
                limit = 10;
            }
            //分页
            int startIndex = (page - 1) * limit;
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
            paramMap.put("woIdList",woIdList);
            woList = bizWoMapper.selectWoListByWoId(paramMap);
        }
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }else{
            for (WoListVo vo :woList){
                MyDecoreteInfo myDecoreteInfo = bizDecoreteApplyMapper.selectUserDecoreteInfo(vo.getId());
                if(myDecoreteInfo != null){
                    vo.setCost(myDecoreteInfo.getCost());
                    vo.setContactTel(myDecoreteInfo.getContactTel());
                    vo.setAddress(myDecoreteInfo.getAddress());
                    vo.setCoveredArea(myDecoreteInfo.getCoveredArea());
                    vo.setDecoreteStage(myDecoreteInfo.getDecoreteStage());
                }
            }
        }
        return woList;
    }


    public ObjectRestResponse queryIncidentList(List<String> busIdList, SearchSubIncidentInWeb searchSubIncidentInWeb){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        int page = searchSubIncidentInWeb.getPage();
        int limit = searchSubIncidentInWeb.getLimit();
        if (page < 0 ) {
            page = 1;
        }
        if (limit < 0) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        if (page != 0 && limit != 0) {
            paramMap.put("page",startIndex);
            paramMap.put("limit",limit);
        }
        paramMap.put("busIdList",busIdList);
        paramMap.put("searchVal",searchSubIncidentInWeb.getSearchVal());
        paramMap.put("startDate",searchSubIncidentInWeb.getStartDate());
        paramMap.put("endDate",searchSubIncidentInWeb.getEndDate());
        paramMap.put("projectId",searchSubIncidentInWeb.getProjectId());
        paramMap.put("workCome",searchSubIncidentInWeb.getWorkCome());
        paramMap.put("guests",searchSubIncidentInWeb.getGuests());
        paramMap.put("syncStatus",searchSubIncidentInWeb.getSyncStatus());
        paramMap.put("companyId",searchSubIncidentInWeb.getCompanyId());
        paramMap.put("woStatus", searchSubIncidentInWeb.getWoStatus());
        List<WoListForWebVo> woList = bizWoMapper.queryIncidentList(paramMap);
        int total = 0;
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }else {
            total = bizWoMapper.queryIncidentListTotal(paramMap);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",woList);

        restResponse.setData(map);
        return restResponse;
    }

    public ObjectRestResponse<WoDetailOutForWebVo> getWoDetailByWeb(String id ){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        WoDetailOutForWebVo woDetailOutVo = new WoDetailOutForWebVo();
        //1.获取工单详情
        WoListForWebVo detail = bizWoMapper.getWoDetailForWeb(id);
        woDetailOutVo.setDetail(detail);
		if(detail.getWoType() != null && detail.getWoType().equals("2")){
			ObjectRestResponse<List<PlanWoDetailVo>> response = getPlanWoContentById(detail.getId());
			woDetailOutVo.setPlanWoDetailVos(response.getData());
		}else {
			//2.获取工单操作按钮
			List<OperateButton> operateButtonList = null;
			operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(detail.getProcessId());

			//判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
			String woStatusStr = detail.getWoStatus() == null ? "" : (String) detail.getWoStatus();
			String userId = BaseContextHandler.getUserID();
			if(StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleId())){
				detail.setIsTurn("1");
			}else{
				detail.setIsTurn("0");
			}

			woDetailOutVo.setDetail(detail);
			if(operateButtonList==null || operateButtonList.size()==0){
				operateButtonList = new ArrayList<>();
			}
			woDetailOutVo.setOperateButtonList(operateButtonList);
		}


		//3.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if(transactionLogList==null && transactionLogList.size()==0){
            transactionLogList = new ArrayList<>();
        }
        woDetailOutVo.setTransactionLogList(transactionLogList);


        restResponse.data(woDetailOutVo);
        return restResponse;
    }

    /**
     * 生成投诉报修CRM工单编码
     * @param crmProjectCode
     * @param incidentType
     * @return
     */
    private String createCrmWoCode(String crmProjectCode,String incidentType){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String crmWoCode = "";
        //生成投诉报修CRM工单编码
        if(StringUtils.isNotEmpty(crmProjectCode)){
            String[] crmProjectCodeArray = crmProjectCode.split("-");
            if(crmProjectCodeArray!=null && crmProjectCodeArray.length>=2
                    && ("cmplain".equals(incidentType) || "repair".equals(incidentType))){
                if ("cmplain".equals(incidentType)) {
                    crmWoCode = crmProjectCodeArray[1] + "-T-" + DateTimeUtil.shortDateString() + "-V-";

                }else if ("repair".equals(incidentType)){
                    crmWoCode = crmProjectCodeArray[1] + "-B-" + DateTimeUtil.shortDateString() + "-V-";
                }
                objectRestResponse = codeFeign.getCode("CRMWorkOrder", crmWoCode, "4", "0");
                log.info("生成CRM工单编码处理结果："+objectRestResponse.toString());
                if(objectRestResponse.getStatus()==200){
                    crmWoCode = objectRestResponse.getData()==null?"":(String)objectRestResponse.getData();
                }
            }
        }
        return crmWoCode;
    }

	private String createWoPrefixCode(String crmProjectCode,String incidentType){
		ObjectRestResponse objectRestResponse = new ObjectRestResponse();
		String woCode = "";
		//生成投诉报修CRM工单编码
		if(StringUtils.isNotEmpty(crmProjectCode)){
			String[] crmProjectCodeArray = crmProjectCode.split("-");
			if(crmProjectCodeArray!=null && crmProjectCodeArray.length>=2
					&& ("cmplain".equals(incidentType) || "repair".equals(incidentType) || "plan".equals(incidentType))){
				if ("cmplain".equals(incidentType)) {
					woCode = crmProjectCodeArray[1] + "-C-" + DateTimeUtil.shortDateString() +"-" ;
				}else if ("repair".equals(incidentType)){
					woCode = crmProjectCodeArray[1] + "-R-" + DateTimeUtil.shortDateString() +"-";
				}else if ("plan".equals(incidentType)){
					woCode = crmProjectCodeArray[1] + "-P-" + DateTimeUtil.shortDateString() +"-";
				}
//				objectRestResponse = codeFeign.getCode("CRMWorkOrder", crmWoCode, "4", "0");
				objectRestResponse = codeFeign.getCode("WorkOrder", woCode, "6", "0");
				log.info("生成工单编码处理结果："+objectRestResponse.toString());
				if(objectRestResponse.getStatus()==200){
					woCode = objectRestResponse.getData()==null?"":(String)objectRestResponse.getData();
				}
			}
		}
		return woCode;
	}

	public ObjectRestResponse<List<PlanWoDetailVo>> getPlanWoContentById(String woId) {
		ObjectRestResponse<List<PlanWoDetailVo>> response = new ObjectRestResponse<>();
    	if(StringUtils.isEmpty(woId)){
    		response.setStatus(501);
    		response.setMessage("woId不能为空");
    		return response;
		}
		List<PlanWoDetail> planWoDetails = planWoRelationMapper.getPlanWoContentByWoId(woId);
    	if(planWoDetails!=null && planWoDetails.size()>0){
			List<PlanWoDetailVo> planWoDetailVos = new ArrayList<>();
			for (PlanWoDetail woDetail: planWoDetails) {
				PlanWoDetailVo planWoDetailVo = new PlanWoDetailVo();
				BeanUtils.copyProperties(woDetail,planWoDetailVo);
				List<PlanWoOptDetail> planWoOptDetailList = planWoPmpsMapper.getPlanWoOptDetail(woDetail.getPmpId(),woDetail.getId());
				planWoDetailVo.setPlanWoOptDetails(planWoOptDetailList);
				planWoDetailVos.add(planWoDetailVo);
			}
			response.setData(planWoDetailVos);
		}
		return response;
	}

	public ObjectRestResponse savePlanWo(PlanWoParamVo planWoParamVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(planWoParamVo == null){
			response.setStatus(501);
			response.setMessage("参数不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(planWoParamVo.getWoId())){
			response.setStatus(502);
			response.setMessage("woId不能为空！");
			return response;
		}
		if(planWoParamVo.getPlanWoParams() == null || planWoParamVo.getPlanWoParams().size() < 1){
			response.setStatus(503);
			response.setMessage("planWoParams不能为空！");
			return response;
		}
		List<PlanWoDetailVo> planWoDetailVos = getPlanWoContentById(planWoParamVo.getWoId()).getData();
		if(planWoDetailVos == null || planWoDetailVos.size() < 1){
			log.info("计划工单:"+planWoParamVo.getWoId()+"没有要执行的步骤!");
			return response;
		}
		List<PlanWoParam> planWoParams = planWoParamVo.getPlanWoParams();
		if(planWoDetailVos.size() != planWoParams.size()){
			response.setStatus(504);
			response.setMessage("参数不完整：需要检查的设备/房屋个数不对！");
			return response;
		}

		List<BizPlanWoPmpsOp> planWoPmpsOps = new ArrayList<>();
		List<String> pwrIds = new ArrayList<>();
		for (PlanWoDetailVo detailVo : planWoDetailVos) {
			boolean falg = true;
			String detailId = detailVo.getId();
			for (PlanWoParam woParam : planWoParams) {
				if(detailId.equals(woParam.getId())){
					List<PlanWoOptDetail> optDetailVos = detailVo.getPlanWoOptDetails();
					List<PlanWoOptDetail> optDetails = woParam.getPlanWoOptDetails();
					if(optDetailVos.size() != optDetails.size()){
						response.setStatus(505);
						response.setMessage("参数不完整：程序步骤没有检查完！");
						return response;
					}
					for (PlanWoOptDetail optDetail : optDetailVos) {
						falg = true;
						String optId = optDetail.getId();
						for (PlanWoOptDetail optDetailParam : optDetails) {
							if(optId.equals(optDetailParam.getId())){
								if(StringUtils.isNotEmpty(optDetailParam.getOpVal()) || "default".equals(optDetailParam.getOpType())){
									BizPlanWoPmpsOp woPmpsOp = new BizPlanWoPmpsOp();
									woPmpsOp.setId(UUIDUtils.generateUuid());
									woPmpsOp.setPwpsId(optId);
									woPmpsOp.setPwrId(detailId);
									woPmpsOp.setOpVal(optDetailParam.getOpVal());
									woPmpsOp.setCreateTime(new Date());
									planWoPmpsOps.add(woPmpsOp);
									pwrIds.add(detailId);
									falg = false;
									break;
								}
							}
						}
						if(falg){
							response.setStatus(506);
							response.setMessage("参数不完整：程序步骤没有填写完整！");
							return response;
						}
					}
					if(!falg){
						break;
					}
				}
			}
			if(falg){
				response.setStatus(507);
				response.setMessage("参数不完整：程序步骤没有填写完整！");
				return response;
			}
		}
		if(pwrIds.size() > 0){
			planWoRelationMapper.updateByIds(pwrIds,BaseContextHandler.getUserID());
		}
		if(planWoPmpsOps.size() > 0){
			for (BizPlanWoPmpsOp woPmpsOp: planWoPmpsOps) {
				try {
					planWoPmpsOpMapper.insert(woPmpsOp);
				}catch (Exception e){
					log.info("添加失败，无须处理");
					e.printStackTrace();
				}
			}
//			planWoPmpsOpMapper.insertBatch(planWoPmpsOps);
		}
		return response;
    }

	public List<String> findDiffList(List<String> firstList,List<String> scendList) {
		Map<String,String> map = new HashMap<String,String>();
		List<String> newList = new ArrayList<String>();
		for (String string : scendList) {
			map.put(string, string);
		}
		for (String string : firstList) {
			if(!map.containsKey(string)) {
				newList.add(string);
			}
		}
		return newList;
	}

	public ObjectRestResponse savePlanWoByOne(PlanWoParamVo planWoParamVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(planWoParamVo == null){
			response.setStatus(501);
			response.setMessage("参数不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(planWoParamVo.getWoId())){
			response.setStatus(502);
			response.setMessage("woId不能为空！");
			return response;
		}
		if(planWoParamVo.getPlanWoParams() == null || planWoParamVo.getPlanWoParams().size() < 1){
			response.setStatus(503);
			response.setMessage("planWoParams不能为空！");
			return response;
		}
		List<PlanWoDetailVo> planWoDetailVos = getPlanWoContentById(planWoParamVo.getWoId()).getData();
		if(planWoDetailVos == null || planWoDetailVos.size() < 1){
			log.info("计划工单:"+planWoParamVo.getWoId()+"没有要执行的步骤!");
			return response;
		}
		List<PlanWoParam> planWoParams = planWoParamVo.getPlanWoParams();

		List<BizPlanWoPmpsOp> planWoPmpsOps = new ArrayList<>();
		List<String> pwrIds = new ArrayList<>();
		for (PlanWoDetailVo detailVo : planWoDetailVos) {
			boolean falg = true;
			String detailId = detailVo.getId();
			for (PlanWoParam woParam : planWoParams) {
				if(detailId.equals(woParam.getId())){
					List<PlanWoOptDetail> optDetailVos = detailVo.getPlanWoOptDetails();
					List<PlanWoOptDetail> optDetails = woParam.getPlanWoOptDetails();
					if(optDetailVos.size() != optDetails.size()){
						response.setStatus(505);
						response.setMessage("参数不完整：程序步骤没有检查完！");
						return response;
					}
					for (PlanWoOptDetail optDetail : optDetailVos) {
						falg = true;
						String optId = optDetail.getId();
						for (PlanWoOptDetail optDetailParam : optDetails) {
							if(optId.equals(optDetailParam.getId())){
								if(StringUtils.isNotEmpty(optDetailParam.getOpVal()) || "default".equals(optDetailParam.getOpType())){
									BizPlanWoPmpsOp woPmpsOp = new BizPlanWoPmpsOp();
									woPmpsOp.setId(UUIDUtils.generateUuid());
									woPmpsOp.setPwpsId(optId);
									woPmpsOp.setPwrId(detailId);
									woPmpsOp.setOpVal(optDetailParam.getOpVal());
									woPmpsOp.setCreateTime(new Date());
									planWoPmpsOps.add(woPmpsOp);
									pwrIds.add(detailId);
									falg = false;
									break;
								}
							}
						}
						if(falg){
							response.setStatus(506);
							response.setMessage("参数不完整：程序步骤没有填写完整！");
							return response;
						}
					}
					if(!falg){
						break;
					}
				}
			}
			if(falg){
				continue;
			}else {
				break;
			}
		}
		if(pwrIds.size() > 0){
			planWoRelationMapper.updateByIds(pwrIds,BaseContextHandler.getUserID());
		}
		if(planWoPmpsOps.size() > 0){
			planWoPmpsOpMapper.insertBatch(planWoPmpsOps);
		}
		return response;
	}

	public ObjectRestResponse getPlanWoExcel(List<String> busIdList, SearchSubIncidentInWeb searchSubIncidentInWeb) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("busIdList",busIdList);
		paramMap.put("searchVal",searchSubIncidentInWeb.getSearchVal());
		paramMap.put("startDate",searchSubIncidentInWeb.getStartDate());
		paramMap.put("endDate",searchSubIncidentInWeb.getEndDate());
		paramMap.put("projectId",searchSubIncidentInWeb.getProjectId());
		paramMap.put("companyId",searchSubIncidentInWeb.getCompanyId());
		paramMap.put("workCome",searchSubIncidentInWeb.getWorkCome());
		paramMap.put("guests",searchSubIncidentInWeb.getGuests());
		paramMap.put("syncStatus",searchSubIncidentInWeb.getSyncStatus());
		List<PlanWoExcelVo> woList = bizWoMapper.getPlanWoExcel(paramMap);
		Map<String,Object> map = new HashMap<>();
		map.put("list",woList);
		restResponse.setData(map);
		return restResponse;
	}

    public ObjectRestResponse<QrResultVo> qrForWo(String url) {
        ObjectRestResponse response = new ObjectRestResponse();
        String[] info = url.split("=");
        String queryId = "";
        if (info.length > 1) {
            queryId = info[1];
        }else {
            queryId = info[0];
        }
        QrResultVo qrResultVo = new QrResultVo();
        String[] split = queryId.split("-");
        if (queryId.split("-").length == 7) {
            //空间二维码
            BizCrmHouse baseHouse =  bizCrmHouseMapper.getRegionInfo(queryId);
            if (baseHouse != null) {
                qrResultVo.setAddr(baseHouse.getRecordHouseName());
                qrResultVo.setRoomCode(queryId);
                qrResultVo.setRoomId(baseHouse.getHouseId());
                qrResultVo.setRoomName(baseHouse.getRecordHouseName());
                qrResultVo.setIsPublic(baseHouse.getPropertyType());
            }else {
                response.setStatus(101);
                response.setMessage("无效二维码！");
                return response;
            }
        }else {
            //设备设施二维码
            BizPlanWoEq eqInfo = bizCrmHouseMapper.getEqInfo(queryId);
            if (eqInfo != null) {
                BizCrmHouse baseHouse =  bizCrmHouseMapper.getRegionInfo(eqInfo.getEqCode().substring(0,eqInfo.getEqCode().lastIndexOf("-")));
                qrResultVo.setEqId(eqInfo.getId());
                qrResultVo.setEqCode(queryId);
                qrResultVo.setEqName(eqInfo.getEqName());
                qrResultVo.setRoomId(baseHouse.getHouseId());
                qrResultVo.setAddr(baseHouse.getRecordHouseName());
                qrResultVo.setIsPublic(baseHouse.getPropertyType());
            }else {
                response.setStatus(101);
                response.setMessage("无效二维码！");
                return response;
            }
        }
        return ObjectRestResponse.ok(qrResultVo);
    }
}