package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizDecisionVoteBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizDecisionVote;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 决策投票表
 *
 * @author guohao
 * @Date 2020-06-04 13:33:20
 */
@RestController
@RequestMapping("bizDecisionVote")
@CheckClientToken
@CheckUserToken
public class BizDecisionVoteController extends BaseController<BizDecisionVoteBiz,BizDecisionVote,String> {

}