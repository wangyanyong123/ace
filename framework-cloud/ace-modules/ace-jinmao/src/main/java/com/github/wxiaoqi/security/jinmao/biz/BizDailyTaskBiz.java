package com.github.wxiaoqi.security.jinmao.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizDailyTask;
import com.github.wxiaoqi.security.jinmao.mapper.BizDailyTaskMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-每日随机任务表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Service
public class BizDailyTaskBiz extends BusinessBiz<BizDailyTaskMapper,BizDailyTask> {
}