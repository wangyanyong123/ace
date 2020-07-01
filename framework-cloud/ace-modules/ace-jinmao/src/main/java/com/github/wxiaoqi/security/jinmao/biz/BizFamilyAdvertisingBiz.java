package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyAdvertising;
import com.github.wxiaoqi.security.jinmao.entity.BizFamilyAdvertisingProject;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProductInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.ActivityInfo;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.in.SaveFamilyAdParam;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdVo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.ObjectInfo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.PostsInfo;
import com.github.wxiaoqi.security.jinmao.vo.reservat.out.ReservationInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 家里人广告位
 *
 * @author huangxl
 * @Date 2019-08-12 14:50:41
 */
@Service
public class BizFamilyAdvertisingBiz extends BusinessBiz<BizFamilyAdvertisingMapper,BizFamilyAdvertising> {

    @Autowired
    private BizFamilyAdvertisingMapper bizFamilyAdvertisingMapper;
    @Autowired
    private BizFamilyAdvertisingProjectMapper bizFamilyAdvertisingProjectMapper;
    @Autowired
    private BizFamilyPostsBiz bizFamilyPostsBiz;
    @Autowired
    private BizChamberTopicBiz bizChamberTopicBiz;
    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizCommunityTopicMapper bizCommunityTopicMapper;
    @Autowired
    private BizActivityMapper bizActivityMapper;
    @Autowired
    private BizForumPostsMapper bizForumPostsMapper;
    @Autowired
    private ToolFegin toolFeign;
    @Autowired
    private BizProductMapper productMapper;
    @Autowired
    private BizReservationMapper reservationMapper;


    /**
     * 查询家里人广告位列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<FamilyAdVo> getFamilyAdList(String projectId,String searchVal,Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<FamilyAdVo> familyAdVoList = bizFamilyAdvertisingMapper.selectFamilyAdList(projectId, searchVal, startIndex, limit);
        if(familyAdVoList !=null && familyAdVoList.size() > 0){
            for (FamilyAdVo adVo : familyAdVoList){
                StringBuilder sb = new StringBuilder();
                String projectNames = "";
                List<String> projectNameList =  bizFamilyAdvertisingProjectMapper.selectProjectNameByAdId(adVo.getId());
                if(projectNameList != null && projectNameList.size() > 0){
                    for (String projectName : projectNameList){
                        sb.append(projectName+",");
                    }
                    if(sb.toString() != null && sb.length() >0){
                        projectNames = sb.substring(0,sb.length()-1);
                    }
                    adVo.setProjectNames(projectNames);
                }
            }
        }
        return familyAdVoList;
    }

    /**
     * 查询家里人广告位列表数量
     * @param projectId
     * @param searchVal
     * @return
     */
    public int selectFamilyAdCount(String projectId,String searchVal){
        return bizFamilyAdvertisingMapper.selectFamilyAdCount(projectId, searchVal);
    }

    /**
     * 查询社区话题列表
     * @param projectId
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<CommunityTopicVo> getCommunityTopicList(String projectId,String isTop,String startTime,String endTime,
                                                        String searchVal,Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<String> projectNameList = null;
        if(projectId != null && !StringUtils.isEmpty(projectId)){
            List<String> projectIdList = Arrays.asList(projectId.split(","));
            projectNameList = bizFamilyAdvertisingProjectMapper.selectProjectNameList(projectIdList);
        }
        List<CommunityTopicVo> newList = new ArrayList<>();
        List<CommunityTopicVo> communityTopicVoList =  bizCommunityTopicBiz.getCommunityTopicList(null,"1",isTop,startTime,endTime,searchVal,0,9999);
        if(communityTopicVoList != null && communityTopicVoList.size() >0){
            for(CommunityTopicVo topicVo : communityTopicVoList){
                int temp = 1;
                for(String str : projectNameList){
                    if(topicVo.getProjectNames().contains(str)){
                        temp = 1;
                    }else{
                        temp = 0;
                        break;
                    }
                }
                if(temp ==1){
                    newList.add(topicVo);
                }
            }
        }
        return newList;
    }

    /**
     * 查询社区话题列表数量
     * @param projectId
     * @param isTop
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    public int selectCommunityTopicCount(String projectId,String isTop,String startTime,String endTime,
                                         String searchVal){
        List<String> projectNameList = null;
        if(projectId != null && !StringUtils.isEmpty(projectId)){
            List<String> projectIdList = Arrays.asList(projectId.split(","));
            projectNameList = bizFamilyAdvertisingProjectMapper.selectProjectNameList(projectIdList);
        }
        List<CommunityTopicVo> newList = new ArrayList<>();
        List<CommunityTopicVo> communityTopicVoList =  bizCommunityTopicBiz.getCommunityTopicList(null,"1",isTop,startTime,endTime,searchVal,0,9999);
        if(communityTopicVoList != null && communityTopicVoList.size() >0){
            for(CommunityTopicVo topicVo : communityTopicVoList){
                int temp = 1;
                for(String str : projectNameList){
                    if(topicVo.getProjectNames().contains(str)){
                        temp = 1;
                    }else{
                        temp = 0;
                        break;
                    }
                }
                if(temp ==1){
                    newList.add(topicVo);
                }
            }
        }
        int num = newList.size();
        return num;
    }



    /**
     * 查询邻里活动活动列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ActivityVo> getActivityList(String projectId,String startTime, String endTime,String searchVal, Integer page, Integer limit){
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
        List<ActivityVo> activityVoList = bizFamilyAdvertisingMapper.selectActivityList(projectId,startTime,endTime, searchVal, startIndex, limit);
        if(activityVoList == null || activityVoList.size() == 0){
            activityVoList = new ArrayList<>();
        }
        return activityVoList;
    }

    /**
     * 查询邻里活动数量
     * @param projectId
     * @param searchVal
     * @return
     */
    public int selectActivityCount(String projectId,String startTime, String endTime, String searchVal){
        int total = bizFamilyAdvertisingMapper.selectActivityCount(projectId,startTime,endTime, searchVal);
        return total;
    }


