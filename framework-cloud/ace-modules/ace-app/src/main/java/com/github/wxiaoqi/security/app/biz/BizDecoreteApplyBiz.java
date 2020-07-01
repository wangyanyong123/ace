package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.CreateWoInVo;
import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import com.github.wxiaoqi.security.api.vo.order.out.OperateButton;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.decorete.in.DecoreteApplyParam;
import com.github.wxiaoqi.security.app.vo.decorete.out.DecoreteVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteInfo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.WoDetail;
import com.github.wxiaoqi.security.app.vo.in.WoInVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.UserBillOutVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 装修监理申请表
 *
 * @author huangxl
 * @Date 2019-04-01 15:20:10
 */
@Service
public class BizDecoreteApplyBiz extends BusinessBiz<BizDecoreteApplyMapper,BizDecoreteApply> {
    private Logger log = LoggerFactory.getLogger(BizDecoreteApplyBiz.class);
    @Autowired
    private BizDecoreteApplyMapper bizDecoreteApplyMapper;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;

    /**
     * 查询当前项目下的装修监理服务
     * @param projectId
     * @return
     */
    public ObjectRestResponse<DecoreteVo> getDecoreteList(String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecoreteVo decoreteVo = bizDecoreteApplyMapper.selectDecoreteList(projectId);
        if(decoreteVo == null){
            decoreteVo = new DecoreteVo();
            String isShow = "0";
            msg.setData(isShow);
            return msg;
        }
        return ObjectRestResponse.ok(decoreteVo);
    }


    /**
     * 查询装修监理服务的详情
     * @param id
     * @return
     */
    public ObjectRestResponse<DecoreteVo> getDecoreteInfo(String id){
        DecoreteVo decoreteVo =bizDecoreteApplyMapper.selectDecoreteInfo(id);
        if(decoreteVo == null){
            decoreteVo = new DecoreteVo();
        }
        return ObjectRestResponse.ok(decoreteVo);
    }


