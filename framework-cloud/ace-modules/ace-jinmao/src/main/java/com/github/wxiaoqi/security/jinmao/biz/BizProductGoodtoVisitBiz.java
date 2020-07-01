package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizProductGoodtoVisit;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BaseProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductGoodtoVisitMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.DeleteGoodVisit;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.GVUpdateStatus;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam.SaveGoodVisitParam;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.TenantVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 好物探访表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:53
 */
@Service
public class BizProductGoodtoVisitBiz extends BusinessBiz<BizProductGoodtoVisitMapper,BizProductGoodtoVisit> {

    private Logger logger = LoggerFactory.getLogger(BizProductGoodtoVisitBiz.class);
    @Autowired
    private BizProductGoodtoVisitMapper bizProductGoodtoVisitMapper;
    @Autowired
    private BaseProjectMapper baseProjectMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private ToolFegin toolFeign;

    /**
     * 查询好物探访列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultGoodVisitVo> getGoodVisitList(String enableStatus, String searchVal,String projectId, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        Integer startIndex = (page - 1) * limit;
        List<ResultGoodVisitVo> goodVisitList = bizProductGoodtoVisitMapper.selectGoodVisitList(enableStatus, searchVal,projectId, startIndex, limit);
        for (ResultGoodVisitVo goodVisitVo : goodVisitList) {
            List<ProjectListVo> projectInfo= bizProductGoodtoVisitMapper.selectProjectNames(goodVisitVo.getProductId());
            if (projectInfo != null && projectInfo.size() > 0) {
                String projectName = "";
                StringBuilder projectsb = new StringBuilder();
                for (ProjectListVo projectListVo : projectInfo) {
                    projectsb.append(projectListVo.getProjectName() + ",");
                }
                projectName = projectsb.substring(0, projectsb.length() - 1);
                goodVisitVo.setProjectName(projectName);
            }else {
                goodVisitVo.setProjectName("");
            }
        }

        return goodVisitList;
    }

    /**
     * 根据搜索查询数量
     * @param eableStatus
     * @param searchVal
     * @return
     */
    public int selectGoodVisitCount(String eableStatus, String searchVal,String projectId) {
        return bizProductGoodtoVisitMapper.selectGoodVisitCount(eableStatus, searchVal,projectId);
    }

