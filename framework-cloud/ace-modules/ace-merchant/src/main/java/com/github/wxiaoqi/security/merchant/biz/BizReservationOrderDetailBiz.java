package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.RedisKeyUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.merchant.constants.CommonConstants;
import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.merchant.mapper.BizReservationOrderDetailMapper;
import com.github.wxiaoqi.security.merchant.response.MerchantObjectResponse;
import com.github.wxiaoqi.security.merchant.util.RedisUtil;
import com.github.wxiaoqi.security.merchant.vo.order.OrderStatusDO;
import com.github.wxiaoqi.security.merchant.vo.order.OrderTotalVO;
import com.sun.corba.se.impl.orbutil.ObjectUtility;
import io.netty.util.internal.ObjectUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 预约服务订单详情表
 *
 * @author wangyanyong
 * @Date 2020-04-24 22:16:54
 */
@Service
public class BizReservationOrderDetailBiz extends BusinessBiz<BizReservationOrderDetailMapper,BizReservationOrderDetail> {

    @Resource
    private RedisUtil redisUtil;
    /**
     * 工作台 统计信息
     * @return
     */
    public ObjectRestResponse orderTotal(){
        String tenantId = BaseContextHandler.getTenantID();
        if(StringUtils.isEmpty(tenantId)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
        }
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 订单状态统计
        List<OrderStatusDO> list = this.mapper.queryOderStatusTotal(tenantId);
        // 退款统计
        OrderStatusDO refundStatusDO = this.mapper.queryRefundStatusTotal(tenantId,AceDictionary.ORDER_REFUND_STATUS_APPLY);
        if(ObjectUtils.isEmpty(refundStatusDO)){
            refundStatusDO = new OrderStatusDO();
            refundStatusDO.setTotal(0);

        }
        refundStatusDO.setOrderStatus(AceDictionary.ORDER_STATUS_APPLY_REFUND);
        list.add(refundStatusDO);
        OrderStatusDO commentStatusDO = this.mapper.queryCommentStatusTotal(tenantId,AceDictionary.ORDER_STATUS_COM,AceDictionary.PRODUCT_COMMENT_NONE,AceDictionary.ORDER_REFUND_STATUS_NONE);
        if(ObjectUtils.isEmpty(commentStatusDO)){
            commentStatusDO = new OrderStatusDO();
            commentStatusDO.setTotal(0);
        }
        commentStatusDO.setOrderStatus(AceDictionary.ORDER_STATUS_W_COMMENT);
        list.add(commentStatusDO);
        // 昨日交易金额
        BigDecimal yesterdayTotal = getYesterdayTotal(tenantId);
        // 今日订单数
        int todayOrderTotal = this.mapper.todayOrderTotal(tenantId);
        // 订单状态格式化处理
        list.forEach(orderStatusDO -> {
            orderStatusDO.setOrderStatusStr(AceDictionary.ORDER_STATUS.get(orderStatusDO.getOrderStatus()));
        });
        AceDictionary.ORDER_STATUS.forEach((key,value) -> {
            OrderStatusDO orderStatusDO = new OrderStatusDO();
            orderStatusDO.setOrderStatus(key);
            orderStatusDO.setTotal(0);
            orderStatusDO.setOrderStatusStr(value);
            if (!list.contains(orderStatusDO)){
                list.add(orderStatusDO);
            }
        });
        OrderTotalVO orderTotalVO = new OrderTotalVO();
        orderTotalVO.setOrderStatusList(list);
        orderTotalVO.setTodayOrderTotal(todayOrderTotal);
        orderTotalVO.setYesterdayTotal(yesterdayTotal);
        objectRestResponse.setData(orderTotalVO);
        return objectRestResponse;
    }


    private BigDecimal getYesterdayTotal(String tenantId){
        Date yesterday = DateUtils.addDays(new Date(),-1);
        String yesterdayFormat = DateUtils.formatDateTime(yesterday,DateUtils.COMPACT_DATE_FORMAT);
        String key = CommonConstants.ORDER_YESTERDAY_AMOUNT+yesterdayFormat;
        Object obj = redisUtil.get(key);
        BigDecimal yesterdayTotal = null;
        if(ObjectUtils.isEmpty(obj)){
            yesterdayTotal = this.mapper.yesterdayTotal(tenantId);
            redisUtil.set(key,yesterdayTotal,getTime());
        }else {
            yesterdayTotal = (BigDecimal)obj;
        }
        return yesterdayTotal;
    }

    /**
     * 获取当前时间距离凌晨多少秒
     * @return
     */
    private static long getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
        Long timeout = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return timeout;
    }
}