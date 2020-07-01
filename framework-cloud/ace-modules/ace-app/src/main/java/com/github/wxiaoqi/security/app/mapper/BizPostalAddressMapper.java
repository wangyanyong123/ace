package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressDeliveryOut;
import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressOut;
import com.github.wxiaoqi.security.app.entity.BizPostalAddress;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收货地址
 * 
 * @author huangxl
 * @Date 2018-12-18 18:34:14
 */
public interface BizPostalAddressMapper extends CommonMapper<BizPostalAddress> {

    /**
     * 获取我的收货地址列表
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    List<PostalAddressOut> getPostalAddressList(@Param("userId") String userId, @Param("page") int page, @Param("limit") int limit);

    /**
     * 去除默认地址
     * @param userId
     * @return
     */
    int delDefaultPostalAddress(@Param("userId") String userId);


    PostalAddressDeliveryOut getNoDeliveryAddress(@Param("tenantId")String tenantId,@Param("procCode") String procCode);

    /**
     * 获取我的默认收货地址
     * @param userId
     * @return
     */
    PostalAddressOut getUsePostalAddress(@Param("userId") String userId);

    BizPostalAddress selectById(@Param("addressId") String addressId);
}
