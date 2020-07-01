package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizDecisionAnnexBiz;
import com.github.wxiaoqi.security.app.entity.BizDecisionAnnex;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 决策附件表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
@RestController
@RequestMapping("bizDecisionAnnex")
@CheckClientToken
@CheckUserToken
public class BizDecisionAnnexController extends BaseController<BizDecisionAnnexBiz,BizDecisionAnnex,String> {

}