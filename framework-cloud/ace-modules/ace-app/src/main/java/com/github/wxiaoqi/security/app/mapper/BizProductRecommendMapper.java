package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductRecommend;
import com.github.wxiaoqi.security.app.vo.recommend.out.RecommendProductVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
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
     * 商品推荐列表
     * @return
     */
    List<RecommendProductVo> selectRecommendProduct(@Param("projectId")String projectId,@Param("cityCodeList")List<String> cityCodeList,
                                                    @Param("page")Integer page, @Param("limit") Integer limit);

    List<String> getRecProductLabel(@Param("productId") String productId);


    List<RecommendProductVo> selectRecommendProductList(@Param("projectId")String projectId,@Param("busId")String busId,
                                                        @Param("productType") Integer productType,@Param("cityCodeList")List<String> cityCodeList,
                                                        @Param("page")Integer page, @Param("limit") Integer limit);

    List<RecommendProductVo> selectRecommendReservationList(@Param("projectId")String projectId, @Param("busId")String busId,
                                                            @Param("productType") Integer productType,@Param("cityCodeList")List<String> cityCodeList,
                                                            @Param("page")Integer page, @Param("limit") Integer limit);


}
