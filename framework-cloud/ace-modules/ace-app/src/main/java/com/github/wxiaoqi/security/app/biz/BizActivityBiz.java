package com.github.wxiaoqi.security.app.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateByTypeVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundInfoVo;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.activity.out.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.in.SubInVo;
import com.github.wxiaoqi.security.app.vo.propertybill.out.UserBillOutVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 小组活动表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@Service
@Slf4j
public class BizActivityBiz extends BusinessBiz<BizActivityMapper,BizActivity> {

    @Autowired
    private BizActivityMapper bizActivityMapper;
    @Autowired
    private BizCommentMapper bizCommentMapper;
    @Autowired
    private BizActivityApplyMapper bizActivityApplyMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BizCrmHouseMapper bizCrmHouseMapper;
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private BizSubBiz bizSubBiz;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private BizRefundAuditBiz bizRefundAuditBiz;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;

    /**
     * 查询小组活动贴列表
     * @param groupId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ActivityVo>> getActivityList(String groupId, Integer page, Integer limit){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
        List<ActivityVo> activityVoList = bizActivityMapper.selectActivityList(groupId, startIndex, limit);
        for (ActivityVo activity : activityVoList){
            //活动封面
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(activity.getActivitCcover());
            list.add(imgInfo);
            activity.setActivitCcoverList(list);
            //报活动名人头像
            List<ImgInfo> imgInfoList = bizActivityMapper.selectApplyPhotoByThree(activity.getId());
            for(ImgInfo url:imgInfoList){
                if("".equals(url.getUrl())){
                    url.setUrl(getRandomPhoto());
                }
            }
            activity.setImgList(imgInfoList);
            //该活动是否收费
            String isFree = bizActivityApplyMapper.selectIsFreeActivity(activity.getId());
            //判断当前用户是否报名
            ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),activity.getId());
            if("0".equals(isFree)){
                //收费
                if(applyInfo != null){
                    //报名成功
                    if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("2");
                    }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{//不收费
                if(applyInfo != null){
                    if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        //报名成功
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(activityVoList);
    }


    /**
     * 查询小组活动贴列表
     * @param projectId
     * @return
     */
    public ObjectRestResponse<List<ActivityVo>> getHoodActivityList(String projectId, Integer page, Integer limit){
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<ActivityVo> activityVoList = bizActivityMapper.selectHoodActivityList(projectId,startIndex,limit);
        for (ActivityVo activity : activityVoList){
            //该活动是否收费
            String isFree = bizActivityApplyMapper.selectIsFreeActivity(activity.getId());
            //判断当前用户是否报名
            ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),activity.getId());
            if("0".equals(isFree)){
                //收费
                if(applyInfo != null){
                    //报名成功
                    if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("2");
                    }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{//不收费
                if(applyInfo != null){
                    if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        //报名成功
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(activityVoList);
    }



    /**
     * 查询当前项目下的活动列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ActivityVo>> getAppActivityList(String projectId, Integer page, Integer limit){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 5;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        Config config = ConfigService.getConfig("ace-app");
        String day = config.getProperty("activity.day", "");
        if(day == null || StringUtils.isEmpty(day)){
            day = "7";
        }
        List<ActivityVo> activityVoList = bizActivityMapper.selectActivityListByProject(day,projectId, startIndex, limit);
        for (ActivityVo activity : activityVoList){
            //活动封面
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(activity.getActivitCcover());
            list.add(imgInfo);
            activity.setActivitCcoverList(list);
            //报活动名人头像
            List<ImgInfo> imgInfoList = bizActivityMapper.selectApplyPhotoByThree(activity.getId());
            for(ImgInfo url:imgInfoList){
                if("".equals(url.getUrl())){
                    url.setUrl(getRandomPhoto());
                }
            }
            activity.setImgList(imgInfoList);
            //该活动是否收费
            String isFree = bizActivityApplyMapper.selectIsFreeActivity(activity.getId());
            //判断当前用户是否报名
            ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),activity.getId());
            if("0".equals(isFree)){
                //收费
                if(applyInfo != null){
                    //报名成功
                    if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("2");
                    }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{//不收费
                if(applyInfo != null){
                    if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        //报名成功
                        activity.setApplyStatus("3");
                    }else{
                        activity.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activity.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activity.getPersonNum()) != -1){
                            if(Integer.parseInt(activity.getPersonNum()) <= activity.getApplyNum()){
                                //报名已报满
                                activity.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activity.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activity.setApplyStatus("4");
                        }
                        if("3".equals(activity.getActivityStatus())){
                            //活动已过期
                            activity.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(activityVoList);
    }


    /**
     * 查询小组活动详情
     * @param id
     * @return
     */
    public ObjectRestResponse<ActivityInfo> getActivityInfo(String id){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(id, BaseContextHandler.getUserID());
        if(activityInfo == null){
            activityInfo = new ActivityInfo();
        }else{
            //判断用户活动报名状态
            //该活动是否收费
            String isFree = bizActivityApplyMapper.selectIsFreeActivity(id);
            //判断当前用户是否报名
            ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),id);
            if("0".equals(isFree)){
               //收费
                if(applyInfo != null){
                    //报名成功
                    if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activityInfo.setApplyStatus("2");
                    }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        activityInfo.setApplyStatus("3");
                    }else{
                        activityInfo.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activityInfo.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activityInfo.getPersonNum()) != -1){
                            if(Integer.parseInt(activityInfo.getPersonNum()) <= activityInfo.getApplyNum()){
                                //报名已报满
                                activityInfo.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activityInfo.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activityInfo.setApplyStatus("4");
                        }
                        if(sdf.parse(activityInfo.getEndTime()).getTime() < new Date().getTime()){
                            //活动已过期
                            activityInfo.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{//不收费
                if(applyInfo != null){
                    if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                        //报名成功
                        activityInfo.setApplyStatus("3");
                    }else{
                        activityInfo.setApplyStatus("1");
                    }
                }else{
                    //未报名
                    activityInfo.setApplyStatus("1");
                    try {
                        if(Integer.parseInt(activityInfo.getPersonNum()) != -1){
                            if(Integer.parseInt(activityInfo.getPersonNum()) <= activityInfo.getApplyNum()){
                                //报名已报满
                                activityInfo.setApplyStatus("6");
                            }
                        }
                        if(sdf.parse(activityInfo.getApplyEndTime()).getTime() < new Date().getTime()){
                            //报名已截止
                            activityInfo.setApplyStatus("4");
                        }
                        if(sdf.parse(activityInfo.getEndTime()).getTime() < new Date().getTime()){
                            //活动已过期
                            activityInfo.setApplyStatus("5");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            //该活动是否可取消报名
            String isCancel = bizActivityApplyMapper.selectisCancelActivity(id);
            try {
                if("1".equals(isCancel)){
                    if(DateUtils.secondBetween(new Date(),DateUtils.addHours(sdf.parse(activityInfo.getBegTime()),-Integer.parseInt(activityInfo.getCancelTime()))) > 0){
                        isCancel = "1";
                    } else{
                        isCancel = "0";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            activityInfo.setIsCancel(isCancel);
            //活动封面
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(activityInfo.getActivitCcover());
            list.add(imgInfo);
            activityInfo.setActivitCcoverList(list);
            //报活动名人头像
            List<ImgInfo> imgInfoList = bizActivityMapper.selectApplyPhoto(id);
            for(ImgInfo url:imgInfoList){
                if("".equals(url.getUrl())){
                    url.setUrl(getRandomPhoto());
                }
            }
            activityInfo.setImgList(imgInfoList);
            /*//活动报名人数
            int applyNum = bizActivityMapper.selectActivityApplyNum(id);
            activityInfo.setApplyNum(applyNum);*/
            //帖子评论数
            int commentNum = bizCommentMapper.selectCommentCount(id);
            activityInfo.setCommentNum(commentNum);
            if("-1".equals(activityInfo.getPersonNum())){
                activityInfo.setPersonNum("无上限");
            }
            //判断用户当前角色
            CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
            if(userInfo != null){
                if("4".equals(userInfo.getIdentityType())){
                    //游客
                    activityInfo.setIdentityType("1");
                }else{
                    activityInfo.setIdentityType("0");
                }
            }else{
                activityInfo.setIdentityType("1");
            }
            if(bizCommunityTopicMapper.selectIsOperationByUserId(BaseContextHandler.getUserID()) > 0){
                activityInfo.setIdentityType("2");
            }
        }
        return ObjectRestResponse.ok(activityInfo);
    }

    /**
     * 获取随机头像
     * @return
     */
    private String getRandomPhoto() {
        String userPhoto = "";
        List<String> list = Stream.of("http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo3@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo4@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo5@2x.png",
                "http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo6@2x.png").collect(toList());
        Random random = new Random();
        int n = random.nextInt(list.size());
        userPhoto = list.get(n);
        return userPhoto;
    }



    /**
     * 活动报名
     * @param activityId
     * @return
     */
    public ObjectRestResponse saveApplyUser(String activityId){
        ObjectRestResponse msg = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        //判断用户当前角色
        CurrentUserInfosVo userInfosvo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if("4".equals(userInfosvo.getIdentityType())){
            msg.setStatus(201);
            msg.setMessage("请前去选择身份验证!");
            return msg;
        }
        ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(activityId, BaseContextHandler.getUserID());
        if(activityInfo != null){
            if("1".equals(activityInfo.getType())){
                //查询用户是否加入该活动所发的小组
                String isGroupMember = bizActivityMapper.selectIsGroupMember(userId,activityId);
                if(isGroupMember == null || "".equals(isGroupMember)){
                    msg.setStatus(201);
                    msg.setMessage("请前去加入该活动所属小组!");
                    return msg;
                }
            }
        }
        int count = bizActivityMapper.selectIsApplyByUser(userId,activityId);
        if(count > 1){
            msg.setStatus(201);
            msg.setMessage("该活动已报名!");
            return msg;
        }
        UserInfo userInfo = bizActivityApplyMapper.selectUserInfo(userId);
        //查询该活动是否免费
        String isFree = bizActivityApplyMapper.selectIsFreeActivity(activityId);
        BizActivityApply apply = new BizActivityApply();
        String id = UUIDUtils.generateUuid();
        apply.setId(id);
        apply.setUserId(userId);
        if(userInfo != null){
            apply.setUserName(userInfo.getName());
            apply.setUserMobile(userInfo.getTel());
        }
        apply.setActivityId(activityId);
        apply.setComeFrom("1");
        if("0".equals(isFree)){
            //收费
            apply.setApplyStatus("0");//未报名
            apply.setPayStatus("1");//待支付
        }else{
            apply.setApplyStatus("1");
            apply.setPayStatus("2");
        }
        apply.setStatus("1");
        apply.setCreateBy(BaseContextHandler.getUserID());
        apply.setCreateTime(new Date());
        apply.setTimeStamp(new Date());
        if(bizActivityApplyMapper.insertSelective(apply) < 0){
            msg.setStatus(201);
            msg.setMessage("报名失败!");
            return msg;
        }else{
            if("0".equals(isFree)){
                //收费活动
                msg.setStatus(250);
                UserBillOutVo userBillOutVo = userActivityOrder(activityId).getData();
                msg.setData(userBillOutVo);
                BizActivityApply updateApply = new BizActivityApply();
                updateApply.setId(id);
                updateApply.setSubId(userBillOutVo.getSubId());
                updateApply.setTimeStamp(new Date());
                updateApply.setModifyBy(BaseContextHandler.getUserID());
                updateApply.setModifyTime(new Date());
                bizActivityApplyMapper.updateByPrimaryKeySelective(updateApply);
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 报名活动下单
     * @param activityId
     * @return
     */
   public ObjectRestResponse<UserBillOutVo> userActivityOrder(String activityId) {
       ObjectRestResponse response = new ObjectRestResponse();
       String userId = BaseContextHandler.getUserID();
       BaseAppClientUser user = baseAppClientUserBiz.getUserById(userId);
       String busId = BusinessConstant.getActivityBusId();
       ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(activityId, userId);
       SubInVo subInVo = new SubInVo();
       String companyId = bizActivityMapper.selectCompanyIdById(activityId);
       if(activityInfo != null){
           subInVo.setBusId(busId);
           subInVo.setDescription(activityInfo.getTitle());
           subInVo.setActualCost(new BigDecimal(activityInfo.getActCost()));
           subInVo.setReceivableCost(new BigDecimal(activityInfo.getActCost()));
           subInVo.setProductCost(new BigDecimal(activityInfo.getActCost()));
           subInVo.setExpressCost(new BigDecimal(0));
           subInVo.setDiscountCost(new BigDecimal(0));
           subInVo.setProjectId(activityInfo.getProjectId());
           subInVo.setContactName(user.getNickname());
           subInVo.setContactTel(user.getMobilePhone());
           subInVo.setUserId(userId);
           subInVo.setCompanyId(companyId);
       }
       //1.创建订单
       BizSubscribe subscribe = bizSubBiz.createSubscribe(subInVo).getData();
       if (subscribe != null) {
           //维护订单产品表
           BizSubProduct product = new BizSubProduct();
           product.setId(UUIDUtils.generateUuid());
           product.setProductId(busId);
           product.setSubId(subscribe.getId());
           product.setProductName(activityInfo.getTitle());
           product.setSpecId(busId);
           product.setSubNum(1);
           product.setPrice(new BigDecimal(1));
           product.setSpecName("默认");
           product.setCost(new BigDecimal(activityInfo.getActCost()));
           product.setImgId(activityInfo.getActivitCcover());
           product.setCreateBy(BaseContextHandler.getUserID());
           product.setCreateTime(new Date());
           product.setTimeStamp(new Date());
           bizSubProductMapper.insertSelective(product);
           //维护订单账单表
           BizAccountBook bizAccountBook = new BizAccountBook();
           bizAccountBook.setId(UUIDUtils.generateUuid());
           bizAccountBook.setPayStatus("1");
           bizAccountBook.setSubId(subscribe.getId());
           String actualId = UUIDUtils.generateUuid();
           bizAccountBook.setActualId(actualId);
           bizAccountBook.setPayId(userId);
           bizAccountBook.setActualCost(new BigDecimal(activityInfo.getActCost()));
           bizAccountBook.setTimeStamp(DateTimeUtil.getLocalTime());
           bizAccountBook.setCreateBy(BaseContextHandler.getUserID());
           bizAccountBook.setCreateTime(DateTimeUtil.getLocalTime());
           bizAccountBookMapper.insertSelective(bizAccountBook);
           //返回结果参数
           UserBillOutVo userBillOutVo = new UserBillOutVo();
           userBillOutVo.setActualId(actualId);
           userBillOutVo.setSubId(subscribe.getId());
           userBillOutVo.setActualPrice(activityInfo.getActCost());
           userBillOutVo.setTitle(subscribe.getTitle());
           response.setData(userBillOutVo);
           log.info("支付信息：{}",userBillOutVo.toString());
       }
        return response;
    }

    /**
     * 更新活动报名,支付状态
     * @param subId
     * @return
     */
    public ObjectRestResponse updateActivityApplyStatus(String subId){
        ObjectRestResponse msg = new ObjectRestResponse();
        //更新活动报名,支付状态
        int temp = bizActivityMapper.updateActivityApplyStatus(BaseContextHandler.getUserID(),subId);
        if (temp != 1) {
            log.error("update product sales error");
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 取消报名
     * @param activityId
     * @return
     */
    public ObjectRestResponse cancelApply(String activityId){
        ObjectRestResponse msg = new ObjectRestResponse();
        //查询该活动是否免费
        String isFree = bizActivityApplyMapper.selectIsFreeActivity(activityId);
        if("0".equals(isFree)){
            //收费
            //最新一条报名信息
            ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),activityId);
            if(applyInfo != null){
                if("1".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                    //已报名待支付后取消报名
                    if(bizActivityApplyMapper.updateActivityApplyById("5",BaseContextHandler.getUserID(),activityId) < 0){
                        msg.setStatus(201);
                        msg.setMessage("取消报名失败!");
                        return msg;
                    }
                }else if("2".equals(applyInfo.getPayStatus()) && "1".equals(applyInfo.getApplyStatus())){
                    //报名成功后取消报名
                    /* if(bizActivityApplyMapper.updateActivityApplyById("3",BaseContextHandler.getUserID(),activityId) < 0){
                        msg.setStatus(201);
                        msg.setMessage("取消报名失败!");
                        return msg;
                    }else{

                    }*/
                    DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
                    doOperateByTypeVo.setId(applyInfo.getSubId());
                    doOperateByTypeVo.setHandleBy(BaseContextHandler.getUserID());
                    doOperateByTypeVo.setOperateType(OperateConstants.OperateType.REFUND.toString());
                    ObjectRestResponse objectRestResponse = bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);
                    if(objectRestResponse.getStatus() == 200){
                        //退款中  修改报名状态
                        bizActivityMapper.updateActivityRefundStatus(BaseContextHandler.getUserID(),applyInfo.getId());
                        //插入退款记录
                        RefundInfoVo refundInfoVo = new RefundInfoVo();
                        refundInfoVo.setSubId(applyInfo.getSubId());
                        refundInfoVo.setUserType("1");
                        bizRefundAuditBiz.applyRefund(refundInfoVo);
                    }else{
                        msg.setStatus(201);
                        msg.setMessage("取消报名失败!");
                        return msg;
                    }
                }
            }

        }else{
            //免费
            if(bizActivityApplyMapper.updateActivityApplyById("5",BaseContextHandler.getUserID(),activityId) < 0){
                msg.setStatus(201);
                msg.setMessage("取消报名失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询活动报名列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ApplyVo>> getApplyList(String id, Integer page, Integer limit){
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
        List<ApplyVo> applyVoList = bizActivityApplyMapper.selectActivityApplyList(id, startIndex, limit);
        for(ApplyVo applyVo: applyVoList){
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            if(applyVo.getPhoto().equals("")){
                imgInfo.setUrl(getRandomPhoto());
            }else{
                imgInfo.setUrl(applyVo.getPhoto());
            }
            list.add(imgInfo);
            applyVo.setPhotoList(list);
        }
        if(applyVoList == null || applyVoList.size() == 0){
            applyVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(applyVoList);
    }





    /**
     * 查询我的活动
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<ActivityVo>> getUserActivityList(Integer page, Integer limit){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
        List<ActivityVo> activityVoList = bizActivityApplyMapper.selectActivityListByUser(BaseContextHandler.getUserID(),startIndex,limit);
        for (ActivityVo activity : activityVoList){
            //活动封面
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(activity.getActivitCcover());
            list.add(imgInfo);
            activity.setActivitCcoverList(list);
            //报活动名人头像
            List<ImgInfo> imgInfoList = bizActivityMapper.selectApplyPhotoByThree(activity.getId());
            for(ImgInfo url:imgInfoList){
                if("".equals(url.getUrl())){
                    url.setUrl(getRandomPhoto());
                }
            }
            activity.setImgList(imgInfoList);
            //该活动是否收费
            String isFree = bizActivityApplyMapper.selectIsFreeActivity(activity.getId());
            //判断当前用户是否报名
            //ApplyInfo applyInfo = bizActivityApplyMapper.selectActivityApplyStatus(BaseContextHandler.getUserID(),activity.getId());
            if("0".equals(isFree)){
                //收费
                //报名成功
                if("2".equals(activity.getStatus())){
                    activity.setApplyStatus("7");
                }else if("3".equals(activity.getStatus())){
                    activity.setApplyStatus("8");
                }else if("4".equals(activity.getStatus())){
                    activity.setApplyStatus("9");
                }else{
                    activity.setApplyStatus("3");
                }
            }else{//不收费
                if("1".equals(activity.getStatus())){
                    //报名成功
                    activity.setApplyStatus("3");
                }else{
                    // activity.setApplyStatus("1");
                }
            }

        }
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(activityVoList);
    }

    /**
     * 判断该用户是否签到
     * @param userId
     * @param id
     * @return
     */
    public ObjectRestResponse selectIsSigntype(String userId,String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(userId)) {
            userId = BaseContextHandler.getUserID();
        }
        Map<String,String> dataMap = new HashMap<>();
        String status = null;
        String applyId = bizActivityMapper.selectIsSigntype(userId, id);
        if(applyId == null || "".equals(applyId)){
            status = "0";
        }else{
            int total = bizActivityMapper.selectIsSigntypeById(userId, id);
            if(total > 0){
                status = "2";
            }else{
                if(bizActivityMapper.updatesignTypeStatus(BaseContextHandler.getUserID(),applyId) > 0){
                    status = "1";
                }
            }
        }
        dataMap.put("status",status);
        msg.setData(dataMap);
        return msg;
    }






}