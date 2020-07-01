package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizDecisionAnnexBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizDecisionAnnex;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 决策附件表
 *
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
@RestController
@RequestMapping("bizDecisionAnnex")
@CheckClientToken
@CheckUserToken
public class BizDecisionAnnexController extends BaseController<BizDecisionAnnexBiz,BizDecisionAnnex,String> {

}