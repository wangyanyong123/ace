package com.github.wxiaoqi.security.jinmao.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizUserTaskLog;
import com.github.wxiaoqi.security.jinmao.mapper.BizUserTaskLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-用户任务日志表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Service
public class BizUserTaskLogBiz extends BusinessBiz<BizUserTaskLogMapper,BizUserTaskLog> {
}