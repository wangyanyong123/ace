package com.github.wxiaoqi.job.feign.fallback;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.job.biz.BizJobFailRecordBiz;
import com.github.wxiaoqi.job.feign.AceXxlJobFeign;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务feign 调用失败
 */
@Slf4j
@Component
public class AceXxlJobFeignFallback implements AceXxlJobFeign {

    @Autowired
    private BizJobFailRecordBiz bizJobFailRecordBiz;
    @Override
    public ReturnT<String> add(XxlJobInfo jobInfo) {
        if(log.isWarnEnabled()){
            log.warn("添加定时任务失败，param:{}", JSON.toJSONString(jobInfo));
        }
        //记录日志
        bizJobFailRecordBiz.addJobFailRecord(jobInfo,"新增定时任务失败");
        return ReturnT.FAIL;
    }
}
