package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.api.vo.order.in.QueryRefundAuditListParam;
import com.github.wxiaoqi.security.api.vo.order.out.RefundOrderAuditInfoVo;
import com.github.wxiaoqi.security.app.entity.BizRefundAudit;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 退款审核表
 * 
 * @author zxl
 * @Date 2019-03-28 14:08:45
 */
public interface BizRefundAuditMapper extends CommonMapper<BizRefundAudit> {

	String getOutTradeNoBySubId(@Param("subId") String subId);

	int getRefundAuditListTotal(Map<?, ?> map);

	List<RefundOrderAuditInfoVo> getRefundAuditList(Map<?, ?> map);

	String viewReason(@Param("auditId") String auditId);

    int countRefundAuditList(QueryRefundAuditListParam param);

    List<RefundOrderAuditInfoVo> selectRefundAuditList(QueryRefundAuditListParam param);

    List<BizRefundAudit> selectValidRefundAuditListBySubId(@Param("subId") String subId);

    /**
     * 获取当前支付记录，有效的总申请退款金额（除取消的申请）
     * @param actualId 支付id
     * @return refundAuditMapper
     */
    BigDecimal getValidApplyPrice(String actualId);
}