    /**
     * 保存家里人广告位
     * @param param
     * @return
     */
     public ObjectRestResponse saveFamilyAd(SaveFamilyAdParam param){
         ObjectRestResponse msg = new ObjectRestResponse();
         if(param == null){
             msg.setStatus(1001);
             msg.setMessage("参数不能为空!");
             return msg;
         }
         if(StringUtils.isAnyEmpty(param.getTitle(),param.getViewSort())){
             msg.setStatus(1002);
             msg.setMessage("标题/排序不能为空!");
             return msg;
         }
         if(param.getProjectVo() == null && param.getProjectVo().size() == 0){
             msg.setStatus(1003);
             msg.setMessage("项目不能为空!");
             return msg;
         }
         if(param.getAdImageList() == null && param.getAdImageList().size() == 0){
             msg.setStatus(1003);
             msg.setMessage("广告图片不能为空!");
             return msg;
         }
         String busn = "1";
         String busw = "2";
         if(busn.equals(param.getSkipBus())){
             if(StringUtils.isAnyEmpty(param.getBusClassify())){
                 msg.setStatus(1004);
                 msg.setMessage("业务类型不能为空!");
                 return msg;
             }
             if(param.getObjectVo() == null && param.getObjectVo().size() == 0){
                 msg.setStatus(1003);
                 msg.setMessage("业务对象不能为空!");
                 return msg;
             }
         }else if(busw.equals(param.getSkipBus())){
             if(StringUtils.isEmpty(param.getSkipUrl())){
                 msg.setStatus(1003);
                 msg.setMessage("跳转地址不能为空!");
                 return msg;
             }
         }
         BizFamilyAdvertising advertising = new BizFamilyAdvertising();
         String id = UUIDUtils.generateUuid();
         advertising.setId(id);
         advertising.setTitle(param.getTitle());
         advertising.setSkipBus(param.getSkipBus());
         if (param.getClassifyId() != null) {
             advertising.setClassifyId(param.getClassifyId());
         }
         if(busn.equals(param.getSkipBus())){
             advertising.setBusClassify(param.getBusClassify());
             advertising.setObjectId(param.getObjectVo().get(0).getId());
         }else if(busw.equals(param.getSkipBus())){
             advertising.setSkipUrl(param.getSkipUrl());
         }
         advertising.setViewSort(Integer.parseInt(param.getViewSort()));
         for (ImgInfo info : param.getAdImageList()){
             if(StringUtils.isNotEmpty(info.getUrl())){
                 ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(info.getUrl(), DocPathConstant.WEBPOSTS);
                 if(objectRestResponse.getStatus()==200){
                     advertising.setAdvertisingImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                 }
             }
             //advertising.setAdvertisingImg(info.getUrl());
         }
         advertising.setTimeStamp(new Date());
         advertising.setCreateTime(new Date());
         advertising.setCreateBy(BaseContextHandler.getUserID());
         if(bizFamilyAdvertisingMapper.insertSelective(advertising) > 0){
             for (ResultProjectVo temp : param.getProjectVo()){
                 BizFamilyAdvertisingProject project = new BizFamilyAdvertisingProject();
                 project.setId(UUIDUtils.generateUuid());
                 project.setAdvertId(id);
                 project.setProjectId(temp.getId());
                 project.setTimeStamp(new Date());
                 project.setCreateTime(new Date());
                 project.setCreateBy(BaseContextHandler.getUserID());
                 bizFamilyAdvertisingProjectMapper.insertSelective(project);
             }
         }else{
             msg.setStatus(105);
             msg.setMessage("保存家里人广告位失败!");
             return msg;
         }
         msg.setMessage("Operation succeed!");
         msg.setData("1");
         return msg;
     }


