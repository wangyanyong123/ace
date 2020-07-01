package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.FaceConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizAppUserFace;
import com.github.wxiaoqi.security.app.mapper.BizAppUserFaceMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.app.mapper.BizExternalUserPasslogMapper;
import com.github.wxiaoqi.security.app.vo.face.FaceInfoVo;
import com.github.wxiaoqi.security.app.vo.face.SyncCrmFaceVo;
import com.github.wxiaoqi.security.app.vo.face.UploadVo;
import com.github.wxiaoqi.security.app.vo.face.UserFaceInfo;
import com.github.wxiaoqi.security.app.vo.face.in.CheckFaceResponse;
import com.github.wxiaoqi.security.auth.client.jwt.MD5;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户人脸头像表
 *
 * @author zxl
 * @Date 2019-01-07 10:36:52
 */
@Service
@Slf4j
public class BizAppUserFaceBiz extends BusinessBiz<BizAppUserFaceMapper, BizAppUserFace> {

    @Autowired
    private BizAppUserFaceMapper bizAppUserFaceMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BizCrmUnitMapper bizCrmUnitMapper;
    @Autowired
    private BizExternalUserPasslogMapper bizExternalUserPasslogMapper;
    @Autowired
    private BizAppUserFaceBiz bizAppUserFaceBiz;
    @Autowired
    private FaceConfig faceConfig;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private ZhongTaiServiceBiz zhongTaiServiceBiz;
    private Logger logger = LoggerFactory.getLogger(BizAppUserFaceBiz.class);
    /**
     * 获取人脸图片信息
     * @param userId
     * @return
     */
    public ObjectRestResponse getFaceInfo(String userId) {
        ObjectRestResponse msg = new ObjectRestResponse();
        FaceInfoVo face = bizAppUserFaceMapper.getFaceInfoByUserId(BaseContextHandler.getUserID());
        if (face != null) {
            String[] photoes = face.getPhoto().split(",");
            List<String> list = new ArrayList<>();
            for (String photo : photoes) {
                list.add(photo);
            }
            msg.setData(photoes);
        }
        return msg;
    }

    /**
     * 删除人脸信息
     * @param userId
     * @return
     */
    public int deleteFaceInfo(String userId) {
        int i = bizAppUserFaceMapper.deleteFaceInfo(userId);
        if (i < 0) {
            logger.info("删除人脸失败,ID为{}", userId);

        }
        return i;
    }

    public ObjectRestResponse deleteFaceByUser(UserFaceInfo param) {
        return ObjectRestResponse.ok();
    }

    public ObjectRestResponse send(String url, Map<String,Object> param, String timestamp) {
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
            CheckFaceResponse checkFaceResponse = JSONObject.parseObject(strbody, CheckFaceResponse.class);
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

    /**
     * 上传人脸成功后同步CRM
     * @param uploadfaceVo
     * @return
     */
    public ObjectRestResponse syncCrmNewFace(UploadVo uploadfaceVo){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        BaseAppClientUser baseAppClientUser = baseAppClientUserBiz.getUserById(userId);
        if(CollectionUtils.isNotEmpty(uploadfaceVo.getPhotoArray())){
            List<SyncCrmFaceVo> syncCrmFaceVoList = this.mapper.queryFaceUserInfo(userId,uploadfaceVo.getProjectId());
            for (SyncCrmFaceVo syncCrmFaceVo:syncCrmFaceVoList) {
                syncCrmFaceVo.setName(baseAppClientUser.getNickname());
                syncCrmFaceVo.setPhoneNumber(baseAppClientUser.getMobilePhone());
                syncCrmFaceVo.setPhotoUrl(uploadfaceVo.getPhotoArray().get(0));
                // 调取同步接口
                objectRestResponse = syncFaceToCRM(syncCrmFaceVo);
                if(!objectRestResponse.success()){
                    return objectRestResponse;
                }
            }
            return objectRestResponse;
        }
        objectRestResponse.setStatus(507);
        objectRestResponse.setMessage("请上传人脸照片");
        return objectRestResponse;
    }


    /**
     * CRM同步人脸
     * @param syncCrmFaceVo
     * @return
     */
    private ObjectRestResponse syncFaceToCRM(SyncCrmFaceVo syncCrmFaceVo) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        Map<String, Object> noticeMap = new HashMap<>();
        noticeMap.put("housecode", syncCrmFaceVo.getHousecode());
        noticeMap.put("houseID", syncCrmFaceVo.getHouseID());
        noticeMap.put("name", syncCrmFaceVo.getName());
        noticeMap.put("phoneNumber", syncCrmFaceVo.getPhoneNumber());
        noticeMap.put("photoUrl", syncCrmFaceVo.getPhotoUrl());
        noticeMap.put("relationship", syncCrmFaceVo.getRelationship());
        String params = "api/Owner/CreateMemberPiecture";
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(noticeMap);
        ObjectRestResponse responseEntity = zhongTaiServiceBiz.zhongTaiAuthServicePost(params,jsonObject,noticeMap,HttpMethod.POST);
        log.info("人脸同步CRM响应结果:"+responseEntity.getStatus()+",message:"+responseEntity.getMessage());
        if(!ObjectUtils.isEmpty(responseEntity)){
            return responseEntity;
        }
        objectRestResponse.setStatus(507);
        objectRestResponse.setMessage("CRM同步人脸接口异常");
        return objectRestResponse;
    }

    /**
     * 人脸历史数据同步CRM
     * @return
     */
    public ObjectRestResponse syncHistoryFaceToCrm() {
        ObjectRestResponse response = new ObjectRestResponse();
        int page = 0;
        int limit = 100;
        int total = 0;
        AtomicInteger successTotal = new AtomicInteger();
        AtomicInteger failTotal = new AtomicInteger();
        while (true){
            //分页
            int startIndex = page * limit;
            List<SyncCrmFaceVo> syncCrmFaceVoList = this.mapper.syncHistoryFaceToCrm(startIndex,limit);
            if(CollectionUtils.isEmpty(syncCrmFaceVoList)){
                log.info("同步CRM人脸数据完成");
                break;
            }
            // 调取同步接口
            syncCrmFaceVoList.forEach(syncCrmFaceVo ->{
                        ObjectRestResponse responseEntity = syncFaceToCRM(syncCrmFaceVo);
                        if(responseEntity.success()){
                            successTotal.getAndIncrement();
                        }else {
                            failTotal.getAndIncrement();
                        }
                    });
            page++;
            total += syncCrmFaceVoList.size();
            try {
                Thread.sleep(1000);
                log.info("同步一次后休眠1秒后继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("共同步人脸数据:{}条,成功：{}条，失败:{}条。",total,successTotal,failTotal);
        response.setMessage("共同步人脸数据:"+total+"条,成功："+successTotal+"条，失败:"+failTotal+"条。");
        return response;
    }
}