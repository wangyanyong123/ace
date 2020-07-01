package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizUserInviteBiz;
import com.github.wxiaoqi.security.app.entity.BizUserInvite;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户邀请成为房屋成员表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("bizUserInvite")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BizUserInviteController extends BaseController<BizUserInviteBiz,BizUserInvite,String> {

}