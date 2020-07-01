package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizReservationOrderCommentBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderComment;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-25 18:23:26
 */
@RestController
@RequestMapping("bizReservationOrderComment")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderCommentController extends BaseController<BizReservationOrderCommentBiz, BizReservationOrderComment,String> {

}