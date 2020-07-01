package com.github.wxiaoqi.security.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizGroupMember;
import com.github.wxiaoqi.security.app.mapper.BizGroupMemberMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

/**
 * 小组成员表
 *
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@Service
public class BizGroupMemberBiz extends BusinessBiz<BizGroupMemberMapper,BizGroupMember> {
}