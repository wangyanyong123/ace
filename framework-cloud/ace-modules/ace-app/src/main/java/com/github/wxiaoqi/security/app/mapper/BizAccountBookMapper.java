package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 订单账本表
 * 
 * @author zxl
 * @Date 2018-12-14 17:44:12
 */
public interface BizAccountBookMapper extends CommonMapper<BizAccountBook> {
    /**
     * 支付成功后更新账本状态
     * @param bizAccountBook
     * @return
     */
    int updatePayStatusById(BizAccountBook bizAccountBook);

    /**
     * 根据实际支付id获取业务id
    * @param actualId
     * @return
     */
    String selectBusIdByActualId(String actualId);

    BizAccountBook getPayAndRefundStatusBySubId(@Param("subId") String subId);


    int getBusTypeByActualId(@Param("actualId") String actualId);

    BizAccountBook getPayAndRefundStatusByActualId(@Param("actualId") String actualId);
}
