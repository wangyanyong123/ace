package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizUserAudit;
import com.github.wxiaoqi.security.app.mapper.BizUserAuditMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.stereotype.Service;

/**
 * 用户申请成为房屋家属、租客表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
public class BizUserAuditBiz extends BusinessBiz<BizUserAuditMapper,BizUserAudit> {
}