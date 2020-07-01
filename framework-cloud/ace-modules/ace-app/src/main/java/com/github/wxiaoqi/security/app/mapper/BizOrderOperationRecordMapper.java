package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizOrderOperationRecord;
import com.github.wxiaoqi.security.app.vo.order.out.OrderOperationRecordInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-23 10:28:58
 */
public interface BizOrderOperationRecordMapper extends CommonMapper<BizOrderOperationRecord> {

    List<OrderOperationRecordInfo> selectInfoVoByOrderId(@Param("orderId") String orderId);
}
