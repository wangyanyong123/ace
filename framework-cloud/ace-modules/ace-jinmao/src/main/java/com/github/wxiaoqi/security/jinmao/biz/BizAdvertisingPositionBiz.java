package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizAdvertisingPosition;
import com.github.wxiaoqi.security.jinmao.entity.BizAdvertisingProject;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BizAdvertisingPositionMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizAdvertisingProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.advertising.in.SaveAdvert;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.AdvertProjectInfo;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvert;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvertList;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 优选商城广告位
 *
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
@Service
public class BizAdvertisingPositionBiz extends BusinessBiz<BizAdvertisingPositionMapper,BizAdvertisingPosition> {

    private Logger logger = LoggerFactory.getLogger(BizAdvertisingPositionBiz.class);
    @Autowired
    private BizAdvertisingPositionMapper bizAdvertisingPositionMapper;
    @Autowired
    private BizAdvertisingProjectMapper bizAdvertisingProjectMapper;
    @Autowired
    private ToolFegin toolFeign;
    /**
     * 查询广告列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultAdvertList> getAdvertList(String projectId, String searchVal, Integer page, Integer limit) {
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
        List<ResultAdvertList> advertList = bizAdvertisingPositionMapper.selectAdvertList(projectId, searchVal, startIndex, limit);
        for (ResultAdvertList resultAdvertList : advertList) {
            List<AdvertProjectInfo> projectInfos = bizAdvertisingPositionMapper.selectProjectByAdvertId(resultAdvertList.getId());
            if (projectInfos != null && projectInfos.size() > 0) {
                String projectName = "";
                StringBuilder sb = new StringBuilder();
                for (AdvertProjectInfo projectInfo : projectInfos) {
                    sb.append(projectInfo.getProjectName() + ",");
                }
                projectName = sb.substring(0, sb.length() - 1);
                resultAdvertList.setProjectName(projectName);
            }
        }
        return advertList;
    }

    /**
     * 根据条件查询数量
     * @param searchVal
     * @return
     */
    public int selectAdvertCount(String projectId, String searchVal) {
        int total = bizAdvertisingPositionMapper.selectAdvertCount(projectId,searchVal);
        return total;
    }
    /**
     * 查询广告详情
     * @param id
     * @return
     */
    public List<ResultAdvert> getAdvertInfo(String id) {
        List<ResultAdvert> advertInfo = new ArrayList<>();
        ResultAdvert resultAdvert = bizAdvertisingPositionMapper.selectAdvertInfo(id);
        ProductListVo product = new ProductListVo();
        product.setProductId(resultAdvert.getProductId());
        product.setProductName(resultAdvert.getProductName());
        List<ProductListVo> productList = new ArrayList<>();
        productList.add(product);
        resultAdvert.setProductInfo(productList);
        List<AdvertProjectInfo> projectInfo = bizAdvertisingPositionMapper.selectProjectByAdvertId(id);
        if (resultAdvert != null) {
            if (resultAdvert.getAdvertisingImge()!=null) {
                List<ImgInfo> list = new ArrayList<>();
                ImgInfo imge = new ImgInfo();
                imge.setUrl(resultAdvert.getAdvertisingImge());
                list.add(imge);
                resultAdvert.setAdvertisingImg(list);
            }
            if (projectInfo != null) {
                resultAdvert.setProjectInfo(projectInfo);
            }else {
                resultAdvert.setAdvertisingImg(new ArrayList<>());
            }
            advertInfo.add(resultAdvert);
        }else {
            ResultAdvert advert = new ResultAdvert();
            advert.setId("");
            advert.setTitle("");
            advert.setViewSort(0);
            advert.setProjectInfo(new ArrayList<>());
            advert.setAdvertisingImg(new ArrayList<>());
            advertInfo.add(advert);
        }
        return advertInfo;
    }

