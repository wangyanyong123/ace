package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizDecisionVoteBiz;
import com.github.wxiaoqi.security.app.entity.BizDecisionVote;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 决策投票表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
@RestController
@RequestMapping("bizDecisionVote")
@CheckClientToken
@CheckUserToken
public class BizDecisionVoteController extends BaseController<BizDecisionVoteBiz,BizDecisionVote,String> {

}