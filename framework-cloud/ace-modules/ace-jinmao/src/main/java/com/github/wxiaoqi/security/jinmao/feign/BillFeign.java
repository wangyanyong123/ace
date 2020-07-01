package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.vo.propertybill.UserBillVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface BillFeign {

    @RequestMapping(value = "/bill/getUserBillList", method = RequestMethod.GET)
    public ObjectRestResponse<UserBillVo> getUserBillList(@RequestParam String projectId, @RequestParam Integer currentPage, @RequestParam Integer pageSize);

}
