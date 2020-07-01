package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizFacilitiesBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizFacilities;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 道闸表
 *
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
@RestController
@RequestMapping("bizFacilities")
@CheckClientToken
@CheckUserToken
public class BizFacilitiesController  {

}