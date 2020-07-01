package com.github.wxiaoqi.security.app.mapper;


import com.github.wxiaoqi.security.app.entity.BizPnsCall;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 号码管理
 */
public interface BizPnsCallMapper extends CommonMapper<BizPnsCall> {
    /**
     * 修改绑定状态
     * @param bindingFlag 绑定状态
     * @param bindId 绑定ID
     * @return
     */
    int updateByBindId(@Param("bindingFlag") int bindingFlag, @Param("bindId") String bindId);

    /**
     * 获取商家手机号(旧订单)
     * @param id 订单ID
     * @return
     */
    String getCompanyTel(@Param("id") String id);

    /**
     * 获取商家手机号(新订单)
     * @param id 订单ID
     * @return
     */
    String getNewCompanyTel(@Param("id") String id);

}
