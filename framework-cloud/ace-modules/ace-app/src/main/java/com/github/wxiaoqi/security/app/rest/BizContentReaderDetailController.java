package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容(产品)阅读详情表
 *
 * @author zxl
 * @Date 2018-12-13 09:56:49
 */
@RestController
@RequestMapping("bizContentReaderDetail")
@CheckClientToken
@CheckUserToken
public class BizContentReaderDetailController  {

}