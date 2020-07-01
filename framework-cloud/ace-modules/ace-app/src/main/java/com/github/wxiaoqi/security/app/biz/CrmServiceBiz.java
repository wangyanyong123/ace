package com.github.wxiaoqi.security.app.biz;


import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.config.FaceConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizPropertyPayLog;
import com.github.wxiaoqi.security.app.entity.BizSubscribe;
import com.github.wxiaoqi.security.app.entity.BizWo;
import com.github.wxiaoqi.security.app.fegin.LogService;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.crm.in.*;
import com.github.wxiaoqi.security.app.vo.crm.out.SyncWorkOrderStateRespose;
import com.github.wxiaoqi.security.app.vo.face.in.CheckFaceResponse;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.out.*;
import com.github.wxiaoqi.security.auth.client.jwt.MD5;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/***
 * 与CRM对接的接口调用
 */
@Service
@Slf4j
public class CrmServiceBiz {


	@Autowired
	private CrmConfig crmConfig;
	@Autowired
	private BizSubPropertyFeeMapper bizSubPropertyFeeMapper;
	@Autowired
	private BizSubscribeMapper bizSubscribeMapper;
	@Autowired
	private BizPropertyPayLogMapper bizPropertyPayLogMapper;
    @Autowired
    private ZhongTaiServiceBiz zhongTaiServiceBiz;
    @Autowired
    private CrmOldServiceBiz crmOldServiceBiz;
    @Autowired
    private BaseAppClientUserBiz clientUserBiz;
    @Autowired
    private QrProvideFegin qrProvideFegin;
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;
    @Autowired
    private SaveLogBiz saveLogBiz;
    @Autowired
    private BizCrmHouseMapper bizCrmHouseMapper;
    @Autowired
    private LogService logService;
    private static Config config = ConfigService.getConfig("ace-app");

    @Autowired
    private BizWoMapper bizWoMapper;

    private final String CRM = "1";

    private final String ZHONGTAI = "2";
    /**
	 * 报修/投诉工单同步接口
	 * @param syncWorkOrderStateIn
	 * @return
	 */
	public ObjectRestResponse syncWorkOrderState(SyncWorkOrderStateIn syncWorkOrderStateIn) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		if(!"true".equals(crmConfig.getWoOpenFlag())){
			restResponse.setStatus(201);
			restResponse.setMessage("未开放同步到CRM系统服务");
			return restResponse;
		}
		if( StringUtils.isEmpty(syncWorkOrderStateIn.getProjectId()) || StringUtils.isEmpty(crmConfig.getProjectIds()) ||
				crmConfig.getProjectIds().indexOf(syncWorkOrderStateIn.getProjectId())==-1){
			restResponse.setStatus(201);
			restResponse.setMessage("未开放同步到CRM系统服务项目"+crmConfig.getProjectIds());
			log.info("工单id{},项目id:{},配置开放的项目列表:{}",syncWorkOrderStateIn.getWoId(),syncWorkOrderStateIn.getProjectId(),crmConfig.getProjectIds());
			return restResponse;
		}

        if(syncWorkOrderStateIn!=null){
            //0.同步前同步状态设置为2-同步失败订单状态表
            BizWo bizWo = new BizWo();
            bizWo.setId(syncWorkOrderStateIn.getWoId());
            bizWo.setCrmSyncFlag("2");
            bizWoMapper.updateByPrimaryKeySelective(bizWo);
        }