    /**
     * 查询家里人广告位详情
     * @param id
     * @return
     */
     public ObjectRestResponse<FamilyAdInfo> getFamilyAdInfo(String id){
         ObjectRestResponse msg = new ObjectRestResponse();
         if(StringUtils.isEmpty(id)){
             msg.setStatus(102);
             msg.setMessage("id不能为空!");
             return msg;
         }
         List<ObjectInfo> objectList = new ArrayList<>();
         ObjectInfo objectInfo = new ObjectInfo();
         FamilyAdInfo familyAdInfo = bizFamilyAdvertisingMapper.selectFamilyAdInfo(id);
         if(familyAdInfo != null){
             List<ResultProjectVo> projectVoList = bizFamilyAdvertisingProjectMapper.selectProjectInfoById(id);
             familyAdInfo.setProjectVo(projectVoList);
             //处理业务对象
             String busj = "1";
             String busy = "2";
             String bushl = "3";
             String buss = "4";
             String bust = "5";
             List<String> product = new ArrayList<>();
             product.add("6");
             product.add("7");
             product.add("8");
             String reserve = "9";
             if(busj.equals(familyAdInfo.getBusClassify())){
               ObjectRestResponse  faObject =  bizFamilyPostsBiz.getFamilyPostsInfo(familyAdInfo.getObjectId());
               if(faObject.getStatus() == 200){
                   FamilyPostsInfo familyPostsInfo =(FamilyPostsInfo)faObject.getData();
                   if(familyPostsInfo != null){
                       objectInfo.setId(familyAdInfo.getObjectId());
                       objectInfo.setName(familyPostsInfo.getContent());
                       objectList.add(objectInfo);
                   }
               }
             }else if(busy.equals(familyAdInfo.getBusClassify())){
                 ObjectRestResponse  chObject =  bizChamberTopicBiz.getChamberTopicInfo(familyAdInfo.getObjectId());
                 if(chObject.getStatus() == 200){
                     ChamberTopicInfo chamberTopicInfo =(ChamberTopicInfo)chObject.getData();
                     if(chamberTopicInfo != null){
                         objectInfo.setId(familyAdInfo.getObjectId());
                         objectInfo.setName(chamberTopicInfo.getContent());
                         objectList.add(objectInfo);
                     }
                 }
             }else if(bushl.equals(familyAdInfo.getBusClassify())){
                 ActivityInfo activityInfo = bizActivityMapper.selectActivityInfo(familyAdInfo.getObjectId());
                 if(activityInfo != null){
                     objectInfo.setId(familyAdInfo.getObjectId());
                     objectInfo.setName(activityInfo.getTitle());
                     objectList.add(objectInfo);
                 }
             }else if(buss.equals(familyAdInfo.getBusClassify())){
                 CommunityTopicInfo communityTopicInfo = bizCommunityTopicMapper.selectCommunityTopicInfo(familyAdInfo.getObjectId());
                 if(communityTopicInfo != null){
                     objectInfo.setId(familyAdInfo.getObjectId());
                     objectInfo.setName(communityTopicInfo.getTitle());
                     objectList.add(objectInfo);
                 }
             }else if(bust.equals(familyAdInfo.getBusClassify())){
                 PostsInfo postsInfo = bizForumPostsMapper.selectPostsInfo(familyAdInfo.getObjectId());
                 if(postsInfo != null){
                     objectInfo.setId(familyAdInfo.getObjectId());
                     objectInfo.setName(postsInfo.getTitle());
                     objectList.add(objectInfo);
                 }
             }else if(product.contains(familyAdInfo.getBusClassify())){
                 ResultProductInfoVo postsInfo = productMapper.selectProductInfo(familyAdInfo.getObjectId());
                 if(postsInfo != null){
                     objectInfo.setId(familyAdInfo.getObjectId());
                     objectInfo.setName(postsInfo.getProductName());
                     objectList.add(objectInfo);
                 }
             }else if(reserve.equals(familyAdInfo.getBusClassify())){
                 ReservationInfo postsInfo = reservationMapper.selectReservationInfo(familyAdInfo.getObjectId());
                 if(postsInfo != null){
                     objectInfo.setId(familyAdInfo.getObjectId());
                     objectInfo.setName(postsInfo.getName());
                     objectList.add(objectInfo);
                 }
             }
             familyAdInfo.setObjectVo(objectList);
         }
         return ObjectRestResponse.ok(familyAdInfo);
     }

