package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.out.*;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 商品购物车
 * 
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
public interface BizShoppingCartMapper extends CommonMapper<BizShoppingCart> {

    /**
     * 根据商品id查询所属公司信息
     * @param productId
     * @return
     */
    CompanyInfo selectCompanyInfoById(String productId);

    /**
     * 根据规格id查询详情
     * @param specId
     * @return
     */
    SpecInfo selectSpecInfoById(String specId);

    /**
     * 编辑购物车购买数量
     * @param id
     * @param productNum
     * @param userId
     * @return
     */
    int updateShoppinfBuyNumById(@Param("id") String id, @Param("productNum") String productNum, @Param("userId") String userId);

    /**
     * 删除购物车记录
     * @param idList
     * @param userId
     * @return
     */
    int delShoppinfById(@Param("idList") List<String> idList, @Param("userId") String userId);

    /**
     * 查询用户下购物车商品所属公司列表
     * @return
     */
    List<ShoppingVo> selectShoppingCartCompany(@Param("userId") String userId, @Param("projectId") String projectId,
                                               @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询公司所属商品信息
     * @param companyId
     * @return
     */
    List<ProductInfo> selectProductInfoByCompanyId(@Param("projectId") String projectId,@Param("userId") String userId, @Param("companyId") String companyId);

    /**
     * 统计购物车金额
     * @param specIdList
     * @return
     */
    ShoppingStatisInfo selectShoppingCartMoneyAndCount(@Param("projectId") String projectId, @Param("userId") String userId, @Param("specIdList") List<String> specIdList,
                                                       @Param("selectIsAll") String selectIsAll);

    /**
     * 查询购物车数量
     * @param userId
     * @return
     */
    int selectCartCountByUserId(@Param("projectId") String projectId, @Param("userId") String userId);

    /**
     * 查询商品是否已加入购物车
     * @param productId
     * @return
     */
    ProductInfo selectIsCartByProductId(@Param("projectId") String projectId, @Param("userId") String userId,
                                        @Param("productId") String productId, @Param("specId") String specId);

    String selectNameByCompanyId(String companyId);

    SpecDataForAddCart selectSpecDataById(@Param("specId") String specId);

    List<CartTenantInfoVo> selectTenantProductInfo(@Param("specIdList") List<String> specIdList);

    int updateUserIdByOpenId(@Param("openId") String openId, @Param("userId") String userId, @Param("modifyTime") Date modifyTime);

    List<BizShoppingCart> selectCartDataByUserId(@Param("userId") String userId);

    List<BizShoppingCart> selectByIdList(@Param("idList") List<String> idList);

    List<ShoppingVo> selectWxShoppingCartCompany(@Param("userId") String userId, @Param("projectId") String projectId);


    List<ProductInfo> selectWxProductInfoByCompanyId(@Param("projectId") String projectId,
                                                     @Param("userId") String userId,
                                                     @Param("companyId") String companyId);

    ShoppingStatisInfo selectWxShoppingCartMoneyAndCount(@Param("projectId") String projectId, @Param("userId") String userId, @Param("specIdList") List<String> specIdList,
                                                         @Param("selectIsAll") String selectIsAll);

    int selectWxCartCountByUserId(@Param("projectId") String projectId, @Param("userId") String userId);


    ProductInfo selectWxIsCartByProductId(@Param("projectId") String projectId, @Param("userId") String userId,
                                          @Param("productId") String productId, @Param("specId") String specId);

}
