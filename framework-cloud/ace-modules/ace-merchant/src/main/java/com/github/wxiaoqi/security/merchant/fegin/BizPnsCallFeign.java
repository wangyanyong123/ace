package com.github.wxiaoqi.security.merchant.fegin;

import com.github.wxiaoqi.security.api.vo.pns.in.AXBBindingDTO;
import com.github.wxiaoqi.security.api.vo.pns.out.AXBResult;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface BizPnsCallFeign {
    /**
     * 隐私号绑定
     * @param axbBindingDTO
     * @return
     */
    @RequestMapping(value = "/axb/binding/inner", method = RequestMethod.POST)
    AXBResult axbBindingInner(@RequestBody AXBBindingDTO axbBindingDTO);

    /**
     * 隐私号解绑
     * @param bindId
     * @return
     */
    @RequestMapping(value = "/axb/unbinding/inner",method = RequestMethod.GET)
    AXBResult axbUnbindingInner(@RequestParam("bindId") String bindId);

    /**
     * 解绑失败再次添加任务去解绑
     * @param bindId
     */
    @RequestMapping(value = "/axb/add/timeout/job",method = RequestMethod.GET)
    void addBindTimeoutJob(@RequestParam("bindId") String bindId);

}
