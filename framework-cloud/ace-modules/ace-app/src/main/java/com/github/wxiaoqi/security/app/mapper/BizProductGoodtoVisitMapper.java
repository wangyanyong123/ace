package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductGoodtoVisit;
import com.github.wxiaoqi.security.app.vo.goodvisit.out.*;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好物探访表
 * 
 * @author zxl
 * @Date 2018-12-13 09:56:57
 */
public interface BizProductGoodtoVisitMapper extends CommonMapper<BizProductGoodtoVisit> {

    /**
     * 好物探访列表
     * @return
     */
    List<GoodVisitVo> getGoodVisitList(@Param("projectId") String projectId,@Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询好物探访详情
     * @param id
     * @return
     */
    GoodVisitDetailsVo getGoodVisitDetails(@Param("id")String id);

    /**
     * 查询商品介绍
     * @param productId
     * @return
     */
    ProductInfoVo getProductInfo(@Param("productId")String productId);

    /**
     * 查询商品阅读量和用户头像
     * @return
     */
    List<GoodVisitUserPhoto> selectGoodVisitUserPhoto(@Param("id") String id);


//    ContentAndUser selectContentByProductId(@Param("productId") String productId);
}
