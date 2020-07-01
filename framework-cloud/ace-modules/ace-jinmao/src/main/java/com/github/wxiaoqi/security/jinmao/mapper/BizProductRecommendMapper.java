package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductRecommend;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品推荐表
 * 
 * @author zxl
 * @Date 2018-12-10 10:10:55
 */
public interface BizProductRecommendMapper extends CommonMapper<BizProductRecommend> {

    /**
     * 查询项目下的推荐商品列表
     * @param projectId
     * @return
     */
    List<ResultRecommendListVo> selectProductList(@Param("projectId") String projectId,@Param("searchVal")String searchVal,@Param("page")Integer page,@Param("limit")Integer limit);

//    List<ResultRecommendListVo> selectReserveList(@Param("projectId") String projectId,@Param("searchVal")String searchVal,@Param("page")Integer page,@Param("limit")Integer limit);

    /**
     * 查询数量
     * @param projectId
     * @return
     */
    int selectProductCount(@Param("projectId") String projectId,@Param("searchVal")String searchVal);

    /**
     * 查询项目下的商品列表
     * @param projectId
     * @return
     */
    List<ResultRecommendInfo> selectAllProduct(@Param("projectId") String projectId,@Param("tenantId")String tenantId,@Param("searchVal")String searchVal);

    /**
     * 根据ID删除推荐商品
     * @param id
     * @return
     */
    int deleteRecProductById(@Param("id") String id);

    /**
     * 根据商品ID查询关联 项目和商户
     * @param productId
     * @return
     */
    ResultTenantInfo selectTenantByProductId(@Param("productId") String productId);

    ResultTenantInfo selectTenantByReserveId(@Param("productId") String productId);

    /**
     * 根据ID查询推荐详情
     * @param id
     * @return
     */
    ResultRecommendInfoVo selectRecommendById(@Param("id") String id);

    /**
     * 查询商品是否已被推荐
     * @param productId
     * @return
     */
    String selectProductIsRecommend(@Param("productId") String productId,@Param("projectId")String projectId);

//    List<ResultProductsInfo> selectProductInfo(String id);
}

