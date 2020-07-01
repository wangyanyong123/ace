package com.github.wxiaoqi.security.jinmao.biz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Bean2MapUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizActivity;
import com.github.wxiaoqi.security.jinmao.entity.BizActivityQr;
import com.github.wxiaoqi.security.jinmao.feign.OssExcelFeign;
import com.github.wxiaoqi.security.jinmao.feign.SmsFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultServiceInfo;
import com.github.wxiaoqi.security.jinmao.vo.activity.in.SaveActivityParam;
import com.github.wxiaoqi.security.jinmao.vo.activity.in.UpdateStatus;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 小组活动表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizActivityBiz extends BusinessBiz<BizActivityMapper,BizActivity> {

    private Logger logger = LoggerFactory.getLogger(BizActivityBiz.class);
    @Autowired
    private BizActivityMapper bizActivityMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private OssExcelFeign ossExcelFeign;
    @Autowired
    private SmsFeign smsFeign;
    @Autowired
    private ToolFegin toolFeign;
    @Autowired
    private BizActivityQrMapper bizActivityQrMapper;
    @Autowired
    private BaseAppServerUserMapper baseAppServerUserMapper;

    @Autowired
    BizCrmHouseMapper bizCrmHouseMapper;

    /**
     * 查询各小组活动列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ActivityVo> getActivityList(String enableStatus, String searchVal, Integer page, Integer limit){
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
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ActivityVo> activityVoList = bizActivityMapper.selectActivityList(type, BaseContextHandler.getTenantID(),
                enableStatus, searchVal, startIndex, limit);
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return activityVoList;
    }

    /**
     * 查询活动数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectActivityCount(String enableStatus, String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = bizActivityMapper.selectActivityCount(type, BaseContextHandler.getTenantID(), enableStatus, searchVal);
        return total;
    }


    /**
     * 保存活动
     * @param param
     * @return
     */
    public ObjectRestResponse saveActivity(SaveActivityParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        Map<String,String> dataMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isAnyEmpty(param.getTitle(),param.getBegTime(),param.getEndTime(),param.getAddress(),
                param.getContactorName(),param.getContactTel(),param.getApplyEndTime())){
            msg.setStatus(501);
            msg.setMessage("请继续完善信息内容!");
            return msg;
        }
        if(param.getProjectList() == null || param.getProjectList().size() == 0){
            msg.setStatus(502);
            msg.setMessage("项目id不能为空!");
            return msg;
        }
        if("1".equals(param.getType())){//小组
            if(param.getGroupList() == null || param.getGroupList().size() == 0){
                msg.setStatus(503);
                msg.setMessage("小组id不能为空!");
                return msg;
            }
        }
        BizActivity activity = new BizActivity();
        String id = UUIDUtils.generateUuid();
        try {
            activity.setId(id);
            activity.setProjectId(param.getProjectList().get(0).getProjectId());
            if("1".equals(param.getType())){
                activity.setGroupId(param.getGroupList().get(0).getGroupId());
            }
            activity.setTitle(param.getTitle());
            activity.setAddress(param.getAddress());
            if(param.getActivitCcoverList() != null && param.getActivitCcoverList().size() > 0){
                if(StringUtils.isNotEmpty(param.getActivitCcoverList().get(0).getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(param.getActivitCcoverList().get(0).getUrl(), DocPathConstant.OWNERCIRCLE);
                    if(objectRestResponse.getStatus()==200){
                        activity.setActivitCcover(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //activity.setActivitCcover(param.getActivitCcoverList().get(0).getUrl());
            }
            activity.setContactorName(param.getContactorName());
            activity.setContactTel(param.getContactTel());
            activity.setBegTime(sdf.parse(param.getBegTime()));
            activity.setEndTime(sdf.parse(param.getEndTime()));
            activity.setApplyEndTime(sdf.parse(param.getApplyEndTime()));
            activity.setType(param.getType());
           if("0".equals(param.getIsFree())){ //是否收费
               activity.setIsFree("0");
               activity.setActCost(new BigDecimal(param.getActCost()));
               if("1".equals(param.getIsCancel())){//是否可取消
                   activity.setIsCancel("1");
                   activity.setCancelTime(Double.parseDouble(param.getCancelTime()));
               }
           }else{
               activity.setActCost(new BigDecimal(0));
           }
            activity.setSummary(param.getSummary());
           if(param.getPersonNum() != null && param.getPersonNum() != ""){
               activity.setPersonNum(Integer.parseInt(param.getPersonNum()));
           }
            activity.setCreateBy(BaseContextHandler.getUserID());
            activity.setCreateTime(new Date());
            activity.setTimeStamp(new Date());
            if(bizActivityMapper.insertSelective(activity) < 0){
                msg.setStatus(201);
                msg.setMessage("保存活动失败!");
                return msg;
            }
        } catch (ParseException e) {
            logger.error("时间格式异常",e);
        }
        //生成二维码
        int width = 150;
        int height = 150;
        dataMap.put("type","activitySign");
        dataMap.put("id",id);
        JSONObject json = JSONObject.fromObject(dataMap);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        for (String temp : list){
            if("1".equals(temp)){
                width = 80;
                height = 80;
            }else if("2".equals(temp)){
                width = 120;
                height = 120;
            }else if("4".equals(temp)){
                width = 300;
                height = 300;
            }else if("5".equals(temp)){
                width = 500;
                height = 500;
            }else if("3".equals(temp)){
                width = 150;
                height = 150;
            }
            String url = "";
            ExcelInfoVo excelInfoVo = new ExcelInfoVo();
            excelInfoVo.setFileName(Integer.toString(width)+Integer.toString(height)+".png");
            excelInfoVo.setContent(json.toString());
            excelInfoVo.setWidth(width);
            excelInfoVo.setHeight(height);
            ObjectRestResponse objectRestResponse =smsFeign.uploadQRImg(excelInfoVo);
            if(objectRestResponse.getStatus() == 200){
                url =(String) objectRestResponse.getData();
                BizActivityQr qr = new BizActivityQr();
                qr.setId(UUIDUtils.generateUuid());
                qr.setActivityId(id);
                qr.setSize(temp);
                qr.setQrUrl(url);
                qr.setCreateBy(BaseContextHandler.getUserID());
                qr.setCreateTime(new Date());
                qr.setTimeStamp(new Date());
                if(bizActivityQrMapper.insertSelective(qr) > 0){
                    //删除原来的图片
                    //new File("/opt/jmdeploy/temp"+Integer.toString(width)+Integer.toString(height)+".png").delete();
                }
            }

        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询活动签到二维码
     * @param id
     * @return
     */
    public List<QRVo> getAllQRList(String id){
        List<QRVo> qrVoList = bizActivityMapper.selectAllQRList(id);
        if(qrVoList == null || qrVoList.size() == 0){
            qrVoList = new ArrayList<>();
        }
        return qrVoList;
    }



    /**
     * 查询该租户下所属项目
     * @return
     */
    public List<ProjectVo> getProjectByTenantId(){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ProjectVo> projectVoList = bizActivityMapper.selectProjectByTenantId(type,BaseContextHandler.getTenantID());
        if(projectVoList == null || projectVoList.size() == 0){
            projectVoList = new ArrayList<>();
        }
        return projectVoList;
    }

    /**
     * 查询项目下所属小组
     * @param projectId
     * @return
     */
    public List<GroupVo> getGroupByProjectId(String projectId){
        List<GroupVo> groupVoList = bizActivityMapper.selectGroupByProjectId(projectId);
        if(groupVoList == null || groupVoList.size() == 0){
            groupVoList = new ArrayList<>();
        }
        return groupVoList;
    }

    /**
     * 查询小组活动详情
     * @param id
     * @return
     */
    public List<ActivityInfo> getActivityInfo(String id){
        List<ActivityInfo> returnVo = new ArrayList<>();
        ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(id);
        if(activityInfo != null){
            if(activityInfo.getPersonNum().equals("-1")){
                activityInfo.setPersonNum("");
            }
            List<ProjectVo> projectList =  bizActivityMapper.selectProjectById(id);
            if(projectList == null && projectList.size() == 0){
                projectList = new ArrayList<>();
            }
            activityInfo.setProjectList(projectList);

            List<GroupVo> groupList = bizActivityMapper.selectGroupById(id);
            if(groupList == null && groupList.size() == 0){
                groupList = new ArrayList<>();
            }
            activityInfo.setGroupList(groupList);

            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(activityInfo.getActivitCcover());
            list.add(imgInfo);
            activityInfo.setActivitCcoverList(list);
        }else{
            activityInfo = new ActivityInfo();
        }
        returnVo.add(activityInfo);
        return returnVo;
    }


    /**
     * 编辑活动
     * @param param
     * @return
     */
    public ObjectRestResponse updateActivity(SaveActivityParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isAnyEmpty(param.getTitle(),param.getBegTime(),param.getEndTime(),param.getAddress(),
                param.getContactorName(),param.getContactTel(),param.getApplyEndTime())){
            msg.setStatus(501);
            msg.setMessage("请继续完善信息内容!");
            return msg;
        }
        if(param.getProjectList() == null || param.getProjectList().size() == 0){
            msg.setStatus(502);
            msg.setMessage("项目id不能为空!");
            return msg;
        }

        if("1".equals(param.getType())){//小组
            if(param.getGroupList() == null || param.getGroupList().size() == 0){
                msg.setStatus(503);
                msg.setMessage("小组id不能为空!");
                return msg;
            }
        }
        ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(param.getId());
        if(activityInfo != null){
            BizActivity activity = new BizActivity();
            try {
                activity.setId(param.getId());
                activity.setProjectId(param.getProjectList().get(0).getProjectId());
                if("1".equals(param.getType())) {//小组
                    activity.setGroupId(param.getGroupList().get(0).getGroupId());
                }else{
                    activity.setGroupId("");
                }
                activity.setTitle(param.getTitle());
                activity.setAddress(param.getAddress());
                if(param.getActivitCcoverList() != null && param.getActivitCcoverList().size() > 0){
                    if(StringUtils.isNotEmpty(param.getActivitCcoverList().get(0).getUrl())){
                        ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(param.getActivitCcoverList().get(0).getUrl(), DocPathConstant.OWNERCIRCLE);
                        if(objectRestResponse.getStatus()==200){
                            activity.setActivitCcover(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                        }
                    }
                    //activity.setActivitCcover(param.getActivitCcoverList().get(0).getUrl());
                }
                activity.setContactorName(param.getContactorName());
                activity.setContactTel(param.getContactTel());
                activity.setBegTime(sdf.parse(param.getBegTime()));
                activity.setEndTime(sdf.parse(param.getEndTime()));
                activity.setApplyEndTime(sdf.parse(param.getApplyEndTime()));
                activity.setType(param.getType());
                if("0".equals(param.getIsFree())){ //是否收费
                    activity.setIsFree("0");
                    activity.setActCost(new BigDecimal(param.getActCost()));
                    if("1".equals(param.getIsCancel())){//是否可取消
                        activity.setIsCancel("1");
                        activity.setCancelTime(Double.parseDouble(param.getCancelTime()));
                    }else{
                        activity.setIsCancel("0");
                        activity.setCancelTime(null);
                    }
                }else{
                    activity.setIsFree("1");
                    activity.setActCost(new BigDecimal(0));
                    activity.setIsCancel("0");
                    activity.setCancelTime(null);
                }
                if(param.getSummary() != null && param.getSummary() != ""){
                    activity.setSummary(param.getSummary());
                }else{
                    activity.setSummary("");
                }
                if(param.getPersonNum() != null && param.getPersonNum() != ""){
                    activity.setPersonNum(Integer.parseInt(param.getPersonNum()));
                }else{
                    activity.setPersonNum(-1);
                }
                activity.setModifyBy(BaseContextHandler.getUserID());
                activity.setModifyTime(new Date());
                activity.setTimeStamp(new Date());
                if(bizActivityMapper.updateByPrimaryKeySelective(activity) < 0){
                    msg.setStatus(201);
                    msg.setMessage("编辑活动失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑活动异常",e);
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 活动操作(0-删除,2-发布,3-撤回)
     * @param param
     * @return
     */
    public ObjectRestResponse updateActivityStatus(UpdateStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(bizActivityMapper.updateActivityStatus(BaseContextHandler.getUserID(),param.getStatus(),param.getId()) < 0){
            msg.setStatus(201);
            msg.setMessage("活动操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询用户反馈列表
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
       public List<FeedbackVo> getFeedbackList(String projectId,String startTime,String endTime,String searchVal,Integer page, Integer limit){
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
           String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
           if(!"3".equals(type)){
               projectId = bizActivityMapper.selectProjectIdByTenantId(BaseContextHandler.getTenantID());
           }
           List<FeedbackVo> feedbackVoList = bizActivityMapper.selectFeedbackList(type,projectId,startTime, endTime, searchVal, startIndex, limit);
           for(FeedbackVo feedbackVo: feedbackVoList){
               if ("2".equals(feedbackVo.getSource()) || "4".equals(feedbackVo.getSource())) {
                   ResultServiceInfo resultServiceInfo = baseAppServerUserMapper.selectServiceInfoById(feedbackVo.getUserId());
                   if(resultServiceInfo != null){
                       StringBuilder str = new StringBuilder();
                       if("1".equals(resultServiceInfo.getIsCustomer())){
                           str.append("客服"+",");
                       }
                       if("1".equals(resultServiceInfo.getIsHouseKeeper())){
                           str.append("管家"+",");
                       }
                       if("1".equals(resultServiceInfo.getIsService())){
                           str.append("工程人员"+",");
                       }
                       if(str.length() > 0){
                           String temp = str.substring(0, str.length() - 1);
                           feedbackVo.setUserType(temp);
                       }
                   }
                   String userType = bizActivityMapper.selectUserTypeByUserId(feedbackVo.getUserId());
                   if(userType != null && !StringUtils.isEmpty(userType)){
                       feedbackVo.setUserType(userType);
                   }

               }else{
                   feedbackVo.setUserType("回家App");
               }
           }
           return feedbackVoList;
       }

    /**
     * 查询用户反馈数量
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
       public int selectFeedbackCount(String projectId,String startTime,String endTime,String searchVal){
           String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
           if(!"3".equals(type)){
               projectId = bizActivityMapper.selectProjectIdByTenantId(BaseContextHandler.getTenantID());
           }
          int total =  bizActivityMapper.selectFeedbackCount(type,projectId,startTime, endTime, searchVal);
          return total;
       }


    /**
     * 查询活动下的报名列表
     * @param page
     * @param limit
     * @return
     */
    public List<UserApplyVo> getUserApplyList(String id, String signType, Integer page, Integer limit){
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
        List<UserApplyVo> userApplyList = bizActivityMapper.selectUserApplyList(id,signType, startIndex, limit);
        if(userApplyList == null || userApplyList.size() == 0){
            userApplyList = new ArrayList<>();
        }
        return userApplyList;
    }

    /**
     * 查询报名人数
     * @return
     */
    public int selectUserApplyCount(String id ,String signType){
        int total = bizActivityMapper.selectUserApplyCount(id,signType);
        return total;
    }





    /**
     * 导出excel
     * @param id
     * @return
     */
    public ObjectRestResponse exportExcel(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        //获取报名人列表
        List<UserApplyVo> userApplyList = bizActivityMapper.selectExportUserApplyList(id);
        if(userApplyList == null || userApplyList.size() == 0){
            msg.setStatus(102);
            msg.setMessage("列表无数据展示,请重新加载数据导出Excel文件!");
            return msg;
        }
        List<Map<String, Object>> dataList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        if (userApplyList != null && userApplyList.size() > 0) {
            for (int i = 0; i < userApplyList.size(); i++) {
                UserApplyVo temp = mapper.convertValue(userApplyList.get(i), UserApplyVo.class);
                Map<String, Object> dataMap = Bean2MapUtil.transBean2Map(temp);

                List<String> houseNames = new ArrayList<>();
                List<Map<String, String>> houses = bizCrmHouseMapper.getCrmHouseByUserId(temp.getUserId());
                for (Map<String, String> tmpHouse : houses) {
                    String houseName = tmpHouse.get("houseName");
                    if (StringUtils.isNotEmpty(houseName)) {
                        houseNames.add(houseName);
                    }
                }
                StringBuilder houseNameSb = new StringBuilder();
                for (int j = 0; j < houseNames.size(); j++) {
                    houseNameSb.append(houseNames.get(j));
                    if (j < houseNames.size() - 1) {
                        houseNameSb.append(',');
                    }
                }
                dataMap.put("houseName", houseNameSb.toString());

                dataList.add(dataMap);
            }
        }
        String[] titles = {"用户姓名","用户电话","报名时间","签到状态", "房号"};
        String[] keys = {"userName","userTel","applyTime","signType", "houseName"};
        String fileName = "报名列表.xlsx";

        ExcelInfoVo excelInfoVo = new ExcelInfoVo();
        excelInfoVo.setTitles(titles);
        excelInfoVo.setKeys(keys);
        excelInfoVo.setDataList(dataList);
        excelInfoVo.setFileName(fileName);
        msg = ossExcelFeign.uploadExcel(excelInfoVo);

        return msg;
    }




}