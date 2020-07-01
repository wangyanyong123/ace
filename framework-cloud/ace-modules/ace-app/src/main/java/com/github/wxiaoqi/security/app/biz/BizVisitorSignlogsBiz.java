package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.FaceConfig;
import com.github.wxiaoqi.security.app.config.VisitorConfig;
import com.github.wxiaoqi.security.app.controller.FaceRecognitionController;
import com.github.wxiaoqi.security.app.entity.BizVisitorSignlogs;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.app.mapper.BizVisitorSignlogsMapper;
import com.github.wxiaoqi.security.app.vo.face.Unit;
import com.github.wxiaoqi.security.app.vo.face.UploadVo;
import com.github.wxiaoqi.security.app.vo.face.in.CheckFaceResponse;
import com.github.wxiaoqi.security.app.vo.userhouse.out.HouseInfoVo;
import com.github.wxiaoqi.security.app.vo.visitor.in.VisitorSignVo;
import com.github.wxiaoqi.security.app.vo.visitor.out.Qr;
import com.github.wxiaoqi.security.app.vo.visitor.out.VisitInfoVo;
import com.github.wxiaoqi.security.app.vo.visitor.out.VisitListVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 访客登记表
 *
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
@Service
public class BizVisitorSignlogsBiz extends BusinessBiz<BizVisitorSignlogsMapper, BizVisitorSignlogs> {

