package com.github.wxiaoqi.security.app.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizSubscribe;
import com.github.wxiaoqi.security.app.mapper.BizPropertyPayLogMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubPropertyFeeMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeMapper;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.propertybill.ChargeInfoList;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.out.AccountBook;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DESUtil;
import com.github.wxiaoqi.security.common.util.HmacSha1Util;
import com.github.wxiaoqi.security.common.util.HttpClientUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ZhongTaiServiceBiz {

    @Autowired
    private CrmConfig crmConfig;
    @Autowired
    private BizSubPropertyFeeMapper bizSubPropertyFeeMapper;
    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;
    @Autowired
    private BizPropertyPayLogMapper bizPropertyPayLogMapper;
    @Autowired
    private SaveLogBiz saveLogBiz;
    private static Config config = ConfigService.getConfig("ace-app");

    /**
     * 中台接口鉴权方法
     * @param urlParams
     * @param jsonObject
     * @return
     */
    public ResponseEntity<String> zhongTaiAuthService(String urlParams, JSONObject jsonObject, Map<String, Object> map, HttpMethod httpMethod) {
        String zhongtai_url = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_URL.toString(), "");
        String zhongtai_access_key = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_ACCESS_KEY.toString(), "");
        String zhongtai_access_secret = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_ACCESS_SECRET.toString(), "");
        String dat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String ran = UUID.randomUUID().toString().toUpperCase();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String accesskey = zhongtai_access_key.toUpperCase();
//        String accesskey = crmConfig.getZhongtaiAccessKey().toUpperCase();
        headers.set("accessKey",accesskey);
        headers.set("dateTime",dat);
        headers.set("randomNumbers",ran);
        //签名生成：Base64（HMAC（accessSecret，value））
        String signature = "";
        HttpEntity<String> entity = null;
        String urlStr = zhongtai_url + urlParams;
