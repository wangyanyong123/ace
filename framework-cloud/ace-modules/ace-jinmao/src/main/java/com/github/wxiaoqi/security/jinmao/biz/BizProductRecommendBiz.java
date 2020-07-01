package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizProductRecommend;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductRecommendMapper;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.InputParam.SaveRecommendParam;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfo;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendListVo;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultTenantInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品推荐表
 *
 * @author zxl
 * @Date 2018-12-10 10:10:55
 */
@Service
public class BizProductRecommendBiz extends BusinessBiz<BizProductRecommendMapper,BizProductRecommend> {
    private Logger logger = LoggerFactory.getLogger(BizProductRecommendBiz.class);
    @Autowired
    private BizProductRecommendMapper bizProductRecommendMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    /**
     * 查询项目下的推荐商品
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    public List<ResultRecommendListVo> getProductRecommendList(String projectId,String searchVal,Integer page,Integer limit) {
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
        List<ResultRecommendListVo> recommendListVos = bizProductRecommendMapper.selectProductList(projectId,searchVal, startIndex, limit);
        return recommendListVos;
    }

    /**
     * 查询数量
     * @param projectId
     * @return
     */
    public int getProductRecommendCount(String projectId,String searchVal) {
        return bizProductRecommendMapper.selectProductCount(projectId,searchVal);
    }

    /**
     * 查询项目ID下所有商品
     * @param projectId
     * @return
     */
    public List<ResultRecommendInfo> getProductList(String projectId,String searchVal) {
        List<ResultRecommendInfo> productList = new ArrayList<>();
        String tenantId = BaseContextHandler.getTenantID();
        String type = bizProductMapper.selectSystemByTenantId(tenantId);
        if (!type.equals("2")) {
            tenantId = "";
        }
        List<ResultRecommendInfo> productsInfos = bizProductRecommendMapper.selectAllProduct(projectId,tenantId, searchVal);
        for (ResultRecommendInfo productsInfo : productsInfos) {
            String product = bizProductRecommendMapper.selectProductIsRecommend(productsInfo.getProductId(),projectId);
            if (product == null) {
                ResultRecommendInfo products = new ResultRecommendInfo();
                products.setProductId(productsInfo.getProductId());
                products.setProductCode(productsInfo.getProductCode());
                products.setProductName(productsInfo.getProductName());
                products.setTenantName(productsInfo.getTenantName());
                products.setBusName(productsInfo.getBusName());
                productList.add(products);
            }
        }
        return productList;
    }

    /**
     * 删除推荐商品
     * @param id
     * @return
     */
    public int deleteProductById(String id) {
        return bizProductRecommendMapper.deleteRecProductById(id);
    }

    /**
     * 保存商品推荐
     * @param param
     * @return
     */
    public ObjectRestResponse saveProductRecommend(SaveRecommendParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizProductRecommend bizProductRecommend = new BizProductRecommend();
        ResultTenantInfo tenantInfo = bizProductRecommendMapper.selectTenantByProductId(param.getProductId());
        if (tenantInfo == null) {
            tenantInfo = bizProductRecommendMapper.selectTenantByReserveId(param.getProductId());
        }
        String id = UUIDUtils.generateUuid();
        bizProductRecommend.setId(id);
        bizProductRecommend.setProductId(param.getProductId());
        bizProductRecommend.setViewSort(param.getViewSort());
        bizProductRecommend.setProjectId(param.getProjectId());
        bizProductRecommend.setTenantId(tenantInfo.getTenantId());
        bizProductRecommend.setTimeStamp(new Date());
        bizProductRecommend.setCreateBy(BaseContextHandler.getUserID());
        bizProductRecommend.setCreateTime(new Date());
        bizProductRecommend.setImgUrl(param.getImgUrl());
        msg.setData(id);
        if (bizProductRecommendMapper.insertSelective(bizProductRecommend) < 0) {
            msg.setStatus(105);
            msg.setMessage("保存商品失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 编辑商品推荐
     * @param param
     * @return
     */
    public ObjectRestResponse updateProductRecommend(SaveRecommendParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizProductRecommend bizProductRecommend = new BizProductRecommend();
        bizProductRecommend.setId(param.getId());
        bizProductRecommend.setProductId(param.getProductId());
        bizProductRecommend.setViewSort(param.getViewSort());
        if (param.getProductId()!=null) {
            ResultTenantInfo tenantInfo = bizProductRecommendMapper.selectTenantByProductId(param.getProductId());
            if (tenantInfo == null) {
                tenantInfo = bizProductRecommendMapper.selectTenantByReserveId(param.getProductId());
            }
            bizProductRecommend.setTenantId(tenantInfo.getTenantId());
        }
        bizProductRecommend.setImgUrl(param.getImgUrl());
        bizProductRecommend.setProjectId(param.getProjectId());
        bizProductRecommend.setModifyBy(BaseContextHandler.getUserID());
        bizProductRecommend.setModifyTime(new Date());
        if (bizProductRecommendMapper.updateByPrimaryKeySelective(bizProductRecommend) < 0) {
            msg.setStatus(105);
            msg.setMessage("修改商品失败!");
            return msg;
        }


        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 删除商品推荐
     * @param id
     * @return
     */
    public ObjectRestResponse deleteProductRecommendById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        int count = bizProductRecommendMapper.deleteRecProductById(id);
        if (count < 0) {
            msg.setMessage("删除商品失败");
            msg.setStatus(105);
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 根据ID获取商品推荐详情
     * @param id
     * @return
     */
    public List<ResultRecommendInfoVo> getProductRecommendById(String id) {
        List<ResultRecommendInfoVo> recommendInfos = new ArrayList<>();
        ResultRecommendInfoVo recommendInfo = bizProductRecommendMapper.selectRecommendById(id);
        if (recommendInfo != null && recommendInfo.getProductName() == null) {
            recommendInfo.setProductName(recommendInfo.getBusName());
        }
        recommendInfos.add(recommendInfo);
        return recommendInfos;
    }

}