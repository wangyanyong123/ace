package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.SysModuleProjectBiz;
import com.github.wxiaoqi.security.jinmao.entity.SysModuleProject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:15 2018/11/14
 * @Modified By:
 */
@RestController
@RequestMapping("web/sysModuleProject")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class SysModuleProjectController extends BaseController<SysModuleProjectBiz,SysModuleProject,String> {

}