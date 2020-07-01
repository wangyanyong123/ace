package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizProductSpec;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecCodeVo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecDataForCreateOrder;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.product.out.SpecVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 * 商品规格表
 * 
 * @author zxl
 * @Date 2018-12-11 10:25:54
 */
public interface BizProductSpecMapper extends CommonMapper<BizProductSpec> {

    /**
     * 查询规格
     * @param id
     * @return
     */
    SpecVo selectSpecInfo(String id);

    /**
     * 查询商品所属规格
     * @param productId
     * @return
     */
    List<ProductSpecInfo> selectSpecListByProductId(String productId);

    /**
     * 查询家政超市的规格类型
     * @param productId
     * @return
     */
    List<ProductSpecInfo> selectAllSpecTypeList(String productId);

    /**
     * 查询家政超市商品规格
     * @param productId
     * @param specCode
     * @return
     */
    List<ProductSpecInfo> selectSpecListByProductIdAndCode(@Param("productId") String productId, @Param("specCode") String specCode);

    /**
     * 查询规格详情
     * @param companyId
     * @param productId
     * @param specId
     * @return
     */
    ProductSpecInfo selectSpecInfoById(@Param("companyId")String companyId,@Param("productId")String productId, @Param("specId")String specId);


    /**
     * 查询预约服务的规格详情
     * @param companyId
     * @param productId
     * @param specId
     * @return
     */
    ProductSpecInfo getReservationSpecInfoById(@Param("companyId")String companyId,@Param("productId")String productId, @Param("specId")String specId);

    ProductSpecCodeVo getSpecInfoById(@Param("specId") String specId, @Param("productId") String productId);

    List<ProductSpecDataForCreateOrder> selectSpecDataForCreateOrder(@Param("specIdList") List<String> specIdList);
    List<ProductSpecDataForCreateOrder>  selectReservationSpecDataForCreateOrder(@Param("specIdList") List<String> specIdList);
}
