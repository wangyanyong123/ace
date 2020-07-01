package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-admin", configuration = FeignApplyConfiguration.class)
public interface AdminFeign {
    /**
     * 获取字典对对应值
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/web/bizDict/type/{code}", method = RequestMethod.GET)
    public Map<String, String> getDictValue(@PathVariable("code") String code);
}
