package com.github.wxiaoqi.security.app.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizSubscribe;
import com.github.wxiaoqi.security.app.mapper.BizSubPropertyFeeMapper;
import com.github.wxiaoqi.security.app.vo.crm.in.SyncWorkOrderStateBody;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.propertybill.ShouldDateList;
import com.github.wxiaoqi.security.app.vo.propertybill.out.AccountBook;
import com.github.wxiaoqi.security.common.util.HmacSha1Util;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CrmOldServiceBiz {

    private static Config config = ConfigService.getConfig("ace-app");

    @Autowired
    private BizSubPropertyFeeMapper bizSubPropertyFeeMapper;
    @Autowired
    private CrmConfig crmConfig;
    @Autowired
    private SaveLogBiz saveLogBiz;

    /**
     * 5.缴费接口方法-old
     * @param bizSubscribe
     * @param accountBook
     * @return
     */
    public ResponseEntity<String> syncBillToCrm(BizSubscribe bizSubscribe, AccountBook accountBook) {
        List<ShouldDateList> propertyDateList = bizSubPropertyFeeMapper.getPropertyDateList(bizSubscribe.getId());
        Map<String, Object> noticeMap = new HashMap<>();
        noticeMap.put("crmUserId", bizSubscribe.getCrmUserId());
        noticeMap.put("projectId", bizSubscribe.getCrmProjectCode());
        noticeMap.put("roomId", bizSubscribe.getCrmRoomCode());
        noticeMap.put("chargeTime", accountBook.getChargeTime());
        noticeMap.put("payAmount",accountBook.getPayAmount());
        noticeMap.put("payType", accountBook.getPayType());
        noticeMap.put("shouldDateList", propertyDateList);
        String urlParams = "api/ChargeBills";
        JSONObject jsonObject = JSONObject.fromObject(noticeMap);
        ResponseEntity<String> responseEntity = crmAuthService(urlParams, jsonObject, HttpMethod.POST);
        log.info("请求crm系统，获取账单接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }

    /**
     * 4.账单查询接口方法-old
     * @param user
     * @param houseInfo
     * @return
     */
    public ResponseEntity<String> getBillFromOldCrm(BaseAppClientUser user, HouseAllInfoVo houseInfo) {
        Integer currentPage = 0;
        Integer pageSize = 1000;
        String urlParams = "api/QueryBills?crmUserId="+user.getCrmUserId()+
                "&projectId="+houseInfo.getCrmProjectCode()+"&roomId="+houseInfo.getCrmHouseCode()+
                "&currentPage="+currentPage+"&pageSize="+pageSize;
        ResponseEntity<String> responseEntity = crmAuthService(urlParams, null,HttpMethod.GET);
        log.info("请求crm系统，获取账单接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }

    /**
     * 3.报修/投诉同步接口方法
     * @param syncWorkOrderStateBody
     * @return
     */
    public ResponseEntity<String> syncWoToCrm(SyncWorkOrderStateBody syncWorkOrderStateBody) {
        String params = "api/SyncWorkOrderState";
        JSONObject jsonObj = JSONObject.fromObject(syncWorkOrderStateBody);
        ResponseEntity<String> responseEntity = crmAuthService(params, jsonObj, HttpMethod.POST);
        log.info("请求crm系统，工单同步接口响应的结果:"+responseEntity.toString());
        return responseEntity;
    }



    /**
     * 6.业主校验接口方法
     * @param userName
     * @param telphone
     * @param houseId
     * @return
     */
    public ResponseEntity<String> authUserFromCrm(String userName,String telphone,String houseId) {
        String urlParams = "api/MemberVerification?userName=" + userName
                + "&telphone=" + telphone + "&roomId=" + houseId;
        ResponseEntity<String> responseEntity= crmAuthService(urlParams, null, HttpMethod.GET);
        log.info("请求crm系统，业主认证接口响应的结果:" + responseEntity.toString());
        return new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
    }

    /**
     * CRM old接口鉴权方法
     * @param urlParams
     * @param jsonObj
     * @return
     */

    public ResponseEntity<String>  crmAuthService(String urlParams, JSONObject jsonObj, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        String url = config.getProperty(CrmConfig.ApolloKey.URL.toString(), "");
        String access_key = config.getProperty(CrmConfig.ApolloKey.ACCESS_KEY.toString(), "");
        String access_secret = config.getProperty(CrmConfig.ApolloKey.ACCESS_SECRET.toString(), "");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("accessKey",access_key);
//		headers.set("accessKey",crmConfig.getAccessKey());
        //时间格式为yyyy-MM-dd HH:mm:ss
        String dat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String ran = UUID.randomUUID().toString();
        headers.set("dateTime",dat);
        headers.set("randomNumbers",ran);
        //签名生成：Base64（HMAC（accessSecret，value））
        String decode = "";
        try {
            decode = URLEncoder.encode(dat, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String enc = "accessKey="+access_key+"&dateTime="+decode+"&randomNumbers="+ ran;
//        String enc = "accessKey="+crmConfig.getAccessKey()+"&dateTime="+decode+"&randomNumbers="+ ran;
		String signature = HmacSha1Util.genHMAC(enc,access_secret);
//        String signature = HmacSha1Util.genHMAC(enc,crmConfig.getAccessSecret());
        headers.set("signature",signature);
        HttpEntity<String> entity = null;
        if (jsonObj != null) {
            entity = new HttpEntity<String>(jsonObj.toString(),headers);
        }else {
            entity = new HttpEntity<String>(headers);
        }
        String urlStr = url + urlParams;
//		String urlStr = crmConfig.getUrl() + urlParams;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

//        //获取Apollo配置,请求超时时间
//        Config config = ConfigService.getConfig("ace-app");
//        String timeout = config.getProperty("http.request.timeout","60");
//        if(StringUtils.isNotEmpty(timeout)){
//            requestFactory.setConnectTimeout(Integer.valueOf(timeout)*1000);
//            requestFactory.setReadTimeout(Integer.valueOf(timeout)*1000);
//        }else{
//            requestFactory.setConnectTimeout(5*1000);
//            requestFactory.setReadTimeout(5*1000);
//        }
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        log.info("请求crm系统，接口URL是:"+urlStr+",请求头参数是："+headers.toString()+",业务参数："+entity.toString());
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(urlStr, httpMethod, entity,String.class);
            if (responseEntity != null) {
                log.info("请求crm系统，接口鉴权响应的结果:" + responseEntity.toString());
                return responseEntity;
            }else {
                saveLogBiz.saveLog(responseEntity,"crm鉴权接口");
                return new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
            }
        } catch (RestClientException e) {
            log.info("请求CRM连接超时");
            saveLogBiz.saveLogEx(e,"crm鉴权接口");
            return new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
        }
    }


}
