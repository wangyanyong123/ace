package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.RefundOrderAuditInfoVo;
import com.github.wxiaoqi.security.jinmao.entity.BizRefundAudit;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

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

	Map selectBySubId(@Param("subId") String subId);
}
