package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizOrderLogisticsBiz;
import com.github.wxiaoqi.security.merchant.entity.BizOrderLogistics;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 订单物流信息表
 *
 * @author wangyanyong
 * @Date 2020-04-24 16:49:37
 */
@RestController
@RequestMapping("bizOrderLogistics")
@CheckClientToken
@CheckUserToken
public class BizOrderLogisticsController extends BaseController<BizOrderLogisticsBiz,BizOrderLogistics,String> {

}