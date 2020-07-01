package com.github.wxiaoqi.log.biz;

import com.github.wxiaoqi.log.entity.BizBusLog;
import com.github.wxiaoqi.log.mapper.BizBusLogMapper;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 
 *
 * @author zxl
 * @Date 2019-04-09 14:18:21
 */
@Service
public class BizBusLogBiz extends BusinessBiz<BizBusLogMapper,BizBusLog> {
}