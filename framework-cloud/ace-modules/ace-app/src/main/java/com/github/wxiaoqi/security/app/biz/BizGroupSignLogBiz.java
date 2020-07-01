package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizGroupSignLog;
import com.github.wxiaoqi.security.app.mapper.BizGroupSignLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 小组成员打卡日志表
 *
 * @author zxl
 * @Date 2018-12-19 16:47:00
 */
@Service
public class BizGroupSignLogBiz extends BusinessBiz<BizGroupSignLogMapper,BizGroupSignLog> {
}