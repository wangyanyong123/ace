package com.github.wxiaoqi.sms.biz;

import com.github.wxiaoqi.sms.entity.SysSms;
import com.github.wxiaoqi.sms.mapper.SysSmsMapper;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 短信发送
 *
 * @author zxl
 * @Date 2018-11-20 18:51:20
 */
@Service
public class SysSmsBiz extends BusinessBiz<SysSmsMapper,SysSms> {
}