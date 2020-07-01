package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.SubToRobotVo;
import com.github.wxiaoqi.security.api.vo.order.out.SubVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoToRobotVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoVo;
import com.github.wxiaoqi.security.app.entity.BizSubscribeWo;
import com.github.wxiaoqi.security.app.vo.crm.in.SyncWorkOrderStateIn;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单工单表
 * 
 * @author huangxl
 * @Date 2018-12-19 17:49:19
 */
public interface BizSubscribeWoMapper extends CommonMapper<BizSubscribeWo> {

    /**
     * 获取订单详情To服务端app
     * @param id
     * @return
     */
    SubVo getSubWoDetail(String id);

    /**
     * 获取订单详情To调度引擎
     * @param id
     * @return
     */
    SubToRobotVo getSubWoToRobotDetail(String id);

    /**
     * 获取工单详情To服务端app
     * @param id
     * @return
     */
    WoVo getWoDetail(String id);

    /**
     * 获取工单详情To调度引擎
     * @param id
     * @return
     */
    WoToRobotVo getWoToRobotDetail(String id);

    /**
     * 根据实际支付id获取支付回调操作
     * @param actualId
     * @return
     */
    List<Map<String,Object>> getPayNotifyOperateId(@Param("actualId")String actualId,@Param("payId")String payId);

    /**
     * 获取待成团已支付的订单
     * @param productId
     * @return
     */
    List<Map<String,Object>> getWaitGroupBuySubList(@Param("productId") String productId);

    /**
     * 获取用户的完成工单数和接单数
     * @param userId
     * @return
     */
    Map<String,Integer> getWoSumByUserId(@Param("userId") String userId,@Param("projectId")String projectId);

    /**
     * 获取用户的完成工单数和接单数
     * @param userId
     * @return
     */
    Double getAppraisalValByUserId(@Param("userId") String userId);

    /**
     * 获取未成团但已支付的订单
     * @param productId
     * @return
     */
    List<Map<String,Object>> getNotCompleteGroupBuySubList(@Param("productId") String productId);

    /**
     * 获取CRM 报修/投诉工单同步信息接口
     */
    SyncWorkOrderStateIn getSyncWorkOrderState(@Param("woId") final String woId);


    int updateSubReservationExpectTime(@Param("subId") String subId, @Param("dateTime") Date dateTime);
}
