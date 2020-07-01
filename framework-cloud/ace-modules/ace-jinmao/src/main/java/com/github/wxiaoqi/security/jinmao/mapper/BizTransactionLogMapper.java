package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.jinmao.entity.BizTransactionLog;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单工单流水日志表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
public interface BizTransactionLogMapper extends CommonMapper<BizTransactionLog> {

    public List<TransactionLogVo> selectTransactionLogListById(String id);
    public Integer selectAppraisalValBySubId(@Param("subId") String subId);
}
