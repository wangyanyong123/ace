package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProduct;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.ProductRecommend.OutputParam.ResultRecommendInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:43
 */
public interface BizProductMapper extends CommonMapper<BizProduct> {

    /**
     * 查询商品列表
     * @param busStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultProductVo> selectProductList(@Param("type") String type, @Param("tenantId") String tenantId,@Param("busStatus") String busStatus,
                                            @Param("searchVal") String searchVal,
                                            @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询可选的秒杀商品列表
     * @return
     */
    List<ResultProductVo> selectSpikeProductList(@Param("searchVal") String searchVal,@Param("type") String type,@Param("tenantId") String tenantId);

    /**
     * 查询已选秒杀商品
     * @return
     */
    List<ResultSpike> selectSpikeByid(String id);

    /**
     * 查询商品数量
     * @param busStatus
     * @param searchVal
     * @return
     */
    int selectProductCount(@Param("type") String type,@Param("tenantId") String tenantId,@Param("busStatus") String busStatus,
                           @Param("searchVal") String searchVal);

    /**
     * 判断登录的用户是否是系统平台
     * @param tenantId
     * @return
     */
    String selectSystemByTenantId(String tenantId);


    String selectProjectById(String tenantId);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    ResultProductInfoVo selectProductInfo(String id);


    /**
     * 根据商品id查询业务状态
     * @param id
     * @return
     */
    String selectBusStatusById(String id);

    /**
     * 申请上架,业务状态为待审核
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
     * 上架,业务状态为已发布
     * @param id
     * @param userId
     * @return
     */
    int updatePutawayStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 驳回,业务状态为已驳回
     * @param id
     * @param userId
     * @return
     */
    int updateRejectStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询业务列表
     * @return
     */
    List<ResultProductBusinessVo> selectProductBusinessList();


    /**
     * 查询商品分类列表
     * @param busId
     * @return
     */
    List<ResultClassifyVo> selectProductClassifyList(String busId);


    /**
     * 根据商户id查询商户下的项目列表
     * @param id
     * @return
     */
    List<ResultProjectVo> selectTenantProjectList(@Param("type")String type,@Param("id") String id);

    /**
     * 根据商户id查询商户下的业务列表
     * @param id
     * @return
     */
    List<ResultProductBusinessVo> selectTenantBusinessList(@Param("id") String id , @Param("type") String type);


    /**
     * 查询商品审核列表
     * @param busStatus
     * @param classifyId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultProductAuditVo> selectProductAuditList(@Param("type") String type, @Param("tenantId") String tenantId,
            @Param("busStatus") String busStatus,@Param("classifyId") String classifyId,
                                                      @Param("searchVal") String searchVal,
                                                      @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询商品审核列表数量
     * @param busStatus
     * @param classifyId
     * @param searchVal
     * @return
     */
    int selectProductAuditCount(@Param("type") String type, @Param("tenantId") String tenantId,
                                @Param("busStatus") String busStatus,@Param("classifyId") String classifyId,
                                @Param("searchVal") String searchVal);


    /**
     * 查询团购活动列表
     * @param productStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultGroupActiveVo> selectGroupActiveList(@Param("groupId") String groupId,@Param("type") String type, @Param("tenantId") String tenantId,
                                                    @Param("productStatus") String productStatus,
                                                    @Param("searchVal") String searchVal,
                                                    @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询团购活动数量
     * @param productStatus
     * @param searchVal
     * @return
     */
    int selectGroupActiveCount(@Param("groupId") String groupId,@Param("type") String type, @Param("tenantId") String tenantId,
            @Param("productStatus") String productStatus, @Param("searchVal") String searchVal);


    /**
     * 查询团购活动详情
     * @param id
     * @return
     */
    ResultGroupActiveInfoVo selectGroupAcidtiveInfo(String id);

    /**
     * 查询该商品所下订单列表
     * @param id
     * @return
     */
    List<ResultOrderList> selectOrderListById(@Param("groupId") String groupId,@Param("id") String id);

    int selectOrderCount(@Param("groupId") String groupId,@Param("id") String id);

    List<ResultRecommendInfo> getProductListByBusId(@Param("searchVal")String searchVal,@Param("page") Integer page,@Param("limit") Integer limit);

    List<Integer> getProductListCount(@Param("searchVal")String searchVal);

    List<ResultRecommendInfo> getProductForAD(@Param("projectIds") List<String> projectIds,@Param("searchVal")String searchVal,
                                              @Param("page") Integer page,@Param("limit") Integer limit,@Param("type") String type);
}
