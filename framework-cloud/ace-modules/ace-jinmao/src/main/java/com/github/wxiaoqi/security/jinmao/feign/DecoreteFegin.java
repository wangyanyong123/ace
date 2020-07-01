package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface DecoreteFegin {

    @RequestMapping(value = "/bizDecoreteApply/getMyDecoreteInfo", method = RequestMethod.GET)
    public ObjectRestResponse getMyDecoreteInfo(@RequestParam("id") String id);
}
