package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizReservationOrderOperationRecordBiz;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderOperationRecord;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-26 15:38:24
 */
@RestController
@RequestMapping("bizReservationOrderOperationRecord")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderOperationRecordController extends BaseController<BizReservationOrderOperationRecordBiz,BizReservationOrderOperationRecord,String> {

}