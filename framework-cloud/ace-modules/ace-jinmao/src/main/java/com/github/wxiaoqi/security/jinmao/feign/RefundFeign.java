package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditparamVo;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface RefundFeign {

    @PostMapping(value = "/refundAudit/getRefundAuditList")
    ObjectRestResponse getRefundAuditList(@RequestBody RefundAuditparamVo refundAuditparamVo);

    @PostMapping("/refundAudit/refundAudit")
    ObjectRestResponse refundAudit( @RequestBody RefundAuditVo refundAuditVo);

    @GetMapping("/refundAudit/restartRefund")
    ObjectRestResponse restartRefund(@RequestParam("auditId") String auditId);

    @GetMapping("/refundAudit/viewReason")
    ObjectRestResponse viewReason(@RequestParam("auditId")String auditId);

    /**
     * 强制审核通过
     * @param orderId
     * @return
     */
    @GetMapping("/refundAudit/forceRefundAudit")
    ObjectRestResponse forceRefundAudit(@RequestParam("orderId")  String orderId);
}
