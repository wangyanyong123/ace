package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizProduct;
import com.github.wxiaoqi.security.app.vo.product.out.*;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品表
 * 
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
public interface BizProductMapper extends CommonMapper<BizProduct> {

    /**
     * 查询商品分类下的上架商品
     * @param classifyId
     * @return
     */
    List<ProductVo> selectProductListByClassifyId(@Param("projectId") String projectId, @Param("classifyId") String classifyId,
                                                  @Param("cityCodeList") List<String> cityCodeList, @Param("page") Integer page, @Param("limit") Integer limit);

    CouponInfoVo getProductCoupon(@Param("productId") String productId);

    List<CouponInfoVo> getProductCouponList(@Param("productId") String productId);

    List<CouponInfoVo> getCartCouponList(@Param("tenantId") String tenantId);

    List<PostageInfoVo> getPostageList(@Param("tenantId") String tenantId);
    /**
     * 查询商品详情
     * @param id
     * @return
     */
    ProductInfo selectProductInfoById(@Param("id") String id,@Param("userId") String userId);

    /**
     * 查询分享商品详情
     * @param id
     * @return
     */
    ProductInfo selectShareProductInfoById(@Param("id") String id);

    /**
     * 查询拼团抢购下的商品列表
     * @return
     */
    List<GroupProductVo> selectGroupProductList(@Param("day") String day, @Param("projectId") String projectId,
                                                @Param("page") Integer page, @Param("limit") Integer limit,
                                                @Param("groupStatus") Integer groupStatus,@Param("type") Integer type,
                                                @Param("cityCodeList") List<String> cityCodeList);

    /**
     * 查询拼购商品详情
     * @param id
     * @return
     */
    GroupProductInfo selectGroupProductInfo(@Param("id") String id);

    /**
     * 查询拼购商品详情
     * @param id
     * @return
     */
    GroupProductInfo selectBerserkProductInfo(@Param("id") String id);

    /**
     * 检查库存并更新库存
     * @author yuwz 2016年9月27日
     * @param productId
     * @param sales
     * @return
     */
    int updateStockById(@Param("productId")String productId,@Param("sales")int sales);

    /**
     * 判断家政超市库存是否够并减库存
     * @param productId
     * @param sales
     * @return
     */
    int updateResStockById(@Param("productId")String productId,@Param("sales")int sales);

    /**
     * 家政超市加库存
     * @param productId
     * @param sales
     * @return
     */
    int updateResStockNoLimitById(@Param("productId")String productId,@Param("sales")int sales);

    //取消后,销量减1
    int updateSalesNumById(@Param("ids") List<String> ids);

    //取消后,销量减1(家政超市)
    int updateResSalesNumById(@Param("ids") List<String> ids);

    List<String> selectProductIdByWoId(String woId);

    int selectIsSeckilByUserId(@Param("userId")String userId,@Param("busId")String busId,@Param("productId") String productId);

    int selectIsSeckilByUserIdNew(@Param("userId")String userId,@Param("productId") String productId);

    /**
     * 查询参团人头像
     * @param productId
     * @return
     */
    List<ImgInfo> selectGroupBuyPhoto(@Param("productId") String productId);

    /**
     * 查询商品挑选所属规格
     * @param productId
     * @return
     */
    ProductSpecVo selectProductSpecInfoById(@Param("productId") String productId);

    /**
     * 查询推荐商品
     * @param projectId
     * @return
     */
    List<RecommendVo> selectRecommendProductInfo(@Param("projectId") String projectId);

    /**
     * 查询首页商品分类下的推荐商品列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ProductVo> selectRecommendListByClassifyId(@Param("projectId") String projectId,
                                                      @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询首页推荐团购
     * @param projectId
     * @return
     */
    List<RecommendVo> selectRecommendGroupInfo(@Param("groupBusId") String groupBusId, @Param("projectId") String projectId);

    /**
     * 查询首页推荐团购列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<ProductVo> selectRecommendGroupList(@Param("groupBusId") String groupBusId, @Param("projectId") String projectId,
                                             @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 更新产品销量
     * @param productId
     * @param subNum
     * @return
     */
    int udpateProductSalesById(@Param("productId")String productId ,@Param("subNum")int subNum);

    /**
     * 更新家政超市产品销量
     * @param productId
     * @param subNum
     * @return
     */
    int udpateReservationSalesById(@Param("productId")String productId ,@Param("subNum")int subNum);

    /**
     * 查询商品的用户评价
     * @param productId
     * @param page
     * @param limit
     * @return
     */
    List<UserCommentVo> selectProductCommentDetail(@Param("productId")String productId, @Param("page") Integer page, @Param("limit") Integer limit );

    /**
     * 查询已结束但未成团的团购产品
     * @param busId
     * @return
     */
    List<BizProduct> selectNotCompleteGroupBuyProduct(@Param("busId")String busId);

    /**
     * 根据订单id 查询产品信息
     * @param subId
     * @return
     */
    List<Map<String,Object>> selectProductInfoBySubId(@Param("subId")String subId);


    /**
     * 查询商家信息
     * @param id
     * @return
     */
    CompanyVo selectCompanyInfoById(String id);

    int updateCouponStatusByProduct(@Param("couponIds") List<String> couponIds);

    List<String> selectCouponIds(@Param("productList") List<String> productList);

    int getUserBuyNumById(@Param("id") String id,@Param("userId") String userId);

	List<com.github.wxiaoqi.security.app.vo.product.in.ProductVo> selectProductVoByWoId(String woId);

	int updateResSalesNum(@Param("productId")String productId, @Param("subNum")String subNum);

    int updateSalesNum(@Param("productId")String productId, @Param("subNum")String subNum);

    int addStockById(@Param("productId")String productId,@Param("sales")int sales);

    String getBusNameByPid(@Param("productId") String productId);

    List<ImgInfo> selectGroupBuyUserPhoto(@Param("productId") String productId);
}