//        String urlStr = crmConfig.getZhongtaiUrl() + urlParams;
        if (jsonObject == null) {
//            try {
//				map.put("accessKey", URLEncoder.encode(zhongtai_access_key,"UTF-8").toUpperCase());
////                map.put("accessKey", URLEncoder.encode(crmConfig.getZhongtaiAccessKey(),"UTF-8").toUpperCase());
//                map.put("dateTime", URLEncoder.encode(dat,"UTF-8").toUpperCase());
//                map.put("randomNumbers", URLEncoder.encode(ran,"UTF-8").toUpperCase());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                log.info("通用参数encode转换异常");
//            }
//            ArrayList<String> list = new ArrayList<String>();
//            //通用参数排序
//            for(Map.Entry<String,Object> entry:map.entrySet()){
//                if(entry.getKey()!=""){
//                    if (entry.getValue() instanceof String){
//                        list.add(entry.getKey() + "=" + entry.getValue() + "&");
//                    }else {
//                        String value = (entry.getValue() + "").replaceAll(" ", "");
//                        list.add(entry.getKey() + "=" + value + "&");
//                    }
//                }
//            }
//            int size = list.size();
//            String [] arrayToSort = list.toArray(new String[size]);
//            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
//            StringBuilder sb = new StringBuilder();
//            for(int i = 0; i < size; i ++) {
//                sb.append(arrayToSort[i].trim());
//            }
//            String result = sb.toString().substring(0,sb.length()-1);

            String result = "";
            try {
                result = "accessKey="+ URLEncoder.encode(crmConfig.getZhongtaiAccessKey(),"UTF-8").toUpperCase()+
                        "&dateTime="+URLEncoder.encode(dat, "UTF-8").toUpperCase()+"&randomNumbers="+ URLEncoder.encode(ran, "UTF-8").toUpperCase();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
			signature = HmacSha1Util.genHMAC(result,zhongtai_access_secret);
            headers.set("signature",signature);
            entity = new HttpEntity<String>(headers);
            log.info("请求接口URL是:"+urlStr+",通用参数："+headers.toString()+"，value参数为："+result);
        }else {
            String result = "";
            try {
                result = "accessKey="+ URLEncoder.encode(crmConfig.getZhongtaiAccessKey(),"UTF-8").toUpperCase()+
                        "&dateTime="+URLEncoder.encode(dat, "UTF-8").toUpperCase()+"&randomNumbers="+ URLEncoder.encode(ran, "UTF-8").toUpperCase();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
			signature = HmacSha1Util.genHMAC(result,zhongtai_access_secret);
//            signature =  HmacSha1Util.genHMAC(enc,crmConfig.getZhongtaiAccessSecret());
            headers.set("signature",signature);
            entity = new HttpEntity<String>(jsonObject.toString(),headers);
            log.info("请求接口URL是:"+urlStr+",通用参数："+headers.toString()+"，value参数为："+result);
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(urlStr), httpMethod, entity, String.class);
            log.info("请求中台，响应的结果:"+responseEntity.toString());
            if (responseEntity != null) {
                return responseEntity;
            }else {
                saveLogBiz.saveLog(responseEntity,"中台接口鉴权");
                return new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
            }
        } catch (Exception e) {
            log.info("请求超时");
            saveLogBiz.saveLogEx(e,"中台接口鉴权");
            return new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
        }
    }

    /**
     * 8账单查询接口方法-中台
     * @param user
     * @param houseInfo
     * @return
     */
    public ResponseEntity<String>  getBillFromZhongTai(BaseAppClientUser user, HouseAllInfoVo houseInfo,String chargeItemCode) {
        Map<String, Object> map = new HashMap<>();
        String filingHouseNumber = houseInfo.getCrmHouseCode();
        String memberId = user.getMemberId();
        try {
            memberId =  URLEncoder.encode(memberId,"UTF-8");
            filingHouseNumber =  URLEncoder.encode(filingHouseNumber,"UTF-8");
            chargeItemCode =  URLEncoder.encode(chargeItemCode,"UTF-8");
            map.put("ChargeItemCode", chargeItemCode.toUpperCase());
            map.put("FilingHouseNumber",filingHouseNumber.toUpperCase());
            map.put("MemberId",memberId.toUpperCase());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.info("请求参数encode转换异常");
        }
        //"11003111062030" ,"BJ-YAJMY-A01-03-01-03F-0301"
        String params  = "api/Bills/QueryBills?FilingHouseNumber="+filingHouseNumber+
                "&MemberId="+memberId+"&ChargeItemCode="+chargeItemCode;
        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, null, map, HttpMethod.GET);
        log.info("请求中台，账单接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }

    /**
     * 9账单缴费接口方法-中台
     * @param bizSubscribe
     * @param accountBook
     * @return
     */
    public ObjectRestResponse syncBillToZhongTai(BizSubscribe bizSubscribe, AccountBook accountBook) {
        List<ShouldDateList> propertyDateList = bizSubPropertyFeeMapper.getPropertyDateList(bizSubscribe.getId());
        Map<String, Object> noticeMap = new HashMap<>();
        List<ChargeInfoList> chargeInfoList = new ArrayList<>();
        for (ShouldDateList shouldDateList : propertyDateList) {
            ChargeInfoList chargeInfo = new ChargeInfoList();
            chargeInfo.setAccountPeriod(shouldDateList.getShouldDate());
            chargeInfo.setAccountReceivableId(shouldDateList.getShouldId());
            chargeInfoList.add(chargeInfo);
        }
        noticeMap.put("ChargeTime", accountBook.getChargeTime());
        noticeMap.put("ChargeTotal", accountBook.getPayAmount());
        noticeMap.put("ChargePerson", bizSubscribe.getContactName());
        noticeMap.put("ChargeMethod", accountBook.getPayType());
        noticeMap.put("ChargeInfoList",chargeInfoList);
        String params = "api/Bills/ChargeBills";
        JSONObject jsonObject = JSONObject.fromObject(noticeMap);
        ObjectRestResponse responseEntity = zhongTaiAuthServicePost(params, jsonObject,noticeMap,HttpMethod.POST);
        log.info("请求中台，账单缴费接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }
//
//    /**
//     * 收费记录接口方法
//     * @param houseCode
//     * @param StartDate
//     * @param EndDate
//     * @return
//     */
//    public ResponseEntity<String> getHistoryFromZhongTai(String houseCode,String StartDate,String EndDate) {
//        Map<String, Object> map = new HashMap<>();
//        String params = "";
//        try {
//            map.put("HouseCode", URLEncoder.encode(houseCode,"UTF-8").toUpperCase());
//            if (StartDate != null && EndDate != null) {
//                map.put("StartDate", URLEncoder.encode(StartDate,"UTF-8").toUpperCase());
//                map.put("EndDate", URLEncoder.encode(EndDate,"UTF-8").toUpperCase());
//                params = "api/Bill/QueryVoucher?HouseCode="+houseCode+"&StartDate="+StartDate+"&EndDate="+EndDate;
//            }else {
//                params = "api/Bill/QueryVoucher?HouseCode="+houseCode;
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.info("请求参数encode转换异常");
//        }
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, null, map, HttpMethod.GET);
//        log.info("请求中台，收费记录接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }

    /**
     * 业主认证接口方法-中台
     * @param userName
     * @param telphone
     * @param houseCode
     * @return
     */
    public ObjectRestResponse authUserFromZhongtai(String userName,String telphone,String houseCode) {
        Map<String, Object> map = new HashMap<>();
        String PhoneNumber = "";
        log.info("业主认证请求参数OwnerName{},PhoneNumber{},RecordHouseCode{}",userName,telphone,houseCode);
        String password = config.getProperty("crm.zhongtaiPassword", "password");
        try {
            PhoneNumber = DESUtil.desEncrypt(telphone, password);
            map.put("PhoneNumber", URLEncoder.encode(PhoneNumber,"UTF-8").toUpperCase());
            map.put("RecordHouseCode", URLEncoder.encode(houseCode,"UTF-8").toUpperCase());
            map.put("OwnerName", URLEncoder.encode(userName,"UTF-8").toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("请求参数encode转换异常");
        }
//        String urlParams = "api/Owner/OwnerCertificationWithPhone?PhoneNumber=" + PhoneNumber
//                + "&RecordHouseCode=" + houseCode + "&OwnerName=" + userName;
//        ResponseEntity<String> responseEntity= zhongTaiAuthService(urlParams, null,map, HttpMethod.GET);

        String params = "api/Owner/OwnerCertificationWithPhone";
        Map<String, Object> noticeMap = new HashMap<>();
        noticeMap.put("PhoneNumber", PhoneNumber);
        noticeMap.put("RecordHouseCode", houseCode);
        noticeMap.put("OwnerName", userName);
        JSONObject jsonObject = JSONObject.fromObject(noticeMap);
        ObjectRestResponse responseEntity = zhongTaiAuthServicePost(params, jsonObject,noticeMap,HttpMethod.POST);
        log.info("请求中台，业主认证接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }

    /**
     * 中台接口鉴权方法
     * @param urlParams
     * @param jsonObject
     * @return
     */
    public ObjectRestResponse zhongTaiAuthServicePost(String urlParams, JSONObject jsonObject, Map<String, Object> map, HttpMethod httpMethod) {
        String zhongtai_url = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_URL.toString(), "");
        String zhongtai_access_key = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_ACCESS_KEY.toString(), "b040c515-3934-4d83-97ba-77e6b1005612");
        String zhongtai_access_secret = config.getProperty(CrmConfig.ApolloKey.ZHONGTAI_ACCESS_SECRET.toString(), "rFVDcJ1NwCtaKuTR18");
        String dat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String ran = UUID.randomUUID().toString().toUpperCase();
        Map<String,String> headers = new HashMap<>();
        String accesskey = zhongtai_access_key.toUpperCase();
//        String accesskey = crmConfig.getZhongtaiAccessKey().toUpperCase();
        headers.put("accessKey",accesskey);
        headers.put("dateTime",dat);
        headers.put("randomNumbers",ran);
        //签名生成：Base64（HMAC（accessSecret，value））
        String signature = "";
        HttpEntity<String> entity = null;
        String urlStr = zhongtai_url + urlParams;
//        String urlStr = crmConfig.getZhongtaiUrl() + urlParams;
        if (jsonObject == null) {
            try {
                map.put("accessKey", URLEncoder.encode(zhongtai_access_key,"UTF-8").toUpperCase());
//                map.put("accessKey", URLEncoder.encode(crmConfig.getZhongtaiAccessKey(),"UTF-8").toUpperCase());
                map.put("dateTime", URLEncoder.encode(dat,"UTF-8").toUpperCase());
                map.put("randomNumbers", URLEncoder.encode(ran,"UTF-8").toUpperCase());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                log.info("通用参数encode转换异常");
            }
            ArrayList<String> list = new ArrayList<String>();
            //通用参数排序
            for(Map.Entry<String,Object> entry:map.entrySet()){
                if(entry.getKey()!=""){
                    if (entry.getValue() instanceof String){
                        list.add(entry.getKey() + "=" + entry.getValue() + "&");
                    }else {
                        String value = (entry.getValue() + "").replaceAll(" ", "");
                        list.add(entry.getKey() + "=" + value + "&");
                    }
                }
            }
            int size = list.size();
            String [] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < size; i ++) {
                sb.append(arrayToSort[i].trim());
            }
            String result = sb.toString().substring(0,sb.length()-1);
            signature = HmacSha1Util.genHMAC(result,zhongtai_access_secret);
//            signature = HmacSha1Util.genHMAC(result,crmConfig.getZhongtaiAccessSecret());
            headers.put("signature",signature);
            log.info("请求接口URL是:"+urlStr+",通用参数："+headers.toString()+"，value参数为："+result);
        }else {
            String enc = "";
            try {
                enc = "accessKey="+ URLEncoder.encode(crmConfig.getZhongtaiAccessKey(),"UTF-8").toUpperCase()+
                        "&dateTime="+URLEncoder.encode(dat, "UTF-8").toUpperCase()+"&randomNumbers="+ URLEncoder.encode(ran, "UTF-8").toUpperCase();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            signature = HmacSha1Util.genHMAC(enc,zhongtai_access_secret);
//            signature =  HmacSha1Util.genHMAC(enc,crmConfig.getZhongtaiAccessSecret());
            headers.put("signature",signature);
            log.info("请求接口URL是:"+urlStr+",通用参数："+headers.toString()+"，value参数为："+enc);
        }

        ObjectRestResponse restResponse = new ObjectRestResponse();
        try {
            log.info("请求接口URL是:"+urlStr+",header参数："+headers.toString()+"，业务参数："+jsonObject.toString());
            String responseEntityStr= HttpClientUtil.sentPostHttpNew(urlStr,headers, jsonObject.toString());
            if(StringUtils.isNotEmpty(responseEntityStr)){
                restResponse.setMessage(responseEntityStr);
            }else{
                log.error("请求接口异常");
                restResponse.setStatus(201);
                restResponse.setMessage("请求接口异常");
            }
            log.info("请求中台，响应的结果:"+responseEntityStr);
        } catch (Exception e) {
            log.error("请求异常"+e);
            saveLogBiz.saveLogEx(e,"中台接口鉴权");
            restResponse.setStatus(201);
            restResponse.setMessage("请求异常"+e);
        }
        return restResponse;
    }

//    /**
//     * 房屋基础信息接口方法-中台
//     * @param ID
//     * @param type
//     * @return
//     */
//    public ResponseEntity<String> syncHouseInfoFromZhongtai(String ID, Integer type) {
//        Map<String, Object> map = new HashMap<>();
//        String urlParams = "";
//        try {
//            if (StringUtils.isNotEmpty(ID)) {
//                map.put("ID", URLEncoder.encode(ID,"UTF-8").toUpperCase());
//                map.put("Type", type);
//                urlParams = "api/Owner/HouseProjectInfo?ID=" + ID + "&Type=" + type;
//
//            }else {
//                map.put("Type",type);
//                urlParams = "api/Owner/HouseProjectInfo?Type=" + type;
//            }
//        } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                log.info("请求参数encode转换异常");
//            }
//        ResponseEntity<String> responseEntity= zhongTaiAuthService(urlParams, null,map, HttpMethod.GET);
//        log.info("请求中台，业主认证接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//
//    /**
//     * 报修工单修改接口方法-中台
//     * @param updateRepairOrderIn
//     * @return
//     */
//    public ResponseEntity<String> syncUpdateRepairToZhongtai(UpdateRepairOrderIn updateRepairOrderIn) {
//        String params = "api/WorkOrder/UpdateRepair";
//        JSONObject jsonObject = JSONObject.fromObject(updateRepairOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，报修工单修改接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//    /**
//     * 报修工单创建接口方法-中台
//     * @param createRepairOrderIn
//     * @return
//     */
//    public ResponseEntity<String> createRepairToZhongtai(CreateRepairOrderIn createRepairOrderIn) {
//        String params = "api/WorkOrder/CreateRecourse";
//        JSONObject jsonObject = JSONObject.fromObject(createRepairOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，报修工单创建接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//    /**
//     * 报修工单查询接口方法-中台
//     * @param selectRepairOrderIn
//     * @return
//     */
//    public ResponseEntity<String> selectRepairWoToZhongtai(SelectRepairOrderIn selectRepairOrderIn) {
//        //HouseCode Y
//        //RepairStatus Y
//        //PhoneNumber
//        //PageSize Y
//        //PageCount Y
//        Map<String, Object> map = new HashMap<>();
//        String PhoneNumber = "";
//        try {
//            if (StringUtils.isNotEmpty(selectRepairOrderIn.getPhoneNumber())) {
//                PhoneNumber = DESUtil.desEncrypt(selectRepairOrderIn.getPhoneNumber(), "password");
//            }
//            map.put("HouseCode", URLEncoder.encode(selectRepairOrderIn.getHouseCode(),"UTF-8").toUpperCase());
//            map.put("RepairStatus", URLEncoder.encode(selectRepairOrderIn.getRepairStatus(),"UTF-8").toUpperCase());
////            map.put("PhoneNumber", URLEncoder.encode(PhoneNumber,"UTF-8").toUpperCase());
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("请求参数encode转换异常");
//        }
//        String params = "api/Owner/QueryRepairs?HouseCode="+selectRepairOrderIn.getHouseCode()+"&RepairStatus="+selectRepairOrderIn.getRepairStatus()
//                +"&PageSize="+selectRepairOrderIn.getPageSize()+"&PageCount="+selectRepairOrderIn.getPageCount();
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, null, map, HttpMethod.GET);
//        log.info("请求中台，报修工单查询接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 投诉工单修改接口方法-中台
//     * @param updateComplainOrderIn
//     * @return
//     */
//    public ResponseEntity<String> syncUpdateComplainToZhongtai(UpdateComplainOrderIn updateComplainOrderIn) {
//        String params = "api/WorkOrder/UpdateComplaint";
//        JSONObject jsonObject = JSONObject.fromObject(updateComplainOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，投诉工单修改接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 投诉工单创建接口方法-中台
//     * @param createComplaintOrderIn
//     * @return
//     */
//    public ResponseEntity<String> createComplaintToZhongtai(CreateComplaintOrderIn createComplaintOrderIn) {
//        String params = "api/WorkOrder/CreateComplaint";
//        JSONObject jsonObject = JSONObject.fromObject(createComplaintOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，投诉工单创建接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 投诉工单查询接口方法-中台
//     * @param selectComplaintOrderIn
//     * @return
//     */
//    public ResponseEntity<String> selectComplaintWoToZhongtai(SelectComplaintOrderIn selectComplaintOrderIn) {
//        //ComplaintNumber
//        //ComplaintStatus
//        //ProjectCode
//        //HouseCode
//        //RecordHouseCode
//        //StartDate
//        //EndDate
//        //PageSize Y
//        //PageCount Y
//        //Type Y
//        Map<String, Object> map = new HashMap<>();
//        try {
//
////            map.put("HouseCode", URLEncoder.encode(selectComplaintOrderIn.getHouseCode(),"UTF-8").toUpperCase());
////            map.put("ComplaintNumber", URLEncoder.encode(selectComplaintOrderIn.getComplaintNumber(),"UTF-8").toUpperCase());
////            map.put("Type", selectComplaintOrderIn.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("请求参数encode转换异常");
//        }
//        String params = "api/WorkOrder/QueryComplaints?Type="+selectComplaintOrderIn.getType()+"&PageSize="+selectComplaintOrderIn.getPageSize()+"&PageCount="+selectComplaintOrderIn.getPageCount();
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, null, map, HttpMethod.POST);
//        log.info("请求中台，投诉工单查询接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 咨询工单修改接口方法-中台
//     * @param updateConsultOrderIn
//     * @return
//     */
//    public ResponseEntity<String> syncUpdateConsultToZhongtai(UpdateConsultOrderIn updateConsultOrderIn) {
//        String params = "api/WorkOrder/UpdateConsultation";
//        JSONObject jsonObject = JSONObject.fromObject(updateConsultOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，咨询工单修改接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 咨询工单创建接口方法-中台
//     * @param createConsultOrderIn
//     * @return
//     */
//    public ResponseEntity<String> createConsultToZhongtai(CreateConsultOrderIn createConsultOrderIn) {
//        String params = "api/WorkOrder/CreateConsultation";
//        JSONObject jsonObject = JSONObject.fromObject(createConsultOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，咨询工单创建接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 咨询工单查询接口方法-中台
//     * @param selectConsultOrderIn
//     * @return
//     */
//    public ResponseEntity<String> selectConsultWoToZhongtai(SelectConsultOrderIn selectConsultOrderIn) {
//        //ProjectCode
//        //HouseCode
//        //IsRecordHouseCode
//        //ConsultationPerson
//        //ConsultationFormat
//        //ConsultationPhone
//        //ConsultationStatus Y
//        //PageSize Y
//        //PageCount Y
//        Map<String, Object> map = new HashMap<>();
//        map.put("HouseCode", selectConsultOrderIn.getHouseCode());
//        map.put("ProjectCode", selectConsultOrderIn.getHouseCode());
//        map.put("PageSize", selectConsultOrderIn.getPageSize());
//        map.put("PageCount", selectConsultOrderIn.getPageCount());
//        map.put("IsRecordHouseCode", selectConsultOrderIn.getIsRecordHouseCode());
//        JSONObject jsonObject = JSONObject.fromObject(map);
////        try {
////            map.put("HouseCode", URLEncoder.encode(selectConsultOrderIn.getHouseCode(),"UTF-8").toUpperCase());
////            map.put("ProjectCode", URLEncoder.encode(selectConsultOrderIn.getHouseCode(),"UTF-8").toUpperCase());
//////            map.put("ConsultationStatus", selectConsultOrderIn.getConsultationStatus());
////        } catch (Exception e) {
////            e.printStackTrace();
////            log.info("请求参数encode转换异常");
////        }
//        String params = "api/WorkOrder/QueryConsultations";
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，咨询工单查询接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//    /**
//     * 求助工单修改接口方法-中台
//     * @param updateRecourseIn
//     * @return
//     */
//    public ResponseEntity<String> syncUpdateRecourseToZhongtai(UpdateRecourseIn updateRecourseIn) {
//        String params = "api/WorkOrder/UpdateRecourse";
//        JSONObject jsonObject = JSONObject.fromObject(updateRecourseIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，求助工单修改接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//    /**
//     * 求助工单创建接口方法-中台
//     * @param createRecourseOrderIn
//     * @return
//     */
//    public ResponseEntity<String> createRecourseToZhongtai(CreateRecourseOrderIn createRecourseOrderIn) {
//        String params = "api/WorkOrder/CreateRecourse";
//        JSONObject jsonObject = JSONObject.fromObject(createRecourseOrderIn);
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, jsonObject, null, HttpMethod.POST);
//        log.info("请求中台，求助工单创建接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
//
//
//    /**
//     * 求助工单查询接口方法-中台
//     * @param selectRecourseOrderIn
//     * @return
//     */
//    public ResponseEntity<String> selectRecourseWoToZhongtai(SelectRecourseOrderIn selectRecourseOrderIn) {
//        //ProjectCode
//        //HouseCode
//        //IsRecordHouseCode
//        //RecoursePerson
//        //RecoursePhone
//        //RecourseStatus
//        //RecourseFormat Y
//        //PageSize Y
//        //PageCount Y
//        Map<String, Object> map = new HashMap<>();
//        try {
//            map.put("RecourseFormat", selectRecourseOrderIn.getRecourseFormat());
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("请求参数encode转换异常");
//        }
//        String params = "api/Owner/QueryRepairs?RecourseFormat="+selectRecourseOrderIn.getRecourseFormat()
//                +"&PageSize="+selectRecourseOrderIn.getPageSize()+"&PageCount="+selectRecourseOrderIn.getPageCount();
//        ResponseEntity<String> responseEntity = zhongTaiAuthService(params, null, map, HttpMethod.GET);
//        log.info("请求中台，求助工单查询接口响应的结果:"+responseEntity.toString());
//        return responseEntity;
//    }
}
