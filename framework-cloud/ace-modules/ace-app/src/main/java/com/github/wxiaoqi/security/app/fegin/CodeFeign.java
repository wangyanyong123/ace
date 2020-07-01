package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ace
 * @create 2018/11/28
 */
@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface CodeFeign {

    /**
     * 获取编码
     */
    @RequestMapping(value = "/code/getCode",method = {RequestMethod.GET})
    public ObjectRestResponse<String> getCode(@RequestParam("searchKey") String searchKey, @RequestParam("prefixCode") String prefixCode,
                                              @RequestParam("codeSize") String codeSize, @RequestParam("sequenceType") String sequenceType);

    }
