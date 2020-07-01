package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizUserSignRule;
import com.github.wxiaoqi.security.app.mapper.BizUserSignRuleMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-签到规则表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Service
public class BizUserSignRuleBiz extends BusinessBiz<BizUserSignRuleMapper,BizUserSignRule> {
}