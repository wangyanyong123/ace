package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressDeliveryOut;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.context.AppContextHandler;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.in.SubInVo;
import com.github.wxiaoqi.security.app.vo.order.BuyProductInfoTwo;
import com.github.wxiaoqi.security.app.vo.order.out.SubContactVo;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecCodeVo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.reservation.in.AppendSubReservationParam;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单
 *
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Service
@Slf4j
public class BizSubBiz extends BusinessBiz<BizSubscribeMapper,BizSubscribe> {

    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private BizProductSpecMapper bizProductSpecMapper;
    @Autowired
    private BizShoppingCartMapper bizShoppingCartMapper;
    @Autowired
    private BizPostalAddressMapper bizPostalAddressMapper;
    @Autowired
    private ConfigureServiceBiz configureServiceBiz;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private BizCouponBiz bizCouponBiz;
    @Autowired
    private BizCouponUseMapper bizCouponUseMapper;
    @Autowired
    private BizCouponMapper bizCouponMapper;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;
    @Autowired
    private SettlementMapper settlementMapper;
    @Autowired
    private BizReservationMapper bizReservationMapper;

    public ObjectRestResponse  buyProduct(BuyProductInfo buyProductInfo, String busId){
        ObjectRestResponse result = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        if(buyProductInfo==null || buyProductInfo.getCompanyProductList()==null || buyProductInfo.getCompanyProductList().size()==0){
            result.setStatus(101);
            result.setMessage("参数为空");
            return result;
        }
        result = checkCompanyProductData(buyProductInfo.getCompanyProductList(),busId,buyProductInfo.getProcCode());
        if(result==null || result.getStatus()!=200){
            return result;
        }
        List<BuyProductInfoTwo> buyProductInfoTwoList = (List<BuyProductInfoTwo>)result.getData();
        //多个公司合并的支付id
        String accountPid = UUIDUtils.generateUuid();
        //多个公司下单标题
        StringBuffer subProductTitleBuffer = new StringBuffer();
        //返回结果参数
        BuyProductOutVo buyProductOutVo = new BuyProductOutVo();
        List<BizSubProduct> bizSubProductList = null;
        List<String> shoppingCartIdList = null;
        //订单商品总额
        BigDecimal subProductCost = new BigDecimal(0);
        for (BuyProductInfoTwo buyProductInfoTwo : buyProductInfoTwoList) {
            bizSubProductList = buyProductInfoTwo.getBizSubProductList();
            shoppingCartIdList = buyProductInfoTwo.getShoppingCartIdList();
            String companyId = buyProductInfoTwo.getCompanyId();
            BigDecimal actualCost = buyProductInfoTwo.getActualCost();
            BigDecimal productCost = buyProductInfoTwo.getProductCost();
            int subNum = buyProductInfoTwo.getSubNum();
            String description = buyProductInfoTwo.getDescription();
            String remark = buyProductInfoTwo.getRemark();
            String imgId = buyProductInfoTwo.getImgId();

            SubInVo subInVo = new SubInVo();
            subInVo.setBusId(busId);
            subInVo.setDescription(description);
            subInVo.setCompanyId(companyId);
            subInVo.setActualCost(actualCost);
            subInVo.setReceivableCost(actualCost);
            subInVo.setProductCost(productCost);
            subInVo.setExpressCost(buyProductInfoTwo.getExpressCost());
            subInVo.setDiscountCost(buyProductInfoTwo.getDiscountCost());
            subInVo.setSource(buyProductInfo.getSource());
            subInVo.setProjectId(buyProductInfo.getProjectId());
            subInVo.setContactName(buyProductInfo.getContactName());
            subInVo.setContactTel(buyProductInfo.getContactTel());
            subInVo.setDeliveryAddr(buyProductInfo.getAddr());
            subInVo.setUserId(userId);
            subInVo.setRemark(remark);
            subInVo.setTotalNum(subNum);
            subInVo.setPrice(bizSubProductList.get(0).getPrice());
            subInVo.setUnit(bizSubProductList.get(0).getUnit());
            subInVo.setImgId(imgId);
            subInVo.setInvoiceType(buyProductInfoTwo.getInvoiceType());
            subInVo.setInvoiceName(buyProductInfoTwo.getInvoiceName());
            subInVo.setDutyNum(buyProductInfoTwo.getDutyNum());
            subInVo.setCouponId(buyProductInfoTwo.getCouponId());
            subInVo.setReservationTime(buyProductInfo.getReservationTime());
            //1.创建订单
            ObjectRestResponse<BizSubscribe> restResponse = this.createSubscribe(subInVo);
            if (restResponse.getStatus() == 200) {
                BizSubscribe bizSubscribe = restResponse.getData();
                //2.维护订单商品数据
                for (BizSubProduct bizSubProductTemp : bizSubProductList) {
                    bizSubProductTemp.setSubId(bizSubscribe.getId());
                    bizSubProductMapper.insert(bizSubProductTemp);
                }
                //3.维护账单表
                BizAccountBook bizAccountBook = new BizAccountBook();
                bizAccountBook.setId(UUIDUtils.generateUuid());
                bizAccountBook.setPayStatus("1");
                bizAccountBook.setSubId(bizSubscribe.getId());
                String atualId = UUIDUtils.generateUuid();
                bizAccountBook.setActualId(atualId);
                //多家公司下单
                if (buyProductInfoTwoList.size() > 1) {
                    bizAccountBook.setAccountPid(accountPid);
                }
                bizAccountBook.setActualCost(actualCost);
                if (actualCost.compareTo(BigDecimal.ZERO) == 0) {
                    bizAccountBook.setPayId("1");
                }
                bizAccountBook.setStatus("1");
                bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
                bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
                bizAccountBook.setCreateBy(userId);
                bizAccountBookMapper.insertSelective(bizAccountBook);
                //4.如果有加入购物车，则删除
                if (shoppingCartIdList != null && shoppingCartIdList.size() > 0) {
                    for (String shoppingCartId : shoppingCartIdList) {
                        BizShoppingCart bizShoppingCart = new BizShoppingCart();
                        bizShoppingCart.setId(shoppingCartId);
                        bizShoppingCart.setStatus("0");
                        bizShoppingCart.setTimeStamp(DateTimeUtil.getLocalTime());
                        bizShoppingCart.setModifyTime(DateTimeUtil.getLocalTime());
                        bizShoppingCart.setModifyBy(userId);
                        bizShoppingCartMapper.updateByPrimaryKeySelective(bizShoppingCart);
                    }
                }
                if ((BusinessConstant.getShoppingBusId().equals(busId) || BusinessConstant.getTravelBusId().equals(busId)
                || BusinessConstant.getReservationBusId().equals(busId))
                        && buyProductInfoTwo.getCouponId()!=null) {
                    //5.设置优惠券状态为已使用
                    BizCouponUse bizCouponUse = new BizCouponUse();
                    bizCouponUse.setId(buyProductInfoTwo.getCouponId());
                    bizCouponUse.setUseStatus("2");
                    bizCouponUse.setOrderId(bizSubscribe.getId());
                    bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
                }


                buyProductOutVo.setActualId(atualId);
                buyProductOutVo.setSubId(bizSubscribe.getId());
                buyProductOutVo.setActualPrice((actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                buyProductOutVo.setTitle(bizSubscribe.getTitle());
            }
            subProductCost = subProductCost.add(actualCost);
            subProductTitleBuffer.append(",").append(subInVo.getDescription());
        }
        //多家公司下单
        if(buyProductInfoTwoList.size()>1){
            buyProductOutVo.setSubId("");//订单id需要为空，前端需要判断是否为合并支付订单
            buyProductOutVo.setActualId(accountPid);
            buyProductOutVo.setActualPrice((subProductCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
            buyProductOutVo.setTitle("合并支付订单("+subProductTitleBuffer.substring(1)+")");
        }
        if (subProductCost.compareTo(BigDecimal.ZERO) == 0) {
            PayOrderFinishIn payOrderFinishIn = new PayOrderFinishIn();
            payOrderFinishIn.setActualId(buyProductOutVo.getActualId());
            payOrderFinishIn.setPayId("1");
            payOrderFinishIn.setPayType("1");
            payOrderFinishIn.setTotalFee((subProductCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
            HashMap<String, String> dataMap = settlementMapper.selectAliPayInMall();
            String mchid = "";
            if(dataMap!=null){
                mchid = dataMap.get("partner") == null ? "" : (String)dataMap.get("partner");
            }
            payOrderFinishIn.setMchId(mchid);
            try {
                //休眠一秒，解决订单详情时间轴顺序显示的问题
                Thread.sleep(1000);
            }catch (Exception e){
                log.error("商品下单休眠异常"+e.getMessage());
            }
            bizSubscribeWoBiz.doPayOrderFinish(payOrderFinishIn);
        }
        result.setData(buyProductOutVo);
        return result;
    }


    public ObjectRestResponse<BizSubscribe> createSubscribe(SubInVo subInVo){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        BizFlowProcessOperate operate = ConfigureBuffer.getInstance().getCreateOperateByBusId(subInVo.getBusId());
        BizBusiness bizBusiness = ConfigureBuffer.getInstance().getBusinessById(subInVo.getBusId());

        //1.生成订单
        BizSubscribe bizSubscribe = new BizSubscribe();
        String subId = UUIDUtils.generateUuid();
        BeanUtils.copyProperties(subInVo, bizSubscribe);
        bizSubscribe.setId(subId);

        List<String> projectIds = new ArrayList<>();
        projectIds.add(subInVo.getProjectId());
        List<BizCrmProject> bizCrmProjectBizList = bizCrmProjectMapper.getByIds(projectIds);
        String projectCode = "";
        String code = "";
        if(bizCrmProjectBizList!=null && bizCrmProjectBizList.size()>0){
            projectCode = bizCrmProjectBizList.get(0).getProjectCode();
        }
		String[] projectCodeArray = projectCode.split("-");
		if(projectCodeArray!=null && projectCodeArray.length>=2) {
			code = projectCodeArray[1];
		}
		String companyCode = bizSubscribeMapper.getByCompanyId(subInVo.getCompanyId());
        if (companyCode == null) {
            companyCode = "";
        }

        String bstype = "";
        if(BusinessConstant.getShoppingBusId().equals(subInVo.getBusId())
                || BusinessConstant.getSeckillBusId().equals(subInVo.getBusId())
                || BusinessConstant.getTravelBusId().equals(subInVo.getBusId())){
			bstype = "-P-";
		}else if(BusinessConstant.getGroupBuyingBusId().equals(subInVo.getBusId())){
			bstype = "-G-";
		}else if(BusinessConstant.getReservationBusId().equals(subInVo.getBusId())){
			bstype = "-S-";
		}else if(BusinessConstant.getPropertyBusId().equals(subInVo.getBusId())){
            bstype = "-W-";
        }
		String subCode = code + "-" + companyCode+ bstype + DateTimeUtil.shortDateString()+"-" ;
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Subscribe", subCode, "6", "0");
        log.info("生成工单编码处理结果："+objectRestResponse.toString());
        if(objectRestResponse.getStatus()==200){
            bizSubscribe.setSubCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        bizSubscribe.setTitle(bizBusiness.getBusName()+"("+subInVo.getDescription()+")");
        bizSubscribe.setUserId(userId);
        bizSubscribe.setStatus("1");
        bizSubscribe.setTimeStamp(DateTimeUtil.getLocalTime());
        bizSubscribe.setCreateTime(DateTimeUtil.getLocalTime());
        bizSubscribe.setCreateBy(userId);
        bizSubscribe.setCouponId(subInVo.getCouponId());
        bizSubscribeMapper.insertSelective(bizSubscribe);

        BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
        BeanUtils.copyProperties(bizSubscribe, bizSubscribeWo);
        bizSubscribeWo.setWoType(bizBusiness.getWoType());
        bizSubscribeWo.setCreateType(bizBusiness.getCreateType());
        bizSubscribeWo.setDealType(bizBusiness.getDealType());
        bizSubscribeWo.setCode(bizSubscribe.getSubCode());
        bizSubscribeWo.setFlowId(operate.getFlowId());
        bizSubscribeWo.setBusId(bizBusiness.getId());
        bizSubscribeWo.setBusName(bizBusiness.getBusName());
        bizSubscribeWo.setSubscribeStatus(operate.getNextSubStatus());
        bizSubscribeWo.setWoStatus(operate.getNextWoStatus());
        bizSubscribeWo.setProcessId(operate.getSuccNextStep());
        bizSubscribeWo.setCompanyId(subInVo.getCompanyId());
        bizSubscribeWo.setImgId(subInVo.getImgId());
        //生活预约服务时间放在期望服务时间
        if(BusinessConstant.getReservationBusId().equals(bizBusiness.getId())){
            if(StringUtils.isNotEmpty(subInVo.getReservationTime())){
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = sdf.parse(subInVo.getReservationTime());
                    //判断是否小于当前时间
                    if(date.getTime()<DateTimeUtil.getLocalTime().getTime()){
                        bizSubscribeWo.setExpectedServiceTime(new Date());
                    }else{
                        bizSubscribeWo.setExpectedServiceTime(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        bizSubscribeWo.setStatus("1");
        bizSubscribeWo.setTimeStamp(DateTimeUtil.getLocalTime());
        bizSubscribeWo.setCreateTime(DateTimeUtil.getLocalTime());
        bizSubscribeWo.setCreateBy(userId);
        bizSubscribeWoMapper.insertSelective(bizSubscribeWo);

        //2.生成流水日志
        TransactionLogBean transactionLogBean = new TransactionLogBean();
        transactionLogBean.setCurrStep(operate.getTranslogStepName());
        transactionLogBean.setDesc(operate.getTranslogStepDesc());
        bizTransactionLogBiz.insertTransactionLog(subId,transactionLogBean);

        restResponse.setData(bizSubscribe);
        return restResponse;
    }

    public ObjectRestResponse<SubDetailOutVo> getSubDetail(String id,String clientType) throws Exception {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        SubDetailOutVo subDetailOutVo = new SubDetailOutVo();
        //1.获取订单详情
        SubVo detail = bizSubscribeWoMapper.getSubWoDetail(id);
        if(detail==null){
            restResponse.setStatus(101);
            restResponse.setMessage("该ID获取不到详情");
            return restResponse;
        }

        //2.获取订单产品信息
        List<SubProductInfo> subProductInfoList = bizSubProductMapper.getSubProductInfo(id);
        if(subProductInfoList==null || subProductInfoList.size()==0){
            subProductInfoList = new ArrayList<>();
        }
        detail.setSubProductInfoList(subProductInfoList);


        //3.获取操作按钮
        List<OperateButton> operateButtonList = null;
        if(OperateConstants.ClientType.CLIENT_APP.toString().equals(clientType)){
            operateButtonList = ConfigureBuffer.getInstance().getClientButtonByProcessId(detail.getProcessId());
        }else if(OperateConstants.ClientType.SERVER_APP.toString().equals(clientType)){
            operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(detail.getProcessId());

            //判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
            String woStatusStr = detail.getWoStatus() == null ? "" : (String) detail.getWoStatus();
            String userId = BaseContextHandler.getUserID();
            if(StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleByUserId())){
                detail.setIsTurn("1");
            }else{
                detail.setIsTurn("0");
            }
        }
        subDetailOutVo.setDetail(detail);
        if(operateButtonList==null || operateButtonList.size()==0){
            operateButtonList = new ArrayList<>();
        }else{
            List<OperateButton> operateButtonListTemp = new ArrayList<>();
            for (OperateButton operateButton : operateButtonList){
                List<BizFlowServiceBean> bizFlowServiceBeanList = operateButton.getBeforeServiceList();
                if(bizFlowServiceBeanList!=null && bizFlowServiceBeanList.size()>0){
                    List<BizFlowService> beforeServiceList = new ArrayList<>();
                    for (BizFlowServiceBean bizFlowServiceBean : bizFlowServiceBeanList){
                        BizFlowService bizFlowService= new BizFlowService();
                        BeanUtils.copyProperties(bizFlowServiceBean,bizFlowService);
                        beforeServiceList.add(bizFlowService);
                    }
                    //2.1调用节点前服务
                    List<Object> paramList = new ArrayList<Object>();
                    paramList.add(id);
                    ObjectRestResponse objectRestResponse = configureServiceBiz.doBeforeService(beforeServiceList,paramList);
                    if(objectRestResponse!=null && objectRestResponse.getStatus()==200){
                        operateButtonListTemp.add(operateButton);
                    }
                }else{
                    operateButtonListTemp.add(operateButton);
                    if("申请售后".equals(operateButton.getOperateName()) && StringUtils.isNotEmpty(detail.getReviewWoTimeStr())){
                        Config config = ConfigService.getConfig("ace-app");
                        //默认7天只会不显示申请售后按钮
                        int hours = config.getIntProperty("wo.postSale.disappearHours", 168);
                        if(DateUtils.secondBetween(DateUtils.parseDateTime(detail.getReviewWoTimeStr(),"yyyy-MM-dd HH:mm:ss"),new Date()) > hours*3600){
                            operateButtonListTemp.remove(operateButton);
                        }
                    }
                    if("服务评价".equals(operateButton.getOperateName()) && StringUtils.isNotEmpty(detail.getCommentStatus())){
                        if("2".equals(detail.getCommentStatus())){
                            //已评价
                            operateButtonListTemp.remove(operateButton);
                        }
                    }
                }
            }
            operateButtonList = operateButtonListTemp;
        }

        subDetailOutVo.setOperateButtonList(operateButtonList);

        //4.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if(transactionLogList==null && transactionLogList.size()==0){
            transactionLogList = new ArrayList<>();
        }
        subDetailOutVo.setTransactionLogList(transactionLogList);

        //判断能否修改订单
        String version = AppContextHandler.getAppVersion();
        String platform = AppContextHandler.getPlatform();
        if (StringUtils.isNotEmpty(version) && StringUtils.isNotEmpty(platform)) {
            boolean canModify = false;
            if ((platform.equals(AppContextHandler.PLATFORM_IOS) && VersionUtil.compareVersion(version, "1.0.10") > 0) ||
                    (platform.equals(AppContextHandler.PLATFORM_ANDROID) && VersionUtil.compareVersion(version, "1.0.12") > 0)) {
                if (detail.getSubStatus().equals("1")) {//处理中
                    canModify = true;
                } else if (detail.getSubStatus().equals("5")
                        && StringUtils.isNotEmpty(detail.getExpectedServiceTimeStr())) {//待确认
                    int seconds = DateUtils.secondBetween(new Date(), DateUtils.parseDateTime(detail.getExpectedServiceTimeStr(),"yyyy-MM-dd HH:mm:ss"));
                    if (seconds >= 3600*6) {
                        canModify = true;
                    }
                }
            }
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(subDetailOutVo);
            JSONObject detailJsonObject = jsonObject.getJSONObject("detail");
            detailJsonObject.put("canModify", canModify ? 1 : 0);

            restResponse.data(jsonObject);
            return restResponse;
        }

        restResponse.data(subDetailOutVo);
        return restResponse;
    }

    public ObjectRestResponse<JSONArray> getReservationStatusForDay(String productId, String day) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        JSONArray jsonArray = new JSONArray();
        restResponse.data(jsonArray);

        List<Map<String, Object>> list = bizSubProductMapper.getSubProductCountForDay(productId, day);
        int amNum = 0, pmNum = 0;
        for (Map tmpMap : list) {
            String createTime = ((String)tmpMap.get("createTime")).toUpperCase();
            if (createTime.equals("AM")) {
                amNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
            } else if (createTime.equals("PM")) {
                pmNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
            }
        }
        ReservationInfo reservationInfo = bizReservationMapper.selectReservationInfo(productId);
        if (reservationInfo == null) {
            return restResponse;
        }

        boolean amCan = (reservationInfo.getProductNumForenoon() < 0 || reservationInfo.getProductNumForenoon() > amNum) ? true : false;
        boolean pmCan = (reservationInfo.getProductNumAfternoon() < 0 || reservationInfo.getProductNumAfternoon() > pmNum) ? true : false;
        try {
            String forenoonStartTime = StringUtils.isEmpty(reservationInfo.getForenoonStartTime()) ? "08:00:00" : reservationInfo.getForenoonStartTime();
            String forenoonEndTime = StringUtils.isEmpty(reservationInfo.getForenoonEndTime()) ? "12:00:00" : reservationInfo.getForenoonEndTime();
            String afternoonStartTime = StringUtils.isEmpty(reservationInfo.getAfternoonStartTime()) ? "12:00:00" : reservationInfo.getAfternoonStartTime();
            String afternoonEndTime = StringUtils.isEmpty(reservationInfo.getAfternoonEndTime()) ? "18:00:00" : reservationInfo.getAfternoonEndTime();
            String[] tmpArr = forenoonStartTime.split(":");
            forenoonStartTime = tmpArr[0] + (Integer.parseInt(tmpArr[1]) < 30 ? ":00" : ":30") + ":00";
            tmpArr = forenoonEndTime.split(":");
            forenoonEndTime = tmpArr[0] + (Integer.parseInt(tmpArr[1]) < 30 ? ":00" : ":30") + ":00";
            tmpArr = afternoonStartTime.split(":");
            afternoonStartTime = tmpArr[0] + (Integer.parseInt(tmpArr[1]) < 30 ? ":00" : ":30") + ":00";
            tmpArr = afternoonEndTime.split(":");
            afternoonEndTime = tmpArr[0] + (Integer.parseInt(tmpArr[1]) < 30 ? ":00" : ":30") + ":00";
            long forenoonStartTs = DateUtils.parseDateTime(day+" "+forenoonStartTime).getTime();
            long forenoonEndTs = DateUtils.parseDateTime(day+" "+forenoonEndTime).getTime();
            long afternoonStartTs = DateUtils.parseDateTime(day+" "+afternoonStartTime).getTime();
            long afternoonEndTs = DateUtils.parseDateTime(day+" "+afternoonEndTime).getTime();
            long noonTs = DateUtils.parseDateTime(day+" 12:00:00").getTime();
            long tmpTs = forenoonStartTs;
            long minTs = System.currentTimeMillis() + 6*3600000;
            while(tmpTs <= afternoonEndTs) {
                if ((tmpTs >= forenoonStartTs && tmpTs <= forenoonEndTs) || (tmpTs >= afternoonStartTs && tmpTs <= afternoonEndTs)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("time", DateUtils.formatDateTime(new Date(tmpTs), "HH:mm"));
                    if ((tmpTs <= noonTs && amCan) || (tmpTs > noonTs && pmCan)) {
                        if (tmpTs < minTs) {//不能修改为6小时以内
                            jsonObject.put("status", 0);
                        } else {
                            jsonObject.put("status", 1);
                        }
                    } else {
                        jsonObject.put("status", 0);
                    }
                    jsonArray.add(jsonObject);
                }
                tmpTs += 1800000;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return restResponse;
    }

    public ObjectRestResponse<Object> updateSubReservation(String subId, String reservationId, String expectedServiceTime) {
        ObjectRestResponse restResponse = new ObjectRestResponse();

        SubVo detail = bizSubscribeWoMapper.getSubWoDetail(subId);
        List<SubProductInfo> subProductInfoList = bizSubProductMapper.getSubProductInfo(subId);
        if(subProductInfoList==null || subProductInfoList.size()==0){
            restResponse.setStatus(101);
            restResponse.setMessage("没有找到订单产品");
            return restResponse;
        }
        boolean flag = false;
        for (SubProductInfo tmpSubProductInfo : subProductInfoList) {
            if (tmpSubProductInfo.getProductId().equals(reservationId)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            restResponse.setStatus(101);
            restResponse.setMessage("没有找到订单产品");
            return restResponse;
        }

        boolean canModify = false;
        if (detail.getSubStatus().equals("1")) {//处理中
            canModify = true;
        } else if (detail.getSubStatus().equals("5")) {//待确认
            try {
                int seconds = DateUtils.secondBetween(new Date(), DateUtils.parseDateTime(detail.getExpectedServiceTimeStr(),"yyyy-MM-dd HH:mm:ss"));
                if (seconds >= 3600*6) {
                    canModify = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!canModify) {
            restResponse.setStatus(101);
            restResponse.setMessage("订单不能修改");
            return restResponse;
        }

        Date expectedDate = null;
        try {
            expectedDate = DateUtils.parseDateTime(expectedServiceTime,"yyyy-MM-dd HH:mm");
            if (expectedDate.getTime() < System.currentTimeMillis() + 6*3600000) {
                restResponse.setStatus(101);
                restResponse.setMessage("不能预约6小时以内时间");
                return restResponse;
            }
        } catch (ParseException e) {
            restResponse.setStatus(101);
            restResponse.setMessage("参数错误");
            return restResponse;
        }
        String expectedDateStr = DateUtils.dateToString(expectedDate, "yyyy-MM-dd");
        String amPm = DateUtils.dateToString(expectedDate, "a").toUpperCase();
        if (amPm.equals("上午")) {
            amPm = "AM";
        } else if (amPm.equals("下午")) {
            amPm = "PM";
        }
        List<Map<String, Object>> list = bizSubProductMapper.getSubProductCountForDay(reservationId, expectedDateStr);
        int amNum = 0, pmNum = 0;
        for (Map tmpMap : list) {
            String createTime = ((String)tmpMap.get("createTime")).toUpperCase();
            if (createTime.equals("AM")) {
                amNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
            } else if (createTime.equals("PM")) {
                pmNum = Integer.parseInt(((BigDecimal)tmpMap.get("num")).toString());
            }
        }
        ReservationInfo reservationInfo = bizReservationMapper.selectReservationInfo(reservationId);
        if (reservationInfo == null) {
            restResponse.setStatus(101);
            restResponse.setMessage("没有找到订单产品");
            return restResponse;
        }
        canModify = false;
        if (amPm.equals("AM")) {
            canModify = (reservationInfo.getProductNumForenoon() < 0 || reservationInfo.getProductNumForenoon() > amNum) ? true : false;
        } else {
            canModify = (reservationInfo.getProductNumAfternoon() < 0 || reservationInfo.getProductNumAfternoon() > pmNum) ? true : false;
        }
        if (!canModify) {
            restResponse.setStatus(101);
            restResponse.setMessage("该时间已约满");
            return restResponse;
        }

        try {
            String tmpDate = DateUtils.formatDateTime(expectedDate, "yyyy-MM-dd");
            String forenoonStartTime = StringUtils.isEmpty(reservationInfo.getForenoonStartTime()) ? "08:00:00" : reservationInfo.getForenoonStartTime();
            String forenoonEndTime = StringUtils.isEmpty(reservationInfo.getForenoonEndTime()) ? "12:00:00" : reservationInfo.getForenoonEndTime();
            String afternoonStartTime = StringUtils.isEmpty(reservationInfo.getAfternoonStartTime()) ? "12:00:00" : reservationInfo.getAfternoonStartTime();
            String afternoonEndTime = StringUtils.isEmpty(reservationInfo.getAfternoonEndTime()) ? "18:00:00" : reservationInfo.getAfternoonEndTime();

            long forenoonStartTs = DateUtils.parseDateTime(tmpDate+" "+forenoonStartTime).getTime();
            long forenoonEndTs = DateUtils.parseDateTime(tmpDate+" "+forenoonEndTime).getTime();
            long afternoonStartTs = DateUtils.parseDateTime(tmpDate+" "+afternoonStartTime).getTime();
            long afternoonEndTs = DateUtils.parseDateTime(tmpDate+" "+afternoonEndTime).getTime();
            long expectedTs = expectedDate.getTime();
            if (expectedTs < forenoonStartTs || (expectedTs > forenoonEndTs && expectedTs < afternoonStartTs) || expectedTs > afternoonEndTs) {
                restResponse.setStatus(101);
                restResponse.setMessage("该时间不能预约");
                return restResponse;
            }
        } catch (ParseException e) {

        }

        int res = bizSubscribeWoMapper.updateSubReservationExpectTime(subId, expectedDate);
        restResponse.data(res);

        //生成流水日志
        TransactionLogBean transactionLogBean = new TransactionLogBean();
        transactionLogBean.setCurrStep("修改预约时间");
        transactionLogBean.setDesc("修改预约时间成功");
        bizTransactionLogBiz.insertTransactionLog(subId,transactionLogBean);

        //todo 同步金小茂，发推送

        return restResponse;
    }

    public ObjectRestResponse<Object> getSubReservationSpec(String subId, String reservationId) {
        ObjectRestResponse restResponse = new ObjectRestResponse();

        List<SubProductInfo> subProductInfoList = bizSubProductMapper.getSubProductInfo(subId);
        if(subProductInfoList==null || subProductInfoList.size()==0){
            restResponse.setStatus(101);
            restResponse.setMessage("没有找到订单产品");
            return restResponse;
        }
        SubProductInfo subProductInfo = null;
        for (SubProductInfo tmpSubProductInfo : subProductInfoList) {
            if (tmpSubProductInfo.getProductId().equals(reservationId)) {
                subProductInfo = tmpSubProductInfo;
                break;
            }
        }
        if (subProductInfo == null) {
            restResponse.setStatus(101);
            restResponse.setMessage("没有找到订单产品");
            return restResponse;
        }

        ProductSpecCodeVo productSpecCodeVo = bizProductSpecMapper.getSpecInfoById(subProductInfo.getSpecId(), reservationId);
        List<ProductSpecCodeVo> list = new ArrayList<>();
        if (productSpecCodeVo != null) {
            list.add(productSpecCodeVo);
        }

        restResponse.data(list);
        return restResponse;
    }

    public ObjectRestResponse<Object> appendSubReservation(AppendSubReservationParam param) {
        BizReservation reservationInfo = bizReservationMapper.selectByPrimaryKey(param.getReservationId());
        ProductSpecInfo productSpecInfo = bizProductSpecMapper.getReservationSpecInfoById(reservationInfo.getCompanyId(), param.getReservationId(), param.getSpecId());
        BizSubscribe bizSubscribe = bizSubscribeMapper.selectByPrimaryKey(param.getSubId());

        SubInVo subInVo = new SubInVo();
        subInVo.setBusId(BusinessConstant.getReservationBusId());
        subInVo.setDescription(reservationInfo.getName());
        subInVo.setCompanyId(reservationInfo.getCompanyId());
        BigDecimal specInfoPrice = new BigDecimal(productSpecInfo.getPrice());
        BigDecimal productCost = specInfoPrice.multiply(new BigDecimal(param.getSubNum()));
        subInVo.setActualCost(productCost);
        subInVo.setReceivableCost(productCost);
        subInVo.setProductCost(productCost);
        subInVo.setExpressCost(new BigDecimal(0));
        subInVo.setDiscountCost(new BigDecimal(0));
        subInVo.setSource(AppContextHandler.getPlatform().equals(AppContextHandler.PLATFORM_ANDROID) ? "1" : "2");
        subInVo.setProjectId(bizSubscribe.getProjectId());
        subInVo.setContactName(bizSubscribe.getContactName());
        subInVo.setContactTel(bizSubscribe.getContactTel());
        subInVo.setDeliveryAddr(bizSubscribe.getDeliveryAddr());
        subInVo.setUserId(BaseContextHandler.getUserID());
        subInVo.setRemark("");
        subInVo.setTotalNum(Integer.parseInt(param.getSubNum()));
        subInVo.setPrice(new BigDecimal(productSpecInfo.getPrice()));
        subInVo.setUnit(productSpecInfo.getUnit());
        subInVo.setImgId(productSpecInfo.getProductImage());
        subInVo.setInvoiceType(bizSubscribe.getInvoiceType());
        subInVo.setInvoiceName(bizSubscribe.getInvoiceName());
        subInVo.setDutyNum(bizSubscribe.getDutyNum());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        subInVo.setReservationTime(sdf.format(DateTimeUtil.getLocalTime()));

        //返回结果参数
        BuyProductOutVo buyProductOutVo = new BuyProductOutVo();

        ObjectRestResponse<BizSubscribe> restResponse = this.createSubscribe(subInVo);
        if (restResponse.getStatus() == 200) {
            BizSubscribe appendBizSubscribe = restResponse.getData();
            BizSubProduct bizSubProduct = new BizSubProduct();
            bizSubProduct.setId(UUIDUtils.generateUuid());
            bizSubProduct.setSpecId(productSpecInfo.getId());
            bizSubProduct.setSpecName(productSpecInfo.getSpecName());
            bizSubProduct.setProductId(param.getReservationId());
            bizSubProduct.setProductName(productSpecInfo.getProductName()+"-"+productSpecInfo.getSpecName());
            bizSubProduct.setPrice(specInfoPrice);
            bizSubProduct.setUnit(productSpecInfo.getUnit());
            bizSubProduct.setSubNum(Integer.parseInt(param.getSubNum()));
            bizSubProduct.setCost(productCost);
            if (StringUtils.isNotEmpty(productSpecInfo.getSpecImage())) {
                bizSubProduct.setImgId(productSpecInfo.getSpecImage());
            }else{
                bizSubProduct.setImgId(productSpecInfo.getProductImage());
            }
            bizSubProduct.setStatus("1");
            bizSubProduct.setTimeStamp(DateTimeUtil.getLocalTime());
            bizSubProduct.setCreateTime(DateTimeUtil.getLocalTime());
            bizSubProduct.setCreateBy(BaseContextHandler.getUserID());
            bizSubProduct.setSubId(appendBizSubscribe.getId());
            bizSubProductMapper.insert(bizSubProduct);

            //3.维护账单表
            BizAccountBook bizAccountBook = new BizAccountBook();
            bizAccountBook.setId(UUIDUtils.generateUuid());
            bizAccountBook.setPayStatus("1");
            bizAccountBook.setSubId(appendBizSubscribe.getId());
            String atualId = UUIDUtils.generateUuid();
            bizAccountBook.setActualId(atualId);
            bizAccountBook.setActualCost(productCost);
            bizAccountBook.setStatus("1");
            bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
            bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
            bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
            bizAccountBookMapper.insertSelective(bizAccountBook);


            buyProductOutVo.setActualId(atualId);
            buyProductOutVo.setSubId(appendBizSubscribe.getId());
            buyProductOutVo.setActualPrice((productCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
            buyProductOutVo.setTitle(bizSubscribe.getTitle());
        }

        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(buyProductOutVo);
        jsonObject.put("contactName", bizSubscribe.getContactName());
        jsonObject.put("contactTel", bizSubscribe.getContactTel());
        jsonObject.put("addr", bizSubscribe.getDeliveryAddr());

        ObjectRestResponse result = new ObjectRestResponse();
        result.data(jsonObject);
        return result;
    }
    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;
    @Autowired
    private BizReservationOrderBiz bizReservationOrderBiz;
    public ObjectRestResponse<SubDetailOutForWebVo> getSubDetailByWeb(String id) {
        ObjectRestResponse restResponse = new ObjectRestResponse();

        // 小程序商品订单
        restResponse = bizProductOrderBiz.getSubDetailByWeb(id);
        if(restResponse.success()){
            return restResponse;
        }

        // 小程序服务订单
        restResponse = bizReservationOrderBiz.getSubDetailByWeb(id);
        if(restResponse.success()){
            return restResponse;
        }

        restResponse = new ObjectRestResponse();
        SubDetailOutForWebVo subDetailOutVo = new SubDetailOutForWebVo();
        //1.获取订单详情
        SubVo detail = bizSubscribeWoMapper.getSubWoDetail(id);
        if(detail==null){
            restResponse.setStatus(101);
            restResponse.setMessage("该ID获取不到详情");
            return restResponse;
        }
        //2.获取工单操作按钮
        List<OperateButton> operateButtonList = null;
        operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(detail.getProcessId());

        //判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
        String woStatusStr = detail.getWoStatus() == null ? "" : (String) detail.getWoStatus();
        String userId = BaseContextHandler.getUserID();
        if(StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleByUserId())){
            detail.setIsTurn("1");
        }else{
            detail.setIsTurn("0");
        }
        if(operateButtonList==null || operateButtonList.size()==0){
            operateButtonList = new ArrayList<>();
        }
        subDetailOutVo.setOperateButtonList(operateButtonList);

        //2.获取订单产品信息
        List<SubProductInfo> subProductInfoList = bizSubProductMapper.getSubProductInfo(id);
        if(subProductInfoList==null || subProductInfoList.size()==0){
            subProductInfoList = new ArrayList<>();
        }
        detail.setSubProductInfoList(subProductInfoList);
        subDetailOutVo.setDetail(detail);

        //4.获取操作流水日志
        List<TransactionLogVo> transactionLogList = bizTransactionLogBiz.selectTransactionLogListById(id);
        if(transactionLogList==null && transactionLogList.size()==0){
            transactionLogList = new ArrayList<>();
        }
        subDetailOutVo.setTransactionLogList(transactionLogList);

        //判断能否修改订单 PRD201911260008
        boolean canModify = false;
        if (detail.getWoType() != null && detail.getWoType().equals("4")) {
            if (detail.getSubStatus().equals("1")) {//处理中
                canModify = true;
            } else if (detail.getSubStatus().equals("5")) {//待确认
                try {
                    int seconds = DateUtils.secondBetween(new Date(), DateUtils.parseDateTime(detail.getExpectedServiceTimeStr(),"yyyy-MM-dd HH:mm:ss"));
                    if (seconds >= 3600*6) {
                        canModify = true;
                    }
                } catch (ParseException e) {

                }
            }
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(subDetailOutVo);
        JSONObject detailJsonObject = jsonObject.getJSONObject("detail");
        detailJsonObject.put("canModify", canModify ? 1 : 0);

        restResponse.data(jsonObject);
        return restResponse;
//        restResponse.data(subDetailOutVo);
//        return restResponse;
    }

    public ObjectRestResponse<List<SubListForWebVo>> querySubListByWeb(List<String> busIdList, SearchSubInWeb searchSubInWeb){
        ObjectRestResponse restResponse = new ObjectRestResponse();
        int page = searchSubInWeb.getPage();
        int limit = searchSubInWeb.getLimit();
        if (page<0) {
            page = 1;
        }
        if (limit<0) {
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
        paramMap.put("searchVal",searchSubInWeb.getSearchVal());
        paramMap.put("startDate",searchSubInWeb.getStartDate());
        paramMap.put("endDate",searchSubInWeb.getEndDate());
        paramMap.put("projectId",searchSubInWeb.getProjectId());
        paramMap.put("companyId",searchSubInWeb.getCompanyId());
        paramMap.put("subStatus",searchSubInWeb.getSubStatus());
        List<SubListForWebVo> woList = bizSubscribeMapper.querySubListByWeb(paramMap);
        int total = 0;
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }else{
            total = bizSubscribeMapper.querySubListByWebTotal(paramMap);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",woList);

        restResponse.setData(map);
        return restResponse;
    }

    /**
     * 校验下单提前数据的正确性
     * @param companyProductList
     * @return
     */
    private ObjectRestResponse<List<BuyProductInfoTwo>> checkCompanyProductData(List<CompanyProduct> companyProductList,String busId,String procCode){
        ObjectRestResponse result = new ObjectRestResponse();
        List<BuyProductInfoTwo> buyProductInfoTwoList = new ArrayList<>();
        BuyProductInfoTwo buyProductInfoTwo = null;
        BizSubProduct bizSubProduct = null;
        String userId = BaseContextHandler.getUserID();
        for (CompanyProduct companyProduct : companyProductList) {
            buyProductInfoTwo = new BuyProductInfoTwo();
            List<BizSubProduct> bizSubProductList = new ArrayList<>();
            List<String> shoppingCartIdList = new ArrayList<>();
            //公司商品总额
            BigDecimal companyProductCost = new BigDecimal(0);
            int companySubNum = 0;
            StringBuffer specImgIdBuffer = new StringBuffer();
            StringBuffer specNameBuffer = new StringBuffer();

            PostalAddressDeliveryOut postalAddressDeliveryOut = bizPostalAddressMapper.getNoDeliveryAddress(companyProduct.getCompanyId(),procCode);
            if(postalAddressDeliveryOut!=null){
                result.setStatus(101);
                result.setMessage("该地区商品无货");
                return result;
            }

            List<SubProduct> subProductList = companyProduct.getSubProductList();
            if (subProductList != null && subProductList.size() > 0) {
                for (SubProduct subProduct : subProductList) {
                    //根据产品和规格ID查询出产品信息
                    ProductSpecInfo productSpecInfo = null;
                    if(BusinessConstant.getReservationBusId().equals(busId)){
                        //预约服务规格
                        productSpecInfo = bizProductSpecMapper.getReservationSpecInfoById(companyProduct.getCompanyId(), subProduct.getProductId(), subProduct.getSpecId());
                    }else {
                        //优选，拼团，疯抢
                        productSpecInfo = bizProductSpecMapper.selectSpecInfoById(companyProduct.getCompanyId(), subProduct.getProductId(), subProduct.getSpecId());
                    }
                    if (productSpecInfo != null) {
                        if(busId.equals(BusinessConstant.getGroupBuyingBusId())){
                            //团购业务
                            result = checkGroupBuyTime(productSpecInfo);
                            if(result==null || result.getStatus()!=200){
                                return result;
                            }
                        }
                        BigDecimal specInfoPrice = new BigDecimal(productSpecInfo.getPrice());
                        BigDecimal productCost = specInfoPrice.multiply(new BigDecimal(subProduct.getSubNum()));
                        companyProductCost = companyProductCost.add(productCost);
                        companySubNum += subProduct.getSubNum();
                        if (StringUtils.isNotEmpty(productSpecInfo.getSpecImage())) {
                            specImgIdBuffer.append(",").append(productSpecInfo.getSpecImage());
                        }else{
                            specImgIdBuffer.append(",").append(productSpecInfo.getProductImage());
                        }
                        if (StringUtils.isNotEmpty(productSpecInfo.getProductName())) {
                            specNameBuffer.append(",").append(productSpecInfo.getProductName());
                        }
                        bizSubProduct = new BizSubProduct();
                        bizSubProduct.setId(UUIDUtils.generateUuid());
                        bizSubProduct.setSpecId(productSpecInfo.getId());
                        bizSubProduct.setSpecName(productSpecInfo.getSpecName());
                        bizSubProduct.setProductId(subProduct.getProductId());
                        bizSubProduct.setProductName(productSpecInfo.getProductName()+"-"+productSpecInfo.getSpecName());
                        bizSubProduct.setPrice(specInfoPrice);
                        bizSubProduct.setUnit(productSpecInfo.getUnit());
                        bizSubProduct.setSubNum(subProduct.getSubNum());
                        bizSubProduct.setCost(productCost);
                        if (StringUtils.isNotEmpty(productSpecInfo.getSpecImage())) {
                            bizSubProduct.setImgId(productSpecInfo.getSpecImage());
                        }else{
                            bizSubProduct.setImgId(productSpecInfo.getProductImage());
                        }
                        bizSubProduct.setStatus("1");
                        bizSubProduct.setTimeStamp(DateTimeUtil.getLocalTime());
                        bizSubProduct.setCreateTime(DateTimeUtil.getLocalTime());
                        bizSubProduct.setCreateBy(userId);
                        bizSubProductList.add(bizSubProduct);
                    } else {
                        result.setStatus(101);
                        result.setMessage("查询商品规格信息为空");
                        return result;
                    }
                    //维护购物车id
                    if (StringUtils.isNotEmpty(subProduct.getShoppingCartId())) {
                        shoppingCartIdList.add(subProduct.getShoppingCartId());
                    }
                }
            } else {
                result.setStatus(101);
                result.setMessage("产品参数为空");
                return result;
            }
            //设置值
            buyProductInfoTwo.setBizSubProductList(bizSubProductList);
            buyProductInfoTwo.setShoppingCartIdList(shoppingCartIdList);
            if(specImgIdBuffer.length()>0){
                buyProductInfoTwo.setImgId(specImgIdBuffer.substring(1));
            }else{
                buyProductInfoTwo.setImgId(specImgIdBuffer.toString());
            }
            if(specNameBuffer.length()>0){
                buyProductInfoTwo.setDescription(specNameBuffer.substring(1));
            }else{
                buyProductInfoTwo.setDescription(specNameBuffer.toString());
            }
            buyProductInfoTwo.setProductCost(companyProductCost);
            buyProductInfoTwo.setDiscountCost(new BigDecimal(0));
            buyProductInfoTwo.setExpressCost(new BigDecimal(0));
            buyProductInfoTwo.setSubNum(companySubNum);
            buyProductInfoTwo.setCompanyId(companyProduct.getCompanyId());
            buyProductInfoTwo.setRemark(companyProduct.getRemark());
            buyProductInfoTwo.setInvoiceType(companyProduct.getInvoiceType());
            buyProductInfoTwo.setInvoiceName(companyProduct.getInvoiceName());
            buyProductInfoTwo.setDutyNum(companyProduct.getDutyNum());
            buyProductInfoTwo.setCouponId(companyProduct.getCouponId());
            buyProductInfoTwoList.add(buyProductInfoTwo);
            //获取优惠金额
            if(StringUtils.isNotEmpty(buyProductInfoTwo.getCouponId())) {
                ObjectRestResponse discountRestResponse = bizCouponBiz.getDiscountPrice(buyProductInfoTwo.getCompanyId(), buyProductInfoTwo.getProductCost(), companyProduct.getSubProductList(), buyProductInfoTwo.getCouponId(), userId);
                if (discountRestResponse != null && discountRestResponse.getStatus() == 200) {
                    buyProductInfoTwo.setDiscountCost(discountRestResponse.getData() == null ? new BigDecimal(0) : (BigDecimal) discountRestResponse.getData());
                }
            }
            //获取运费
            if(!BusinessConstant.getReservationBusId().equals(busId)){
                PostageInfo postageInfo = bizCouponBiz.getPostageList(buyProductInfoTwo.getCompanyId(),buyProductInfoTwo.getProductCost());
                if(postageInfo!=null && postageInfo.getPostage()!=null){
                    buyProductInfoTwo.setExpressCost(postageInfo.getPostage());
                }
            }
            //实收金额=商品总金额+运费-优惠金额
            buyProductInfoTwo.setActualCost(buyProductInfoTwo.getProductCost().add(buyProductInfoTwo.getExpressCost()).subtract(buyProductInfoTwo.getDiscountCost()));
        }
        result.setData(buyProductInfoTwoList);
        return result;
    }

    /**
     * 团购产品下单时间校验
     * @param productSpecInfo
     * @return
     */
    private ObjectRestResponse checkGroupBuyTime(ProductSpecInfo productSpecInfo){
        ObjectRestResponse result = new ObjectRestResponse();
        if(productSpecInfo.getBegTime()==null || productSpecInfo.getEndTime()==null){
            result.setStatus(104);
            result.setMessage("团购起始时间设置错误");
            return result;
        }
        if (new Date().before(productSpecInfo.getBegTime())) {
            result.setStatus(104);
            result.setMessage("团购还未开始");
            return result;
        }
        if (new Date().after(productSpecInfo.getEndTime())) {
            result.setStatus(104);
            result.setMessage("团购已结束");
            return result;
        }
        return result;
    }

}