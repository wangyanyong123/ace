package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizIntegralProduct;
import com.github.wxiaoqi.security.app.vo.integralproduct.CashVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductInfo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.ScreenVo;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
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
     * @param userId
     * @param userId
     * @param isRecommend
     * @param endIntegral
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<IntegralProductVo> selectProductList(@Param("userId") String userId, @Param("isRecommend") String isRecommend,
                                              @Param("startIntegral") String startIntegral, @Param("endIntegral") String endIntegral
            , @Param("projectId") String projectId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询兑换记录
     * @param userId
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<CashVo> selectCashList(@Param("userId") String userId,@Param("projectId") String projectId,
                                @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询积分筛选范围
     * @return
     */
    List<ScreenVo> selectIntegralScreenList();

    /**
     * 查询当前用户积分
     * @param userId
     * @return
     */
    String selectCurrentIntegralByUserId(String userId);

    /**
     * 查询用户消费积分
     * @param userId
     * @return
     */
    UserSignIn selectConsumePointByUser(String userId);


    /**
     * 查询积分商品详情
     * @param userId
     * @param id
     * @return
     */
    IntegralProductInfo selectProductInfo(@Param("userId") String userId, @Param("id") String id);

    /**
     * 断库存是否够并减库存
     * @param productId
     * @return
     */
    int updateStockById(@Param("productId") String productId);
	
}
