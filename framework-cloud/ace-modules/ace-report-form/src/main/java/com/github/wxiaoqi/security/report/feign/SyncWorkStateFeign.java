package com.github.wxiaoqi.security.report.feign;


import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface SyncWorkStateFeign {

    @RequestMapping(value = "/orderEngine/syncWoToCRMNoUserLogin",method = RequestMethod.GET)
    ObjectRestResponse syncWoToCRMNoUserLogin(@RequestParam("id") String id);
}