    private Logger logger = LoggerFactory.getLogger(BizVisitorSignlogsBiz.class);
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;
    @Autowired
    private BizVisitorSignlogsMapper bizVisitorSignLogsMapper;
    @Autowired
    private QrProvideFegin qrProvideFegin;
    @Autowired
    private BizCrmUnitMapper bizCrmUnitMapper;
    @Autowired
    private VisitorConfig visitorConfig;
    @Autowired
    private CrmServiceBiz crmServiceBiz;
    @Autowired
    private FaceConfig faceConfig;
    /**
     * 获取访客记录列表
     * @param projectId
     * @return
     */
    public ObjectRestResponse getVisitList(String projectId,Integer page,Integer limit) {
        ObjectRestResponse msg = new ObjectRestResponse();
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
        List<VisitListVo> visitLogList = bizVisitorSignLogsMapper.getVisitLogList(projectId,BaseContextHandler.getUserID(),startIndex,limit);
        if (StringUtils.isEmpty(projectId)) {
            msg.setMessage("projectId为空！");
            msg.setStatus(501);
            return msg;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        try {
            Date now = sdf.parse(sdf.format(date));
            for (VisitListVo visitListVo : visitLogList) {
                Date visitTime = sdf.parse(visitListVo.getVisitTime());
                //判断访客时间状态是否过期
                if (visitTime.getTime() < now.getTime()) {
                    visitListVo.setStatus("2");
                }else {
                    visitListVo.setStatus("1");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (visitLogList == null || visitLogList.size() == 0) {
            visitLogList = new ArrayList<>();
        }
        msg.setData(visitLogList);
        return msg;
    }

    /**
     * 获取访客记录详情
     * @param id
     * @return
     */
    public ObjectRestResponse getVisitLogInfo(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        VisitInfoVo visitLogInfo = bizVisitorSignLogsMapper.getVisitLogInfo(id);
        if (StringUtils.isEmpty(id)) {
            response.setMessage("id不能为空");
            response.setStatus(501);
            return response;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        try {
                Date now = sdf.parse(sdf.format(date));
                Date visitTime = sdf.parse(visitLogInfo.getVisitTime());
                //判断访客时间状态是否过期
                if (visitTime.getTime() < now.getTime()) {
                    visitLogInfo.setStatus("2");
                }else {
                    visitLogInfo.setStatus("1");
                }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.setData(visitLogInfo);
        return response;
    }

    /**
     * 保存访客记录
     * @param param
     * @return
     */
    public ObjectRestResponse saveVisitSign(VisitorSignVo param){
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        HouseInfoVo currentHouse = bizUserHouseBiz.getCurrentHouse().getData();
        String cityName = bizVisitorSignLogsMapper.getVisitAddress(currentHouse.getProjectName());
        String visitAddr = cityName + currentHouse.getProjectName() + currentHouse.getBuildName() + currentHouse.getFloorName() + currentHouse.getHouseName();
        if (StringUtils.isAnyoneEmpty(param.getName(),param.getProjectId(),
                param.getPhone(),param.getVisitTime(),param.getVisitReason())) {
            response.setMessage("参数不能为空");
            return response;
        }
        BizVisitorSignlogs bizVisitorSignlogs = new BizVisitorSignlogs();
        String visitId = UUIDUtils.generateUuid();
        if (param.getIsDrive() != null) {
            bizVisitorSignlogs.setIsDrive(param.getIsDrive());
        }
        bizVisitorSignlogs.setId(visitId);
        bizVisitorSignlogs.setHouseId(currentHouse.getHouseId());
        bizVisitorSignlogs.setProjectId(param.getProjectId());
        if (param.getIsDrive().equals("1")) {
            bizVisitorSignlogs.setLicensePlate(param.getLicensePlate());
        }
        bizVisitorSignlogs.setUserId(BaseContextHandler.getUserID());
        bizVisitorSignlogs.setVisitAddr(visitAddr);
        bizVisitorSignlogs.setVisitorName(param.getName());
        bizVisitorSignlogs.setVisitReason(param.getVisitReason());
        bizVisitorSignlogs.setVisitorPhone(param.getPhone());
        try {
            bizVisitorSignlogs.setVisitTime(sdf.parse(param.getVisitTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bizVisitorSignlogs.setCreateBy(BaseContextHandler.getUserID());
        bizVisitorSignlogs.setCreateTime(new Date());
        bizVisitorSignlogs.setTimeStamp(new Date());
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        //有效时间
        Calendar calEffTime = Calendar.getInstance();
        //失效时间
        Calendar calLoseTime = Calendar.getInstance();
        Date visitTime=sdf.parse(param.getVisitTime(),new ParsePosition(0));
        calEffTime.setTime(visitTime);
        //访客时间前二维码时间设置
        calEffTime.add(Calendar.HOUR,-Integer.parseInt(visitorConfig.getEffectiveTime()));
        calLoseTime.setTime(visitTime);
        //访客时间后二维码时间设置
        calLoseTime.add(Calendar.HOUR,Integer.parseInt(visitorConfig.getFailureTime()));
        bizVisitorSignlogs.setVisitEffectTime(calEffTime.getTime());
        bizVisitorSignlogs.setVisitEndTime(calLoseTime.getTime());
        if (bizVisitorSignLogsMapper.insertSelective(bizVisitorSignlogs) < 0) {
            logger.info("插入访客记录失败，id为{}",visitId);
            response.setStatus(101);
            response.setMessage("插入访客记录失败");
            return response;
        }
        //获取当前用户下的单元和围合信息
        String unitId = bizVisitorSignLogsMapper.getUnitId(currentHouse.getHouseId());
        String encloseId = bizVisitorSignLogsMapper.getEncloseId(unitId);
        try {
            //获取临时二维码
            ObjectRestResponse data = qrProvideFegin.generateTempPassQr(dateFormat.format(calEffTime.getTime()), dateFormat.format(calLoseTime.getTime()), encloseId,
                    param.getPhone(),param.getName(),Integer.parseInt(visitorConfig.getNumber()));
            Qr qr = (Qr) data.getData();
            if (data.getStatus() != 200) {
                logger.info("二维码生成情况:{}",data.getMessage());
                response.setStatus(101);
                response.setMessage("生成通行二维码失败");
            }
            if (qr.getQrVal() != null) {
                BizVisitorSignlogs visitorSignlogs = bizVisitorSignLogsMapper.selectByPrimaryKey(visitId);
                visitorSignlogs.setQrVal(qr.getQrVal());
                bizVisitorSignLogsMapper.updateByPrimaryKey(visitorSignlogs);
            }
            response.setStatus(200);
            response.setMessage("新增访客记录成功");
            if (param.getVisitPhoto() != null) {
                //上传访客人脸图片
                List<String> photoArray = new ArrayList<>();
                photoArray.add(param.getVisitPhoto());
                SimpleDateFormat visitFormat= new SimpleDateFormat("yyyyMMddHHmmss");
                Calendar calendar = Calendar.getInstance();
                String timestamp = visitFormat.format(calendar.getTime());
                Map<String,Object> tempMap = new HashMap<String,Object>();
                List<Map<String, String>> unitList = new ArrayList<>();
                Map<String, String> unitMap = new HashMap<>();
                unitMap.put("unitId", unitId);
                unitList.add(unitMap);
                //用户类型(1-业主、2-物业、3-访客)
                tempMap.put("userType","3");
                tempMap.put("userName",BaseContextHandler.getUserID());
                tempMap.put("appId",faceConfig.getAppId());
                tempMap.put("photoArray",photoArray);
                tempMap.put("appUserId",BaseContextHandler.getUserID());
                tempMap.put("projectId",param.getProjectId());
                tempMap.put("visitUserId",visitId);
                tempMap.put("visitUserName", param.getName());
                tempMap.put("visitStartTime", visitFormat.format(calEffTime.getTime()));
                tempMap.put("visitEndTime", visitFormat.format(calLoseTime.getTime()));
                tempMap.put("unitList",unitList);
                tempMap.put("timestamp",timestamp);
                ObjectRestResponse send = crmServiceBiz.send(faceConfig.getUploadUrl(), tempMap, timestamp);
                CheckFaceResponse result = (CheckFaceResponse) send.getData();
                if (result == null) {
                    response.setStatus(101);
                    response.setMessage("上传人脸失败");
                    return response;
                }
                if (result.getCode().equals("000")) {
                    bizVisitorSignlogs.setVisitorPhoto(param.getVisitPhoto());
                    bizVisitorSignLogsMapper.updateByPrimaryKeySelective(bizVisitorSignlogs);
                }else {
                    response.setStatus(101);
                    response.setMessage("上传人脸失败");
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("生成二维码失败:{}",e.getMessage());
        }
        response.setData(visitId);
        return response;
    }

    /**
     * 判断单元下是否有围合
     * @return
     */
    public ObjectRestResponse whetherVisitSign() {
        ObjectRestResponse response = new ObjectRestResponse();
        HouseInfoVo data = bizUserHouseBiz.getCurrentHouse().getData();
        String unitId = bizVisitorSignLogsMapper.getUnitId(data.getHouseId());
        String encloseId = bizVisitorSignLogsMapper.getEncloseId(unitId);
        if (encloseId == null) {
            response.setStatus(101);
            response.setMessage("所在单元尚未开放此功能");
        }
        return response;
    }
}