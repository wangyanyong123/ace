package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizReservationOrderOperationRecordBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderOperationRecord;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
@RestController
@RequestMapping("bizReservationOrderOperationRecord")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderOperationRecordController extends BaseController<BizReservationOrderOperationRecordBiz, BizReservationOrderOperationRecord,String> {

}