		SyncWorkOrderStateBody syncWorkOrderStateBody = new SyncWorkOrderStateBody();
		BeanUtils.copyProperties(syncWorkOrderStateIn,syncWorkOrderStateBody);
		if(StringUtils.isNotEmpty(syncWorkOrderStateIn.getFileList())){
			syncWorkOrderStateBody.setFileList(encoder(syncWorkOrderStateIn.getFileList()));
		}
		if(StringUtils.isNotEmpty(syncWorkOrderStateIn.getServiceFileList())){
			syncWorkOrderStateBody.setServiceFileList(encoder(syncWorkOrderStateIn.getServiceFileList()));
		}
		//状态翻译
        //CRM:01-已创建；03-处理中；05-已完成； 07-已废弃
        //金小茂:00-待系统受理,01-待接受,02-已接受,03-处理中,04-暂停中,05-已完成,06-已取消,07-已取消
        if("03".equals(syncWorkOrderStateBody.getWoStatus())){
            syncWorkOrderStateBody.setWoStatus("03");
        }else if("05".equals(syncWorkOrderStateBody.getWoStatus())){
            syncWorkOrderStateBody.setWoStatus("05");
        }else if("06".equals(syncWorkOrderStateBody.getWoStatus()) || "07".equals(syncWorkOrderStateBody.getWoStatus())){
            syncWorkOrderStateBody.setWoStatus("07");
        }else if("00".equals(syncWorkOrderStateBody.getWoStatus()) || "01".equals(syncWorkOrderStateBody.getWoStatus())
                || "02".equals(syncWorkOrderStateBody.getWoStatus())){
            syncWorkOrderStateBody.setWoStatus("01");
        }
		if("1".equals(syncWorkOrderStateBody.getType())){
			//投诉
			syncWorkOrderStateBody.setFirstClassifyCode("");
			syncWorkOrderStateBody.setSecondClassifyCode("");
			syncWorkOrderStateBody.setThirdClassifyCode("");
			syncWorkOrderStateBody.setDealName("");
		}else if("2".equals(syncWorkOrderStateBody.getType())){
			//报修
			syncWorkOrderStateBody.setComplaintClassifyCode("");
		}
        ObjectRestResponse response = syncWoToCrm(syncWorkOrderStateBody);
		return response;
	}

    /**
     * 3.报修/投诉同步接口
     * @return
     */
    public ObjectRestResponse syncWoToCrm(SyncWorkOrderStateBody syncWorkOrderStateBody) {
        ObjectRestResponse response = new ObjectRestResponse();
        ResponseEntity<String> responseEntity = null;
        responseEntity = crmOldServiceBiz.syncWoToCrm(syncWorkOrderStateBody);
        if (responseEntity != null) {
            if(200 == responseEntity.getStatusCodeValue()){
                String strbody=responseEntity.getBody();
                SyncWorkOrderStateRespose result= (SyncWorkOrderStateRespose)JSONObject.toBean(JSONObject.fromObject(strbody), SyncWorkOrderStateRespose.class);
                if(!"000".equals(result.getCode())) {
                    response.setStatus(101);
                    response.setMessage(result.getDescribe());
                    saveLogBiz.saveLog(responseEntity,"报修/投诉同步接口");
                }
            }else if (responseEntity.getStatusCodeValue() == 408) {
                response.setStatus(101);
                response.setMessage("请求超时");
                return response;
            }
        }else {
            response.setStatus(511);
            response.setMessage("请求CRM失败");
        }
        return response;
    }
