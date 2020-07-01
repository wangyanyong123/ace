package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.store.biz.BizStoreLogBiz;
import com.github.wxiaoqi.store.entity.BizStoreLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author huangxl
 * @Date 2020-04-30 17:24:50
 */
@RestController
@RequestMapping("bizStoreLog")
@CheckClientToken
@CheckUserToken
public class BizStoreLogController extends BaseController<BizStoreLogBiz, BizStoreLog,String> {

}