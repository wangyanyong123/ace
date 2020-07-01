package com.github.wxiaoqi.security.auth.module.oauth.rest;

import com.github.wxiaoqi.security.auth.module.oauth.biz.OauthClientDetailsBiz;
import com.github.wxiaoqi.security.auth.module.oauth.entity.OauthClientDetails;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauthClientDetails")
public class OauthClientDetailsController extends BaseController<OauthClientDetailsBiz, OauthClientDetails, String> {

}