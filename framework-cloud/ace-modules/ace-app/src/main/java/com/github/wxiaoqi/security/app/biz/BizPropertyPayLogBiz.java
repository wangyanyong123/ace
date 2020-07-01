package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizPropertyPayLog;
import com.github.wxiaoqi.security.app.mapper.BizPropertyPayLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 物业缴费支付日志表
 *
 * @Date 2019-02-18 13:59:22
 */
@Service
public class BizPropertyPayLogBiz extends BusinessBiz<BizPropertyPayLogMapper,BizPropertyPayLog> {
}