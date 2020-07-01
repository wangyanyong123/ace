package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizReservationOrderOperationRecord;
import com.github.wxiaoqi.security.jinmao.vo.reservation.order.out.ReservationOrderOperationRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
public interface BizReservationOrderOperationRecordMapper extends CommonMapper<BizReservationOrderOperationRecord> {

    List<ReservationOrderOperationRecord> queryReservationOrderOperationRecord(@Param("orderId") String orderId);
}