    /**
     * 保存广告
     * @param param
     * @return
     */
    public ObjectRestResponse saveAdvertInfo(SaveAdvert param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizAdvertisingPosition bizAdvertisingPosition = new BizAdvertisingPosition();
        BizAdvertisingProject project = new BizAdvertisingProject();
        String advertId = UUIDUtils.generateUuid();
        bizAdvertisingPosition.setId(advertId);
        bizAdvertisingPosition.setTenantId(BaseContextHandler.getTenantID());
        bizAdvertisingPosition.setTitle(param.getTitle());
        bizAdvertisingPosition.setCreateBy(BaseContextHandler.getUserID());
        bizAdvertisingPosition.setTimeStamp(new Date());
        bizAdvertisingPosition.setViewSort(param.getViewSort());
        bizAdvertisingPosition.setCreateTime(new Date());
        bizAdvertisingPosition.setSkipBus(param.getSkipBus());
        bizAdvertisingPosition.setPosition(param.getPosition());
        if (param.getSkipBus().equals("2")) {
            bizAdvertisingPosition.setSkipUrl(param.getSkipUrl());
        } else if (param.getSkipBus().equals("1")) {
            bizAdvertisingPosition.setProductId(param.getProductId());
            bizAdvertisingPosition.setBusClassify(param.getBusClassify());
            bizAdvertisingPosition.setBusId(param.getBusId());
        }
        //获取图片
        if (param.getAdvertisingImg().size() > 0 && param.getAdvertisingImg() != null) {
            for (ImgInfo imgInfo : param.getAdvertisingImg()) {
                if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.SHOP);
                    if(restResponse.getStatus()==200){
                        bizAdvertisingPosition.setAdvertisingImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
                //bizAdvertisingPosition.setAdvertisingImg(imgInfo.getUrl());
            }
        } else {
            bizAdvertisingPosition.setAdvertisingImg("");
        }
        msg.setData(advertId);
        //关联项目
        if (bizAdvertisingPositionMapper.insertSelective(bizAdvertisingPosition) > 0) {
            for (Map<String, String> projectInfo : param.getProjectInfo()) {
                project.setId(UUIDUtils.generateUuid());
                project.setAdvertId(advertId);
                project.setProjectId(projectInfo.get("id"));
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateTime(new Date());
                project.setTimeStamp(new Date());
                if (bizAdvertisingProjectMapper.insertSelective(project) < 0) {
                    logger.error("保存关联数据失败,projectId为{}",projectInfo.get("id"));
                    msg.setStatus(102);
                    msg.setMessage("保存关联数据失败");
                    return msg;
                }
            }
        }else {
            logger.error("保存数据失败,projectId为{}",param.getProjectInfo());
            msg.setStatus(102);
            msg.setMessage("保存数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 编辑广告信息
     * @param param
     * @return
     */
    public ObjectRestResponse updateAdvertInfo(SaveAdvert param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizAdvertisingPosition bizAdvertisingPosition = new BizAdvertisingPosition();
        BizAdvertisingProject bizAdvertisingProject = new BizAdvertisingProject();
        ResultAdvert resultAdvert = bizAdvertisingPositionMapper.selectAdvertInfo(param.getId());
//        ResultAdvert advert = new ResultAdvert();
        if (resultAdvert != null) {
            try {
                BeanUtils.copyProperties(bizAdvertisingPosition,resultAdvert);
                bizAdvertisingPosition.setViewSort(param.getViewSort());
                bizAdvertisingPosition.setTitle(param.getTitle());
                bizAdvertisingPosition.setSkipBus(param.getSkipBus());
                bizAdvertisingPosition.setPosition(param.getPosition());
                if (param.getSkipBus().equals("2")) {
                    bizAdvertisingPosition.setSkipUrl(param.getSkipUrl());
                } else if (param.getSkipBus().equals("1")) {
                    bizAdvertisingPosition.setProductId(param.getProductId());
                    bizAdvertisingPosition.setBusClassify(param.getBusClassify());
                    bizAdvertisingPosition.setBusId(param.getBusId());
                }
                //获取图片
                if (param.getAdvertisingImg().size() > 0 && param.getAdvertisingImg() != null) {
                    for (ImgInfo imgInfo : param.getAdvertisingImg()) {
                        if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                            ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.SHOP);
                            if(restResponse.getStatus()==200){
                                bizAdvertisingPosition.setAdvertisingImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                            }
                        }
                        //bizAdvertisingPosition.setAdvertisingImg(imgInfo.getUrl());
                    }
                }else {
                    bizAdvertisingPosition.setAdvertisingImg("");
                }
                bizAdvertisingPosition.setModifyBy(BaseContextHandler.getUserID());
                bizAdvertisingPosition.setModifyTime(new Date());
                //关联项目
                if (bizAdvertisingPositionMapper.updateByPrimaryKeySelective(bizAdvertisingPosition) > 0) {
                    int i = bizAdvertisingPositionMapper.deleteProjectByAdvertId(param.getId());
                    if (i > 0) {
                        for (Map<String, String> project : param.getProjectInfo()) {
                            bizAdvertisingProject.setId(UUIDUtils.generateUuid());
                            bizAdvertisingProject.setAdvertId(param.getId());
                            bizAdvertisingProject.setProjectId(project.get("id"));
                            bizAdvertisingProject.setCreateBy(BaseContextHandler.getUserID());
                            bizAdvertisingProject.setCreateTime(new Date());
                            bizAdvertisingProject.setTimeStamp(new Date());
                            if (bizAdvertisingProjectMapper.insertSelective(bizAdvertisingProject) < 0) {
                                logger.error("保存关联数据失败,projectId为{}",project.get("id"));
                                msg.setStatus(102);
                                msg.setMessage("保存关联数据失败");
                                return msg;
                            }
                        }
                    }
                }else {
                    logger.error("保存失败,id为{}",resultAdvert.getId());
                    msg.setStatus(102);
                    msg.setMessage("保存数据失败");
                    return msg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 删除广告
     * @param
     * @return
     */
    public ObjectRestResponse deleteAdvertById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        bizAdvertisingPositionMapper.deleteAdvertById(id);
        if (bizAdvertisingPositionMapper.deleteAdvertById(id) < 0) {
            logger.error("删除失败，id为{}", id);
            msg.setStatus(102);
            msg.setMessage("删除失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    public List<ProjectListVo> getProjectInfo(String productId) {
        List<ProjectListVo> projectInfo = bizAdvertisingPositionMapper.getProjectInfo(productId);
        if (projectInfo.size() == 0 || projectInfo == null) {
            projectInfo = new ArrayList<>();
        }

        return projectInfo;
    }
}