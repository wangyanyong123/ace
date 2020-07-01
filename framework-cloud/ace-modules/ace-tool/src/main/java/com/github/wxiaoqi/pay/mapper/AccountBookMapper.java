package com.github.wxiaoqi.pay.mapper;

import com.github.wxiaoqi.pay.entity.BizAccountBook;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单账本信息
 * 
 * @author huangxl
 * @Date 2018-1-25 13:59:20
 */
public interface AccountBookMapper extends CommonMapper<BizAccountBook> {



    /**
     * 根据实际支付id获取账单信息
     * @return
     */
    HashMap<String, String> selectAccountBookByActualId(@Param("actualId") String actualId);

    /**
     * 根据accountId更新payid
     * @return
     */
    int updatePayIdByActualId(@Param("actualId") String actualId,@Param("payId") String payId);

    /**
     * 根据accountId更新payid
     * @return
     */
    int updatePayIdByActualPid(@Param("actualId") String actualId,@Param("payId") String payId);

    int updatePayIdAndAppIdByActualId(@Param("actualId") String actualId, @Param("payId") String payId, @Param("appId") String appId);

    int updatePayIdAndAppIdByActualPid(@Param("actualId") String actualId, @Param("payId") String payId, @Param("appId") String appId);


    int updateRefundStatusByActualId(@Param("actualId") String actualId,@Param("refundStatus") String refundStatus,@Param("refundAmount")String refundAmount,@Param("refundReason")String refundReason,
                                     @Param("refundFailReason")String refundFailReason,@Param("outRequestNo")String outRequestNo);

	int getNumByOutRequestNo(@Param("outRequestNo")String outRequestNo);

	HashMap<String,String> selectALiByByActualId(@Param("actualId") String outTradeNo);

	/**
	 * 获取支付账单记录表状态信息
     * @Author guohao
     * @param subId : 业务ID （订单ID）
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 * @Date 2020/4/14 15:31
	 */
    Map<String,Object> getAccountBookStatusInfoBySubId(@Param("subId") String subId);

    BizAccountBook selectByActualId(@Param("actualId") String actualId);

    BizAccountBook selectBySubId(@Param("subId") String subId);

    BigDecimal getActualCostByActualId(@Param("actualId") String actualId);

    int getBusTypeByActualId(@Param("actualId") String actualId);
}
