package com.github.wxiaoqi.security.jinmao.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizUserSignLog;
import com.github.wxiaoqi.security.jinmao.mapper.BizUserSignLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-用户签到日志表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Service
public class BizUserSignLogBiz extends BusinessBiz<BizUserSignLogMapper,BizUserSignLog> {
}