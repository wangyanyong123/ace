package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCashProduct;
import com.github.wxiaoqi.security.jinmao.vo.integralproduct.out.CashProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品兑换记录表
 * 
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
public interface BizCashProductMapper extends CommonMapper<BizCashProduct> {


    /**
     * 查询商品兑换列表
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<CashProductVo> selectCashProductList(@Param("projectId") String projectId, @Param("startTime") String startTime,
                                              @Param("endTime") String endTime, @Param("searchVal") String searchVal,
                                              @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询商品兑换列表数量
     * @param projectId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @return
     */
    int selectCashProductCount(@Param("projectId") String projectId, @Param("startTime") String startTime,
                               @Param("endTime") String endTime, @Param("searchVal") String searchVal);

    /**
     * 查询商品兑换详情
     * @param id
     * @return
     */
    CashProductVo selectCashProductInfo(String id);

    List<CashProductVo> getResignCardList(@Param("startTime") String startTime,
                                          @Param("endTime") String endTime, @Param("searchVal") String searchVal,
                                          @Param("page") Integer page, @Param("limit") Integer limit);

    int getResignCardCount(@Param("startTime") String startTime,
                           @Param("endTime") String endTime, @Param("searchVal") String searchVal);
}