    /**
     * 编辑家里人广告位
     * @param param
     * @return
     */
     public ObjectRestResponse updateFamilyAd(SaveFamilyAdParam param){
         ObjectRestResponse msg = new ObjectRestResponse();
         if(param == null){
             msg.setStatus(1001);
             msg.setMessage("参数不能为空!");
             return msg;
         }
         if(StringUtils.isAnyEmpty(param.getTitle(),param.getViewSort())){
             msg.setStatus(1002);
             msg.setMessage("标题/排序不能为空!");
             return msg;
         }
         if(param.getProjectVo() == null && param.getProjectVo().size() == 0){
             msg.setStatus(1003);
             msg.setMessage("项目不能为空!");
             return msg;
         }
         if(param.getAdImageList() == null && param.getAdImageList().size() == 0){
             msg.setStatus(1003);
             msg.setMessage("广告图片不能为空!");
             return msg;
         }
         String busn = "1";
         String busw = "2";
         if(busn.equals(param.getSkipBus())){
             if(StringUtils.isAnyEmpty(param.getBusClassify())){
                 msg.setStatus(1004);
                 msg.setMessage("业务类型不能为空!");
                 return msg;
             }
             if(param.getObjectVo() == null && param.getObjectVo().size() == 0){
                 msg.setStatus(1003);
                 msg.setMessage("业务对象不能为空!");
                 return msg;
             }
         }else if(busw.equals(param.getSkipBus())){
             if(StringUtils.isEmpty(param.getSkipUrl())){
                 msg.setStatus(1003);
                 msg.setMessage("跳转地址不能为空!");
                 return msg;
             }
         }
         FamilyAdInfo familyAdInfo = bizFamilyAdvertisingMapper.selectFamilyAdInfo(param.getId());
         if(familyAdInfo != null){
             BizFamilyAdvertising advertising = new BizFamilyAdvertising();
             BeanUtils.copyProperties(familyAdInfo, advertising);
             advertising.setTitle(param.getTitle());
             advertising.setSkipBus(param.getSkipBus());
             if (param.getClassifyId() != null) {
                 advertising.setClassifyId(param.getClassifyId());
             }
             if(busn.equals(param.getSkipBus())){
                 advertising.setBusClassify(param.getBusClassify());
                 advertising.setObjectId(param.getObjectVo().get(0).getId());
             }else if(busw.equals(param.getSkipBus())){
                 advertising.setSkipUrl(param.getSkipUrl());
             }
             advertising.setViewSort(Integer.parseInt(param.getViewSort()));
             for (ImgInfo info : param.getAdImageList()){
                 if(StringUtils.isNotEmpty(info.getUrl())){
                     ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(info.getUrl(), DocPathConstant.WEBPOSTS);
                     if(objectRestResponse.getStatus()==200){
                         advertising.setAdvertisingImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                     }
                 }
                 //advertising.setAdvertisingImg(info.getUrl());
             }
             advertising.setModifyTime(new Date());
             advertising.setModifyBy(BaseContextHandler.getUserID());
             if(bizFamilyAdvertisingMapper.updateByPrimaryKeySelective(advertising) > 0){
                 if(bizFamilyAdvertisingProjectMapper.deleteFamilyAdById(param.getId()) > 0){
                     for (ResultProjectVo temp : param.getProjectVo()){
                         BizFamilyAdvertisingProject project = new BizFamilyAdvertisingProject();
                         project.setId(UUIDUtils.generateUuid());
                         project.setAdvertId(param.getId());
                         project.setProjectId(temp.getId());
                         project.setTimeStamp(new Date());
                         project.setCreateTime(new Date());
                         project.setCreateBy(BaseContextHandler.getUserID());
                         bizFamilyAdvertisingProjectMapper.insertSelective(project);
                     }
                 }
             }else{
             msg.setStatus(1101);
             msg.setMessage("编辑家里人广告位失败!");
             return msg;
         }
     }else{
        msg.setStatus(1101);
        msg.setMessage("查无此详情!");
        return msg;
    }
        msg.setMessage("Operation succeed!");
        return msg;
     }


    /**
     * 删除家里人广告位
     * @param id
     * @return
     */
    public ObjectRestResponse delFamilyAdTag(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if (bizFamilyAdvertisingMapper.delFamilyAdById(id, BaseContextHandler.getUserID()) < 0) {
            msg.setStatus(102);
            msg.setMessage("删除家里人广告位失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;

    }





}