package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizUserTaskLog;
import com.github.wxiaoqi.security.app.mapper.BizUserTaskLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 运营服务-用户任务日志表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Service
public class BizUserTaskLogBiz extends BusinessBiz<BizUserTaskLogMapper,BizUserTaskLog> {
}