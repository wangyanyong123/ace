package com.github.wxiaoqi.security.jinmao.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.SysHomepageAd;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.SysHomepageAdMapper;
import com.github.wxiaoqi.security.jinmao.mapper.SysHomepageAdProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.in.AdHomePageParam;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.in.adOpratingParam;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdAppHomePageList;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageInfo;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * app广告位表
 *
 * @author huangxl
 * @Date 2019-05-27 09:55:49
 */
@Service
public class SysHomepageAdBiz extends BusinessBiz<SysHomepageAdMapper,SysHomepageAd> {

    private Logger logger = LoggerFactory.getLogger(SysHomepageAdBiz.class);
    @Autowired
    private SysHomepageAdMapper sysHomepageAdMapper;
    @Autowired
    private SysHomepageAdProjectMapper sysHomepageAdProjectMapper;
    @Autowired
    private ToolFegin toolFeign;

    /**
     * 查询app广告业列表
     * @param isPublish
     * @param searchVal
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public List<AdHomePageVo> getAdHomePageList(String isPublish,String searchVal,String projectId,Integer page, Integer limit){
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
        List<String> adList = sysHomepageAdMapper.selectAdOverdue();
        //更新过期广告
        if (adList.size() != 0) {
            sysHomepageAdMapper.updateAdHomeStatus(adList);
        }
        List<AdHomePageVo> adHomePageVoList = sysHomepageAdMapper.selectAdHomePageList(isPublish, searchVal, projectId, startIndex, limit);
        if(adHomePageVoList == null || adHomePageVoList.size() == 0){
            adHomePageVoList = new ArrayList<>();
        }/*else{
            for (AdHomePageVo vo : adHomePageVoList){
                List<String> projectList = sysHomepageAdMapper.selectProjectNameByAdId(vo.getId());
                if(projectList != null){
                    StringBuilder sb = new StringBuilder();
                    String projectName = "";
                    for(String obj : projectList){
                        sb.append(obj+",");
                    }
                    projectName = sb.substring(0,sb.length()-1);
                    vo.setProjectName(projectName);
                }
            }
        }*/
        return adHomePageVoList;
    }

    /**
     * 询app广告业列表数量
     * @param isPublish
     * @param searchVal
     * @param projectId
     * @return
     */
    public  int selectAdHomePageCount(String isPublish,String searchVal,String projectId){
        return sysHomepageAdMapper.selectAdHomePageCount(isPublish, searchVal, projectId);
    }


    /**
     * 添加app广告
     * @param param
     * @return
     */
    public ObjectRestResponse addAdHomePage(AdHomePageParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(param == null){
            msg.setStatus(101);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isAnyoneEmpty(param.getTitle(),param.getBeginTime(),param.getEndTime(),param.getStopTime(),param.getSort())){
            msg.setStatus(102);
            msg.setMessage("参数不能为空");
            return msg;
        }
        /*if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(103);
            msg.setMessage("项目不能为空");
            return msg;
        }*/
        if(param.getAndroidImg() == null || param.getAndroidImg().size() == 0){
            msg.setStatus(104);
            msg.setMessage("安卓图片不能为空");
            return msg;
        }
        if(param.getIosImg() == null || param.getIosImg().size() == 0){
            msg.setStatus(104);
            msg.setMessage("ios图片不能为空");
            return msg;
        }
        SysHomepageAd ad = new SysHomepageAd();
        String id = UUIDUtils.generateUuid();
        ad.setId(id);
        ad.setTitle(param.getTitle());
        try {
            ad.setBeginTime(sdf.parse(param.getBeginTime()));
            ad.setEndTime(sdf.parse(param.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("处理时间异常",e);
        }
        ad.setSort(Integer.parseInt(param.getSort()));
        ad.setStopTime(Integer.parseInt(param.getStopTime()));
        ad.setBusinessType("1");
        for(ImgInfo temp: param.getAndroidImg()){
            if(StringUtils.isNotEmpty(temp.getUrl())){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SYSTEM);
                if(objectRestResponse.getStatus()==200){
                    ad.setAndroidImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //ad.setAndroidImg(temp.getUrl());
        }
        for(ImgInfo temp: param.getIosImg()){
            if(StringUtils.isNotEmpty(temp.getUrl())){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SYSTEM);
                if(objectRestResponse.getStatus()==200){
                    ad.setIosImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //ad.setIosImg(temp.getUrl());
        }
        ad.setIsPublish("1");
        ad.setTimeStamp(new Date());
        ad.setCreateBy(BaseContextHandler.getUserID());
        ad.setCreateTime(new Date());
        if(sysHomepageAdMapper.insertSelective(ad) > 0){
            //插入项目
           /* for (Map<String,String> map : param.getProjectVo()){
                SysHomepageAdProject project = new SysHomepageAdProject();
                project.setId(UUIDUtils.generateUuid());
                project.setAdId(id);
                project.setProjectId(map.get("id"));
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateTime(new Date());
                project.setTimeStamp(new Date());
                sysHomepageAdProjectMapper.insertSelective(project);
            }*/
        }else{
            msg.setStatus(105);
            msg.setMessage("添加app广告失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询app广告业详情
     * @param id
     * @return
     */
    public List<AdHomePageInfo>  getAdHomePageInfo(String id){
        List<AdHomePageInfo> result = new ArrayList<>();
        AdHomePageInfo adHomePageInfo = sysHomepageAdMapper.selectAdHomePageInfo(id);
        if(adHomePageInfo != null){
            List<ImgInfo> androidImages = new ArrayList<>();
            ImgInfo  androidImageInfo = new ImgInfo();
            androidImageInfo.setUrl(adHomePageInfo.getAndroidImage());
            androidImages.add(androidImageInfo);
            adHomePageInfo.setAndroidImg(androidImages);

            List<ImgInfo> iosImages = new ArrayList<>();
            ImgInfo  iosImageInfo = new ImgInfo();
            iosImageInfo.setUrl(adHomePageInfo.getIosImage());
            iosImages.add(androidImageInfo);
            adHomePageInfo.setAndroidImg(iosImages);

            //adHomePageInfo.setProjectVo(sysHomepageAdMapper.selectAdProjectVo(id));
        }
        result.add(adHomePageInfo);
        return result;
    }


    /**
     * 编辑app广告
     * @param param
     * @return
     */
    public ObjectRestResponse updateAdHomePage(AdHomePageParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(param == null){
            msg.setStatus(101);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isAnyoneEmpty(param.getTitle(),param.getBeginTime(),param.getEndTime(),param.getStopTime(),param.getSort())){
            msg.setStatus(102);
            msg.setMessage("参数不能为空");
            return msg;
        }
      /*  if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(103);
            msg.setMessage("项目不能为空");
            return msg;
        }*/
        if(param.getAndroidImg() == null || param.getAndroidImg().size() == 0){
            msg.setStatus(104);
            msg.setMessage("安卓图片不能为空");
            return msg;
        }
        if(param.getIosImg() == null || param.getIosImg().size() == 0){
            msg.setStatus(104);
            msg.setMessage("ios图片不能为空");
            return msg;
        }
        SysHomepageAd ad = new SysHomepageAd();
        AdHomePageInfo adHomePageInfo = sysHomepageAdMapper.selectAdHomePageInfo(param.getId());
        if(adHomePageInfo != null){
            if("2".equals(adHomePageInfo.getIsPublish())){
                msg.setStatus(109);
                msg.setMessage("发布中的广告不能被修改！");
                return msg;
            } else if ("4".equals(adHomePageInfo.getIsPublish())) {
                msg.setStatus(501);
                msg.setMessage("已过期的广告不能被修改！");
                return msg;
            }
            BeanUtils.copyProperties(adHomePageInfo,ad);
            ad.setTitle(param.getTitle());
            try {
                ad.setBeginTime(sdf.parse(param.getBeginTime()));
                ad.setEndTime(sdf.parse(param.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("处理时间异常",e);
            }
            ad.setSort(Integer.parseInt(param.getSort()));
            ad.setStopTime(Integer.parseInt(param.getStopTime()));
            for(ImgInfo temp: param.getAndroidImg()){
                if(StringUtils.isNotEmpty(temp.getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SYSTEM);
                    if(objectRestResponse.getStatus()==200){
                        ad.setAndroidImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //ad.setAndroidImg(temp.getUrl());
            }
            for(ImgInfo temp: param.getIosImg()){
                if(StringUtils.isNotEmpty(temp.getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(temp.getUrl(), DocPathConstant.SYSTEM);
                    if(objectRestResponse.getStatus()==200){
                        ad.setIosImg(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //ad.setIosImg(temp.getUrl());
            }
            ad.setTimeStamp(new Date());
            ad.setModifyBy(BaseContextHandler.getUserID());
            ad.setModifyTime(new Date());
            if(sysHomepageAdMapper.updateByPrimaryKeySelective(ad) > 0){
                //插入项目
                /*sysHomepageAdMapper.delAdProjectByAdId(param.getId());
                for (Map<String,String> map : param.getProjectVo()){
                    SysHomepageAdProject project = new SysHomepageAdProject();
                    project.setId(UUIDUtils.generateUuid());
                    project.setAdId(param.getId());
                    project.setProjectId(map.get("id"));
                    project.setCreateBy(BaseContextHandler.getUserID());
                    project.setCreateTime(new Date());
                    project.setTimeStamp(new Date());
                    sysHomepageAdProjectMapper.insertSelective(project);
                }*/
            }else{
                msg.setStatus(105);
                msg.setMessage("编辑app广告失败!");
                return msg;
            }
        }else{
            msg.setStatus(106);
            msg.setMessage("查无此详情!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 删除,发布,撤回操作
     * @param param
     * @return
     */
    public ObjectRestResponse opratingAdHomePage(adOpratingParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(101);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getId())){
            msg.setStatus(102);
            msg.setMessage("id不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getPStatus())){
            msg.setStatus(102);
            msg.setMessage("操作参数不能为空");
            return msg;
        }
        if("0".equals(param.getPStatus())){
            AdHomePageInfo adHomePageInfo = sysHomepageAdMapper.selectAdHomePageInfo(param.getId());
            if(adHomePageInfo != null) {
                if ("2".equals(adHomePageInfo.getIsPublish())) {
                    msg.setStatus(109);
                    msg.setMessage("发布中的广告不能被删除！");
                    return msg;
                }
            }
        }
        if(sysHomepageAdMapper.operatingAdById(param.getPStatus(),BaseContextHandler.getUserID(),param.getId()) > 0){
            msg.setMessage("操作成功");
        }else{
            msg.setStatus(106);
            msg.setMessage("操作失败");
            return msg;
        }
        msg.setData("1");
        return msg;
    }


    /**
     * 获取app广告
     * @return
     */
    public ObjectRestResponse<AdAppHomePageList> getAdAppHomePageList(){
        //定义广告初始数量
        int num = 5;
        //获取Apollo配置
        Config config = ConfigService.getConfig("ace-jinmao");
        String adNum = config.getProperty("ad.limitNum", "");
        if(!StringUtils.isEmpty(adNum)){
            try {
                num = Integer.parseInt(adNum);
            } catch (NumberFormatException e) {

            }
        }
        List<AdHomePageInfo> adList = sysHomepageAdMapper.getAppHomepageAdList(num);
        int totalTime = 0;
        if(adList != null && adList.size()>0) {
            for (AdHomePageInfo info : adList){
                totalTime = totalTime + Integer.parseInt(info.getStopTime());
            }
        }
        AdAppHomePageList adAppHomePageList = new AdAppHomePageList();
        adAppHomePageList.setTotalTime(totalTime);
        adAppHomePageList.setAdList(adList);
        return ObjectRestResponse.ok(adAppHomePageList);
    }

}