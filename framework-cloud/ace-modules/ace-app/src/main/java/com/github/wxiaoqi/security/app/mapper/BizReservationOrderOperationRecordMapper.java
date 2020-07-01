package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizReservationOrderOperationRecord;
import com.github.wxiaoqi.security.app.reservation.vo.ReservationOrderOperationRecordVO;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
public interface BizReservationOrderOperationRecordMapper extends CommonMapper<BizReservationOrderOperationRecord> {

    List<ReservationOrderOperationRecordVO> queryReservationOrderOperationRecord(@Param("orderId") String orderId);
}
