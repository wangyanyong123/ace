package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizOrderLogisticsBiz;
import com.github.wxiaoqi.security.app.entity.BizOrderLogistics;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 订单物流信息表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@RestController
@RequestMapping("bizOrderLogistics")
@CheckClientToken
@CheckUserToken
public class BizOrderLogisticsController extends BaseController<BizOrderLogisticsBiz,BizOrderLogistics,String> {

}