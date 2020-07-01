package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralProduct;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.IntegralProductVo;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 积分商品表
 * 
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
public interface BizIntegralProductMapper extends CommonMapper<BizIntegralProduct> {

    /**
     * 查询积分商品列表
     * @param projectId
     * @param isRecommend
     * @param busStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<IntegralProductVo> selectIntegralProductList(@Param("projectId") String projectId, @Param("isRecommend") String isRecommend,
                                                      @Param("busStatus") String busStatus, @Param("searchVal") String searchVal,
                                                      @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询积分商品列表数量
     * @param projectId
     * @param isRecommend
     * @param busStatus
     * @param searchVal
     * @return
     */
    int selectIntegralProductCount(@Param("projectId") String projectId, @Param("isRecommend") String isRecommend,
                                   @Param("busStatus") String busStatus, @Param("searchVal") String searchVal);


    /**
     * 查询积分商品详情
     * @param id
     * @return
     */
    ProductInfo selectIntegralProductInfo(String id);


    /**
     * 申请上架,业务状态为已发布
     * @param id
     * @param userId
     * @return
     */
    int updateAuditStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     *下架,业务状态为已下架
     * @param id
     * @param userId
     * @return
     */
    int updateSoldStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 设置推荐与取消推荐
     * @param isRecommend
     * @param id
     * @param userId
     * @return
     */
    int updateRecommendStatus(@Param("isRecommend") String isRecommend, @Param("id") String id, @Param("userId") String userId);


    /**
     * 查询推荐数
     * @return
     */
    int selectRecommendCount();


    int getResignCard(@Param("projectVo")List<ResultProjectVo> projectVo);

    int getResignCardTop(@Param("projectVo")List<String> projectVo);

}
