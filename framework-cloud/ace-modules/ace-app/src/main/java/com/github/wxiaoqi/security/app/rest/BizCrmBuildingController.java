package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmBuildingBiz;
import com.github.wxiaoqi.security.app.entity.BizCrmBuilding;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 楼栋表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("bizCrmBuilding")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BizCrmBuildingController extends BaseController<BizCrmBuildingBiz,BizCrmBuilding,String> {

}