    /**
     * 提交装修监理服务
     * @param param
     * @return
     */
    public ObjectRestResponse saveDecoreteApply(DecoreteApplyParam param){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectRestResponse msg = new ObjectRestResponse();
        //判断用户当前角色
        CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if("4".equals(userInfo.getIdentityType())){
            msg.setStatus(202);
            msg.setMessage("请前去选择身份验证!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getContactorName())){
            msg.setStatus(201);
            msg.setMessage("联系人不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getContactTel())){
            msg.setStatus(201);
            msg.setMessage("联系方式不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getAddress())){
            msg.setStatus(201);
            msg.setMessage("联系地址不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getDecoreteStage())){
            msg.setStatus(201);
            msg.setMessage("装修阶段不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getCoveredArea())){
            msg.setStatus(201);
            msg.setMessage("建筑面积不能为空!");
            return msg;
        }
        String woCode = "";
        List<String> projectIds = new ArrayList<>();
        projectIds.add(param.getProjectId());
        List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
        String projectCode = "";
        String wcode = "";
        if(bizCrmProjectBizList!=null && bizCrmProjectBizList.size()>0){
            projectCode = bizCrmProjectBizList.get(0).getProjectCode();
        }
        String[] projectCodeArray = projectCode.split("-");
        if(projectCodeArray!=null && projectCodeArray.length>=2) {
            wcode = projectCodeArray[1];
        }
        String bstype = "-D-";
        String subCode = wcode + "-" + bstype + DateTimeUtil.shortDateString()+"-" ;
        ObjectRestResponse response = codeFeign.getCode("Decorete", subCode, "6", "0");
        log.info("生成工单编码处理结果："+response.toString());
        if(response.getStatus()==200){
            woCode = (String)response.getData();
        }
        String woId = "";
        UserBillOutVo userBillOutVo = null;
        //支付金额
        double cost = Double.parseDouble(param.getCoveredArea()) * Double.parseDouble(param.getServicePrice());
        try {
            CreateWoInVo createWoInVo = new CreateWoInVo();
            createWoInVo.setContactUserId(BaseContextHandler.getUserID());
            createWoInVo.setContactName(param.getContactorName());
            createWoInVo.setContactTel(param.getContactTel());
            createWoInVo.setAddr(param.getAddress());
            createWoInVo.setProjectId(param.getProjectId());
            createWoInVo.setRoomId(param.getRoomId());
            createWoInVo.setComeFrom("1");
            createWoInVo.setExpectedServiceTimeStr(sdf.format(new Date()));
            createWoInVo.setBusId(BusinessConstant.getDecoreteBusId());
            WoInVo woInVo = new WoInVo();
            BeanUtils.copyProperties(createWoInVo,woInVo);
            woInVo.setWoCode(woCode);
            userBillOutVo = userActivityOrder(woInVo,Double.toString(cost)).getData();
            if(userBillOutVo == null){
                msg.setStatus(201);
                msg.setMessage("生成工单失败!");
                return msg;
            }
            BizDecoreteApply apply = new BizDecoreteApply();
            apply.setId(UUIDUtils.generateUuid());
            apply.setSubId(userBillOutVo.getSubId());
            apply.setDecoreteId(param.getDecoreteId());
            apply.setUserId(BaseContextHandler.getUserID());
            apply.setContactorName(param.getContactorName());
            apply.setContactTel(param.getContactTel());
            apply.setAddress(param.getAddress());
            apply.setCoveredArea(param.getCoveredArea());
            apply.setDecoreteStage(param.getDecoreteStage());
            apply.setCost(Double.toString(cost));
            apply.setCreateBy(BaseContextHandler.getUserID());
            apply.setCreateTime(new Date());
            apply.setTimeStamp(new Date());
            if(bizDecoreteApplyMapper.insertSelective(apply) < 0){
                msg.setStatus(201);
                msg.setMessage("提交预约失败!");
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setMessage("Operation succeed!");
        msg.setData(userBillOutVo);
        return msg;
    }

    /**
     * 装修监理下单
     * @param woInVo
     * @return
     */
    public ObjectRestResponse<UserBillOutVo> userActivityOrder(WoInVo woInVo,String cost) throws Exception  {
        ObjectRestResponse response = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        BaseAppClientUser user = baseAppClientUserBiz.getUserById(userId);
        String busId = BusinessConstant.getDecoreteBusId();
        BizBusiness bizBusiness = ConfigureBuffer.getInstance().getBusinessById(busId);

        //1.创建订单
        ObjectRestResponse objectRestResponse = createWoOrder(woInVo);
        if (objectRestResponse.getStatus() == 200) {
            Map<String,String> woMap = (Map<String,String>)objectRestResponse.getData();
            //维护订单账单表
            BizAccountBook bizAccountBook = new BizAccountBook();
            bizAccountBook.setId(UUIDUtils.generateUuid());
            bizAccountBook.setPayStatus("1");
            bizAccountBook.setSubId(woMap.get("woId"));
            String actualId = UUIDUtils.generateUuid();
            bizAccountBook.setActualId(actualId);
            bizAccountBook.setPayId(userId);
            bizAccountBook.setActualCost(new BigDecimal(cost));
            bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
            bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
            bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
            bizAccountBookMapper.insertSelective(bizAccountBook);
            //返回结果参数
            UserBillOutVo userBillOutVo = new UserBillOutVo();
            userBillOutVo.setActualId(actualId);
            userBillOutVo.setSubId(woMap.get("woId"));
            userBillOutVo.setActualPrice(cost);
            userBillOutVo.setTitle(bizBusiness.getBusName());
            response.setData(userBillOutVo);
            log.info("支付信息：{}",userBillOutVo.toString());
        }
        return response;
    }




    public ObjectRestResponse createWoOrder(WoInVo woInVo) throws Exception {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        Map<String,String> woMap = new HashMap<>();
        String userId = BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID();
        BizFlowProcessOperate operate = ConfigureBuffer.getInstance().getCreateOperateByBusId(BusinessConstant.getDecoreteBusId());
        BizBusiness bizBusiness = ConfigureBuffer.getInstance().getBusinessById(BusinessConstant.getDecoreteBusId());

        //1.生成工单
        BizWo bizWo = new BizWo();
        String woId = UUIDUtils.generateUuid();
        BeanUtils.copyProperties(woInVo, bizWo);
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
        projectIds.add(woInVo.getProjectId());
        List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
        if(bizCrmProjectBizList!=null && bizCrmProjectBizList.size()>0){
            bizWo.setProjectId(bizCrmProjectBizList.get(0).getProjectId());
            bizWo.setCrmProjectCode(bizCrmProjectBizList.get(0).getProjectCode());
        }
        bizWo.setIncidentType("decorete");
        bizWo.setWoCode(woInVo.getWoCode());
        bizWo.setCrmWoCode(woInVo.getWoCode());
        bizWo.setContactName(woInVo.getContactName());
        bizWo.setContactTel(woInVo.getContactTel());
        bizWo.setAddr(woInVo.getAddr());

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
        if(com.github.wxiaoqi.security.common.util.StringUtils.isNotEmpty(woInVo.getExpectedServiceTimeStr())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(woInVo.getExpectedServiceTimeStr());
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


        //3.生成流水日志
        TransactionLogBean transactionLogBean = new TransactionLogBean();
        transactionLogBean.setCurrStep(operate.getTranslogStepName());
        transactionLogBean.setDesc(operate.getTranslogStepDesc());
        bizTransactionLogBiz.insertTransactionLog(woId,transactionLogBean);

        woMap.put("woId",woId);
        restResponse.setData(woMap);
        return restResponse;
    }


    /**
     * 支付宝、微信回调通知成功后 待支付状态改为待接单
     * @param subId
     * @return
     */
    public ObjectRestResponse updateDecoreteApplyStatus(String subId){
        ObjectRestResponse msg = new ObjectRestResponse();
        //待支付状态改为待接单
        int temp = bizDecoreteApplyMapper.updateDecoreteApplyStatus(BaseContextHandler.getUserID(),subId);
        if (temp != 1) {
            log.error("update product sales error");
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询当前项目下的用户预约装修监理记录
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<MyDecoreteVo>> getUserDecoreteList(String projectId, Integer page, Integer limit){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(userId)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("用户未登陆，请登陆系统");
            return objectRestResponse;
        }
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<MyDecoreteVo> myDecoreteVoList = bizDecoreteApplyMapper.selectUserDecoreteListByProjectId(userId,projectId,startIndex,limit);
        if(myDecoreteVoList == null || myDecoreteVoList.size() == 0){
            myDecoreteVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(myDecoreteVoList);
    }



    /**
     * 查看预约详情
     * @param id
     * @return
     */
    public ObjectRestResponse<WoDetail> getMyDecoreteInfo(String id){
        WoDetail woDetails = new WoDetail();
        MyDecoreteInfo myDecoreteInfo = bizDecoreteApplyMapper.selectUserDecoreteInfo(id);
        if(myDecoreteInfo == null){
            myDecoreteInfo = new MyDecoreteInfo();
        }
        woDetails.setMyDecoreteInfo(myDecoreteInfo);

        //2.获取工单操作按钮
        List<OperateButton> operateButtonList = null;
        operateButtonList = ConfigureBuffer.getInstance().getClientButtonByProcessId(myDecoreteInfo.getProcessId());
        if(operateButtonList==null || operateButtonList.size()==0){
            operateButtonList = new ArrayList<>();
        }
        woDetails.setOperateButtonList(operateButtonList);

        //3.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if(transactionLogList==null && transactionLogList.size()==0){
            transactionLogList = new ArrayList<>();
        }
        woDetails.setTransactionLogList(transactionLogList);
        //4.支付参数
        String busName = bizDecoreteApplyMapper.selectBusNameById(BusinessConstant.getDecoreteBusId());
        UserBillOutVo userBillOutVo = bizDecoreteApplyMapper.selectActualInfoBySubId(id);
        userBillOutVo.setTitle(busName);
        woDetails.setUserBillOutVo(userBillOutVo);

        return ObjectRestResponse.ok(woDetails);
    }









}