    /**
     * 保存好物探访信息
     * @param param
     * @return
     */
    public ObjectRestResponse saveGoodVisitInfo(SaveGoodVisitParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizProductGoodtoVisit bizProductGoodtoVisit = new BizProductGoodtoVisit();
        String goodVisitID = null;
        goodVisitID = UUIDUtils.generateUuid();
        bizProductGoodtoVisit.setId(goodVisitID);
        //插入关联
        for (ProductListVo productInfo : param.getProductInfo()) {
            bizProductGoodtoVisit.setProductId(productInfo.getProductId());
            TenantVo tenantVo = bizProductGoodtoVisitMapper.selectTenantByProductId(productInfo.getProductId());
            bizProductGoodtoVisit.setTenantId(tenantVo.getTenantId());
        }
        bizProductGoodtoVisit.setEnableStatus("1");
        bizProductGoodtoVisit.setTitle(param.getTitle());
        bizProductGoodtoVisit.setSubHeading(param.getSubHeading());
        bizProductGoodtoVisit.setPublishTime(new Date());
        if (param.getRecommendImages().size() > 0 && param.getRecommendImages() != null) {
            for (ImgInfo recommendImg : param.getRecommendImages()) {
                if(StringUtils.isNotEmpty(recommendImg.getUrl())){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(recommendImg.getUrl(), DocPathConstant.SHOP);
                    if(restResponse.getStatus()==200){
                        bizProductGoodtoVisit.setRecommendImages(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
                //bizProductGoodtoVisit.setRecommendImages(recommendImg.getUrl());
            }
        }
        bizProductGoodtoVisit.setSigner(param.getSigner());
        if (param.getSignerLogo().size() > 0 && param.getSignerLogo() != null) {
            for (ImgInfo signerLogo : param.getSignerLogo()) {
                bizProductGoodtoVisit.setSignerLogo(signerLogo.getUrl());
            }
        }
        bizProductGoodtoVisit.setContent(param.getContent());
        bizProductGoodtoVisit.setCreateBy(BaseContextHandler.getUserID());
        bizProductGoodtoVisit.setCreateTime(new Date());
        msg.setData(goodVisitID);
        if (bizProductGoodtoVisitMapper.insertSelective(bizProductGoodtoVisit) < 0) {
            logger.error("保存数据失败,product为{}",param.getProductInfo());
            msg.setStatus(102);
            msg.setMessage("保存数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 查询关联商品
     * @return
     */
    public List<ProductListVo> getProductList(String searchVal) {
        List<ProductListVo> productListVoList = bizProductGoodtoVisitMapper.selectProductList(searchVal);
        List<ProductListVo> productList = new ArrayList<>();
        String projectName = "";
        for (ProductListVo productListVo : productListVoList) {
            String good = bizProductGoodtoVisitMapper.selectGoodVisitExist(productListVo.getProductId());
            List<ProjectListVo> projectInfo= bizProductGoodtoVisitMapper.selectProjectNames(productListVo.getProductId());
            if (good == null ) {
                ProductListVo product = new ProductListVo();
                StringBuilder sb = new StringBuilder();
                for (ProjectListVo project : projectInfo) {
                    sb.append(project.getProjectName() + ",");
                }
                projectName = sb.substring(0, sb.length() - 1);
                product.setProjectName(projectName);
                product.setProductName(productListVo.getProductName());
                product.setProductId(productListVo.getProductId());
                product.setTenantName(productListVo.getTenantName());
                productList.add(product);
            }
        }
        return productList;
    }

    /**
     * 查询好物探访详情
     * @param id
     * @return
     */
    public  List<ResultGoodVisitInfoVo> getGoodVisitInfo(String id) {
        List<ResultGoodVisitInfoVo> goodVisitlist = new ArrayList<>();
        ResultGoodVisitInfoVo goodVisitInfo = bizProductGoodtoVisitMapper.getGoodVisitInfo(id);
//        ResultGoodVisitInfo info = new ResultGoodVisitInfo();
        if (goodVisitInfo != null) {
            List<ProductListVo> productList = bizProductGoodtoVisitMapper.selectGVProductList(id);
            goodVisitInfo.setProductInfo(productList);
            //推荐图片
            if (goodVisitInfo.getRecommendImage() != null) {
                List<ImgInfo> recImages = new ArrayList<>();
                ImgInfo recImgInfo = new ImgInfo();
                recImgInfo.setUrl(goodVisitInfo.getRecommendImage());
                recImages.add(recImgInfo);
                goodVisitInfo.setRecommendImages(recImages);
            }else {
                goodVisitInfo.setRecommendImage("");
                goodVisitInfo.setRecommendImages(new ArrayList<>());
            }
            if (goodVisitInfo.getSignerLog() != null) {
                List<ImgInfo> signImages = new ArrayList<>();
                ImgInfo signImgInfo = new ImgInfo();
                signImgInfo.setUrl(goodVisitInfo.getSignerLog());
                signImages.add(signImgInfo);
                goodVisitInfo.setSignerLogo(signImages);
            }else {
                goodVisitInfo.setSignerLog("");
                goodVisitInfo.setSignerLogo(new ArrayList<>());
            }
            goodVisitlist.add(goodVisitInfo);
        }else {
            ResultGoodVisitInfoVo goodVisitInfoVo = new ResultGoodVisitInfoVo();
            goodVisitInfoVo.setId("");
            goodVisitInfoVo.setTitle("");
            goodVisitInfoVo.setSubHeading("");
            goodVisitInfoVo.setContent("");
            goodVisitInfoVo.setRecommendImage("");
            goodVisitInfoVo.setSignerLogo(new ArrayList<>());
            goodVisitInfoVo.setSigner("");
            goodVisitInfoVo.setSignerLog("");
            goodVisitInfoVo.setSignerLogo(new ArrayList<>());
            goodVisitInfoVo.setProductInfo(new ArrayList<>());
            goodVisitlist.add(goodVisitInfoVo);
        }
        return goodVisitlist;
    }

    /**
     * 编辑好物探访详情
     * @param param
     * @return
     */
    public ObjectRestResponse updateGoodVisitInfo(SaveGoodVisitParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultGoodVisitInfoVo goodVisitInfo = bizProductGoodtoVisitMapper.getGoodVisitInfo(param.getId());
        List<ProductListVo> productListVos = bizProductGoodtoVisitMapper.selectGVProductList(param.getId());
        goodVisitInfo.setProductInfo(productListVos);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BizProductGoodtoVisit bizProductGoodtoVisit = new BizProductGoodtoVisit();
        if (goodVisitInfo != null) {
            try {
                BeanUtils.copyProperties(bizProductGoodtoVisit, goodVisitInfo);
                bizProductGoodtoVisit.setTitle(param.getTitle());
                bizProductGoodtoVisit.setSubHeading(param.getSubHeading());
                bizProductGoodtoVisit.setContent(param.getContent());
                if (param.getRecommendImages().size() > 0 && param.getRecommendImages() != null) {
                    for (ImgInfo recommendImg : param.getRecommendImages()) {
                        if(StringUtils.isNotEmpty(recommendImg.getUrl())){
                            ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(recommendImg.getUrl(), DocPathConstant.SHOP);
                            if(restResponse.getStatus()==200){
                                bizProductGoodtoVisit.setRecommendImages(restResponse.getData()==null ? "" : (String)restResponse.getData());
                            }
                        }
                        //bizProductGoodtoVisit.setRecommendImages(recommendImg.getUrl());
                    }
                }else {
                    bizProductGoodtoVisit.setRecommendImages("");
                }
                bizProductGoodtoVisit.setSigner(param.getSigner());
                if (param.getSignerLogo().size() > 0 && param.getSignerLogo() != null) {
                    for (ImgInfo signerLogo : param.getSignerLogo()) {
                        bizProductGoodtoVisit.setSignerLogo(signerLogo.getUrl());
                    }
                }else {
                    bizProductGoodtoVisit.setSignerLogo("");
                }
                for (ProductListVo productInfo : param.getProductInfo()) {
                    bizProductGoodtoVisit.setProductId(productInfo.getProductId());
                }
                bizProductGoodtoVisit.setModifyBy(BaseContextHandler.getUserID());
                bizProductGoodtoVisit.setModifyTime(new Date());
                if (bizProductGoodtoVisitMapper.updateByPrimaryKeySelective(bizProductGoodtoVisit) < 0) {
                    logger.error("修改数据失败,product为{}",param.getProductInfo());
                    msg.setStatus(102);
                    msg.setMessage("修改数据失败");
                    return msg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {

        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 修改好物探访状态
     * @param param
     * @return
     */
    public ObjectRestResponse updateGoodVisitStatus(GVUpdateStatus param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizProductGoodtoVisitMapper.updateGoodVisitStatus(param.getId(), param.getEnableStatus()) > 0) {
            if (param.getEnableStatus().equals("2")) {
                BizProductGoodtoVisit newGoodtoVisit = new BizProductGoodtoVisit();
                BizProductGoodtoVisit oldGoodtoVisit = bizProductGoodtoVisitMapper.selectByPrimaryKey(param.getId());
                org.springframework.beans.BeanUtils.copyProperties(oldGoodtoVisit,newGoodtoVisit);
                newGoodtoVisit.setPublishTime(new Date());
                bizProductGoodtoVisitMapper.updateByPrimaryKeySelective(newGoodtoVisit);
            }
        }else {
            logger.error("修改状态失败，id为{}", param.getId());
            msg.setStatus(102);
            msg.setMessage("修改状态失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 删除好物探访商品
     * @param param
     * @return
     */
    public ObjectRestResponse deleteGoodVisit(DeleteGoodVisit param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        bizProductGoodtoVisitMapper.deleteGoodVisit(param.getId(),param.getStatus());
        if (bizProductGoodtoVisitMapper.deleteGoodVisit(param.getId(),param.getStatus()) < 0) {
            logger.error("删除失败，id为{}",param.getId());
            msg.setStatus(102);
            msg.setMessage("删除失败！");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }
}