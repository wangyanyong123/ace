package com.github.wxiaoqi.sms.biz;

import com.github.wxiaoqi.sms.entity.SysMsgInfo;
import com.github.wxiaoqi.sms.mapper.SysMsgInfoMapper;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 消息推送
 *
 * @author zxl
 * @Date 2018-11-21 11:06:38
 */
@Service
public class SysMsgInfoBiz extends BusinessBiz<SysMsgInfoMapper,SysMsgInfo> {
}