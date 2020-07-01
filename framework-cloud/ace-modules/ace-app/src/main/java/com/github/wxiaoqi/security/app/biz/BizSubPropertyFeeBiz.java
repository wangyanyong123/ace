package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizSubPropertyFee;
import com.github.wxiaoqi.security.app.mapper.BizSubPropertyFeeMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 物业缴费订单明细表
 *
 * @author huangxl
 * @Date 2019-01-29 17:31:29
 */
@Service
public class BizSubPropertyFeeBiz extends BusinessBiz<BizSubPropertyFeeMapper,BizSubPropertyFee> {
}