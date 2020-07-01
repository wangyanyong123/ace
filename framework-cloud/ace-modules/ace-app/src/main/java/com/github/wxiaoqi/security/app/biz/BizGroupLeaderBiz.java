package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizGroupLeader;
import com.github.wxiaoqi.security.app.mapper.BizGroupLeaderMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 小组组长表
 *
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@Service
public class BizGroupLeaderBiz extends BusinessBiz<BizGroupLeaderMapper,BizGroupLeader> {
}