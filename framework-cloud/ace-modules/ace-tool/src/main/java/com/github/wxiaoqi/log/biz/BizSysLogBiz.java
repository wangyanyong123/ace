package com.github.wxiaoqi.log.biz;

import com.github.wxiaoqi.log.entity.BizSysLog;
import com.github.wxiaoqi.log.mapper.BizSysLogMapper;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 
 *
 * @author zxl
 * @Date 2019-04-09 16:40:10
 */
@Service
public class BizSysLogBiz extends BusinessBiz<BizSysLogMapper,BizSysLog> {
}