package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.entity.BizTransactionLog;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 订单工单流水日志表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
public interface BizTransactionLogMapper extends CommonMapper<BizTransactionLog> {

    public List<TransactionLogVo> selectTransactionLogListById(String id);
}
