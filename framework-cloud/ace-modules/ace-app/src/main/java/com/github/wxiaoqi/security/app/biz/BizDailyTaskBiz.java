package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizDailyTask;
import com.github.wxiaoqi.security.app.mapper.BizDailyTaskMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-每日随机任务表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Service
public class BizDailyTaskBiz extends BusinessBiz<BizDailyTaskMapper,BizDailyTask> {
}