//	/**
//	 * crm同步工单处理方法
//	 * @return
//	 */
//	public ObjectRestResponse syncWoToCrm(SyncWorkOrderStateBody syncWorkOrderStateBody) {
//		ObjectRestResponse response = new ObjectRestResponse();
//		String params = "api/SyncWorkOrderState";
//		JSONObject jsonObj = JSONObject.fromObject(syncWorkOrderStateBody);
//		ResponseEntity<String> responseEntity = crmAuthService(params, jsonObj, HttpMethod.POST);
//		if (responseEntity != null) {
//			log.info("请求crm系统，报修/投诉工单同步接口响应的结果:"+responseEntity.toString());
//			if(200 == responseEntity.getStatusCodeValue()){
//				String strbody=responseEntity.getBody();
//				SyncWorkOrderStateRespose result= (SyncWorkOrderStateRespose)JSONObject.toBean(JSONObject.fromObject(strbody), SyncWorkOrderStateRespose.class);
//				if(!"000".equals(result.getCode())) {
//					response.setStatus(101);
//					response.setMessage(result.getDescribe());
//				}
//			}
//		}else {
//			response.setStatus(101);
//			response.setMessage("请求超时");
//		}
//		return response;
//	}
//	/**
//	 * 中台工单处理方法
//	 * @return
//	 */
//	public ObjectRestResponse syncWoToZhongtai() {
//		ObjectRestResponse response = new ObjectRestResponse();
//		String params = "";
//		ResponseEntity<String> responseEntity = zhongTaiServiceBiz.zhongTaiAuthService(params, null, null, HttpMethod.POST);
//		log.info("请求中台系统，报修/投诉工单同步接口响应的结果:"+responseEntity.toString());
//		if (responseEntity != null) {
//			if(200 == responseEntity.getStatusCodeValue()){
//				String strbody=responseEntity.getBody();
//				SyncWorkOrderStateRespose result= (SyncWorkOrderStateRespose)JSONObject.toBean(JSONObject.fromObject(strbody), SyncWorkOrderStateRespose.class);
//				if(!"1000".equals(result.getCode())) {
//					response.setStatus(101);
//					response.setMessage(result.getDescribe());
//				}
//			}
//		}else {
//			response.setStatus(101);
//			response.setMessage("请求超时");
//		}
//		return response;
//	}

    /**
     * 同步物业缴费
     * @return
     */
    public ObjectRestResponse syncPropertyStatus(BizSubscribe bizSubscribe) {
        //获取CRM对应编码
        BizSubscribe subscribe = bizSubscribeMapper.selectByPrimaryKey(bizSubscribe.getId());
        //获取支付信息
        AccountBook accountBook = bizSubPropertyFeeMapper.getBillAccountBook(bizSubscribe.getId());
        //crm接口
        ObjectRestResponse response = syncBillToCrm(subscribe, accountBook);
        if (response.getStatus() == 200) {
            log.info("请求CRM成功");
            //记录状态 通知CRM成功
            BizSubscribe sub = new BizSubscribe();
            sub.setId(bizSubscribe.getId());
            sub.setNoticeStatus("1");
            bizSubscribeMapper.updateByPrimaryKeySelective(sub);
            //记录支付日志
            BizPropertyPayLog bizPropertyPayLog = new BizPropertyPayLog();
            String id = UUIDUtils.generateUuid();
            bizPropertyPayLog.setId(id);
            bizPropertyPayLog.setSubId(bizSubscribe.getId());
            //记录通知情况
            bizPropertyPayLog.setNoticeDescribe("请求状态:"+response.getStatus()+"描述:"+response.getMessage());
            bizPropertyPayLog.setUserId(subscribe.getUserId());
            bizPropertyPayLog.setChargeTime(accountBook.getChargeTime());
            bizPropertyPayLog.setPayAmount(new BigDecimal(accountBook.getPayAmount()));
            bizPropertyPayLog.setPayType(accountBook.getPayType());
            bizPropertyPayLog.setCreateBy(subscribe.getUserId());
            bizPropertyPayLog.setCreateTime(new Date());
            bizPropertyPayLogMapper.insertSelective(bizPropertyPayLog);
        }else {
            response.setStatus(101);
            response.setMessage("同步失败，请求超时");
            //同步订单请求状态
            BizSubscribe sub = new BizSubscribe();
            sub.setId(bizSubscribe.getId());
            sub.setNoticeStatus("2");
            bizSubscribeMapper.updateByPrimaryKeySelective(sub);
            log.info("记录支付日志 ");
            //记录支付日志
            BizPropertyPayLog bizPropertyPayLog = new BizPropertyPayLog();
            String id = UUIDUtils.generateUuid();
            bizPropertyPayLog.setId(id);
            bizPropertyPayLog.setSubId(bizSubscribe.getId());
            //记录通知情况
            bizPropertyPayLog.setNoticeDescribe("请求状态:"+response.getStatus()+"描述:"+response.getMessage());
            bizPropertyPayLog.setUserId(subscribe.getUserId());
            bizPropertyPayLog.setChargeTime(accountBook.getChargeTime());
            bizPropertyPayLog.setPayAmount(new BigDecimal(accountBook.getPayAmount()));
            bizPropertyPayLog.setPayType(accountBook.getPayType());
            bizPropertyPayLog.setCreateBy(subscribe.getUserId());
            bizPropertyPayLog.setCreateTime(new Date());
            bizPropertyPayLogMapper.insertSelective(bizPropertyPayLog);
            return response;
        }
        return response;

    }


    /**
     * 4.账单查询接口
     * @param user
     * @param houseInfo
     * @return
     */
    public ObjectRestResponse getNoBillFromCrm(BaseAppClientUser user, HouseAllInfoVo houseInfo,String ChargeItemCode){
        ObjectRestResponse response = new ObjectRestResponse();
        ResponseEntity<String> responseEntity = null;
        String serverType = config.getProperty(CrmConfig.ApolloKey.SERVER_TYPE.toString(),"");
        if (CRM.equals(serverType)) {
            responseEntity = crmOldServiceBiz.getBillFromOldCrm(user, houseInfo);
        }else if (ZHONGTAI.equals(serverType)){
            responseEntity = zhongTaiServiceBiz.getBillFromZhongTai(user, houseInfo,ChargeItemCode);
        }
        if (responseEntity != null) {
            if (responseEntity.getStatusCodeValue() == 200) {
                String body = responseEntity.getBody();
                if (CRM.equals(serverType)) {
                    UserBillResponse userBillResponse = com.alibaba.fastjson.JSONObject.parseObject(body, UserBillResponse.class);
                    if ("000".equals(userBillResponse.getCode())) {
                        response.setData(userBillResponse.getData());
                        response.setStatus(200);
                        return response;
                    }else {
                        log.error("请求CRM系统返回提示失败，返回结果{}",userBillResponse.toString());
                        response.setStatus(Integer.valueOf(userBillResponse.getCode()));
                        response.setMessage(userBillResponse.getDescribe());
                        saveLogBiz.saveLog(responseEntity,"账单查询接口");
                        return response;
                    }
                }else if (ZHONGTAI.equals(serverType)){
                    List<ShouldBillOut> data = new ArrayList<>();
                    NewBillResponse newBillResponse = com.alibaba.fastjson.JSONObject.parseObject(body, NewBillResponse.class);
                    if ("1000".equals(newBillResponse.getCode())) {
                        BillsInfo billsInfo =  newBillResponse.getData();
                        for (ArrearsInfoList arrearsInfo : billsInfo.getArrearsInfoList()) {
                            ShouldBillOut shouldBillOut = new ShouldBillOut();
                            if ("100000000".equals(ChargeItemCode)) {
                                shouldBillOut.setItem("1");
                                shouldBillOut.setItemStr("物业管理费");
                            }else {
                                shouldBillOut.setItem("2");
                                shouldBillOut.setItemStr("车位费");
                            }
                            shouldBillOut.setShouldDate(arrearsInfo.getAccountPeriod());
                            shouldBillOut.setShouldAmount(arrearsInfo.getArrearsAmount());
                            shouldBillOut.setShouldId(arrearsInfo.getAccountReceivableId());
                            shouldBillOut.setChargeStatus(arrearsInfo.getChargeStatus());
                            shouldBillOut.setArrearsAmount(arrearsInfo.getArrearsAmount());
                            shouldBillOut.setReceivableAmount(arrearsInfo.getChargeAmount());
                            if (arrearsInfo.getChargeDate() != null) {
                                String payDate = DateUtils.dateToString(arrearsInfo.getChargeDate(), DateUtils.DATETIME_FORMAT);
                                shouldBillOut.setPayDate(payDate);
                            }
                            data.add(shouldBillOut);
                        }
                        response.setData(data);
                        response.setStatus(200);
                        return response;
                    }else {
                        log.error("请求中台返回提示失败，返回结果{}",newBillResponse.toString());
                        response.setStatus(Integer.valueOf(newBillResponse.getCode()));
                        response.setMessage(newBillResponse.getMsg());
                        saveLogBiz.saveLog(responseEntity,"账单查询接口");
                        return response;
                    }
                }
            }else if (responseEntity.getStatusCodeValue() == 408) {
                response.setStatus(101);
                response.setMessage("请求超时");
                return response;
            } else if (responseEntity.getStatusCodeValue() == 415) {
                response.setStatus(415);
                response.setMessage("不支持的类型");
                return response;
            }
        } else {
            response.setStatus(511);
            response.setMessage("请求CRM系统失败");
        }
        return response;
    }

    /**
     * 5.缴费接口
     * @param bizSubscribe
     * @param accountBook
     * @return
     */
    public ObjectRestResponse syncBillToCrm(BizSubscribe bizSubscribe,AccountBook accountBook) {
        ObjectRestResponse response = new ObjectRestResponse();
        ResponseEntity<String> responseEntity = null;
		String serverType = config.getProperty(CrmConfig.ApolloKey.SERVER_TYPE.toString(),"");
 //       String serverType = "1";
        if (CRM.equals(serverType)) {
            responseEntity = crmOldServiceBiz.syncBillToCrm(bizSubscribe, accountBook);
            if (responseEntity != null) {
                if (responseEntity.getStatusCodeValue() == 200) {
                    String body = responseEntity.getBody();
                    UserBillResponse result = com.alibaba.fastjson.JSONObject.parseObject(body, UserBillResponse.class);
                    if (!"000".equals(result.getCode())) {
                        response.setStatus(101);
                        response.setMessage(result.getDescribe());
                        saveLogBiz.saveLog(responseEntity,"缴费接口");
                    }
                }else if (responseEntity.getStatusCodeValue() == 408) {
                    response.setStatus(101);
                    response.setMessage("请求超时");
                    return response;
                }
            } else {
                response.setStatus(511);
                response.setMessage("请求crm系统失败");
            }
        } else if (ZHONGTAI.equals(serverType)) {
            ObjectRestResponse restResponse = zhongTaiServiceBiz.syncBillToZhongTai(bizSubscribe, accountBook);
            if(restResponse!=null && restResponse.getStatus()==200){
                UserBillResponse result = com.alibaba.fastjson.JSONObject.parseObject(restResponse.getMessage(), UserBillResponse.class);
                if (!"1000".equals(result.getCode())) {
                    response.setStatus(101);
                    response.setMessage(result.getDescribe());
                    saveLogBiz.saveLog(result.getDescribe(),"缴费接口");
                }
            }else{
                response.setStatus(511);
                response.setMessage("请求crm系统失败");
            }
        }

        return response;
    }

    /**
     * 6.业主校验接口
     * @param userName
     * @param telphone
     * @param houseId
     * @return
     */
    public ObjectRestResponse authUserHouseFromCrm(String userName,String telphone,String houseId) {
        ObjectRestResponse response = new ObjectRestResponse();
        ResponseEntity<String> responseEntity = null;
        String serverType = config.getProperty(CrmConfig.ApolloKey.SERVER_TYPE.toString(),"2");
        if (CRM.equals(serverType)) {
            responseEntity = crmOldServiceBiz.authUserFromCrm(userName,telphone,houseId);
            if (responseEntity !=null){
                log.info("中台-业主认证接口响应的结果:"+responseEntity.toString());
                if(200 == responseEntity.getStatusCodeValue()){
                    String strbody=responseEntity.getBody();
                    CheckMemberRespose checkMemberRespose = com.alibaba.fastjson.JSONObject.parseObject(strbody, CheckMemberRespose.class);
                    if ("000".equals(checkMemberRespose.getCode())) {
                        response.setData(checkMemberRespose.getData());
                        response.setStatus(200);
                        return response;
                    }else {
                        log.error("请求crm返回结果提示失败！返回结果{}",responseEntity.toString());
                        saveLogBiz.saveLog(responseEntity,"CRM-业主认证接口");
                        response.setStatus(512);
                        response.setMessage(checkMemberRespose.getDescribe());
                        return response;
                    }
                } else if (responseEntity.getStatusCodeValue() == 408) {
                    response.setStatus(101);
                    response.setMessage("请求超时");
                    return response;
                } else {
                    log.error("请求失败！返回结果{}", responseEntity.toString());
                    response.setStatus(511);
                    response.setMessage("请求失败！");
                    saveLogBiz.saveLog(responseEntity,"业主认证接口");
                    return response;
                }
            }else {
                response.setStatus(511);
                response.setMessage("请求失败");
            }
        } else if (ZHONGTAI.equals(serverType)) {
            //RecordHouseCode、PhoneNumber、OwnerName
            HouseAllInfoVo houseInfo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(houseId);
            response = zhongTaiServiceBiz.authUserFromZhongtai(userName,telphone,houseInfo.getCrmHouseCode());
            if (response !=null){
                log.info("中台-业主认证接口响应的结果:"+response.toString());
                if(200 == response.getStatus()){
                    String strbody=response.getMessage();
                    ZhongtaiMemberResponse zhongtaiMemberResponse= com.alibaba.fastjson.JSONObject.parseObject(strbody, ZhongtaiMemberResponse.class);
                    if ("1000".equals(zhongtaiMemberResponse.getCode())) {
                        CheckMemberBody data = zhongtaiMemberResponse.getData().get(0);
                        response.setData(data);
                        response.setStatus(200);
                        return response;
                    }else {
                        log.error("请求中台返回结果提示失败！返回结果{}",response.toString());
                        //saveLogBiz.saveLog(responseEntity,"中台-业主认证接口");
                        response.setStatus(512);
                        response.setMessage(zhongtaiMemberResponse.getMsg());
                        return response;
                    }
                }else {
                    log.error("请求失败！返回结果{}", response.toString());
                    response.setStatus(511);
                    response.setMessage("请求失败！");
                    //saveLogBiz.saveLog(responseEntity,"业主认证接口");
                    return response;
                }
            }else {
                response.setStatus(511);
                response.setMessage("请求失败");
            }
        }

        return response;
    }

    private List<Map<String,String>> encoder(String imagesUrls){
        List<Map<String,String>> imagesList = new ArrayList<Map<String,String>>();
        // 对字节数组Base64编码
        if(StringUtils.isNotEmpty(imagesUrls)) {
            String[] imgURLArray = imagesUrls.split(",");
            Map<String,String> imagesMap = null;
            for (int i = 0; i < imgURLArray.length; i++) {
                imagesMap = new HashMap<>();
                imagesMap.put("filebinary","");
                imagesMap.put("fileId",imgURLArray[i]);
                imagesList.add(imagesMap);
            }
        }
        return imagesList;
    }

    @Autowired
    private FaceConfig faceConfig;
    @Autowired
    private RestTemplate restTemplate;

    public ObjectRestResponse send(String url,Map<String,Object> param,String timestamp) {
        ObjectRestResponse response = new ObjectRestResponse();
        String appId = faceConfig.getAppId();
        String appSecret = faceConfig.getAppSecret();
        appSecret = "appKey=" + appSecret;
        String sign = Base64Utils.encodeToString(MD5.getSign(param,appSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("appId",appId);
        headers.add("sign",sign);
        headers.add("timestamp",timestamp);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> entity = new HttpEntity<Object>(param,headers);
        log.info("请求人脸厂商地址：{}，参数{}", url,param.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if (200 == responseEntity.getStatusCodeValue()) {
            String strbody = responseEntity.getBody();
            CheckFaceResponse checkFaceResponse = com.alibaba.fastjson.JSONObject.parseObject(strbody, CheckFaceResponse.class);
            if ("000".equals(checkFaceResponse.getCode())) {
                log.info("请求人脸厂商成功！返回结果{}", checkFaceResponse.toString());
                response.setData(checkFaceResponse);
            } else {
                log.error("请求人脸厂商返回结果提示失败！返回结果{}", checkFaceResponse.toString());
                response.setStatus(Integer.valueOf(checkFaceResponse.getCode()));
                response.setMessage(checkFaceResponse.getDescribe());
                return response;
            }
        } else {
            log.error("请求人脸厂商失败！返回结果{}", responseEntity.toString());
            response.setStatus(500);
            response.setMessage("请求人脸厂商失败！");
            return response;
        }
        return response;
    }
}
