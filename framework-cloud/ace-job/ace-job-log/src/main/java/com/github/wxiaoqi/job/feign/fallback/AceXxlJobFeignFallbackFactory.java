package com.github.wxiaoqi.job.feign.fallback;

import com.github.wxiaoqi.job.feign.AceXxlJobFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务feign 调用失败
 */
@Slf4j
@Component
public class AceXxlJobFeignFallbackFactory implements FallbackFactory<AceXxlJobFeign> {

    @Autowired
    private AceXxlJobFeignFallback aceXxlJobFeignFallback;

    @Override
    public AceXxlJobFeign create(Throwable throwable) {
        log.error("定时任务操作异常，message:{}",throwable.getMessage());
        return aceXxlJobFeignFallback;
    }
}
