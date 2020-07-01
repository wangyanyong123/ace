package com.github.wxiaoqi.security.jinmao.controller.app;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.jinmao.vo.user.UserInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.user.UserParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/14 10:46
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("operationUser")
@CheckClientToken
@CheckUserToken
@Api(tags = "运营管理-用户管理")
public class OperationUserController {

    @Autowired
    private BaseAppServerUserBiz serverUserBiz;

    @GetMapping(value = "/checkUser")
    @ApiOperation(value = "检测用户是否存在", notes = "检测用户是否存在",httpMethod = "GET")
    public ObjectRestResponse checkUser(String phone) {
        return serverUserBiz.checkUser(phone);
    }


    @GetMapping(value = "/getUserList")
    @ApiOperation(value = "获取运营用户列表", notes = "获取运营用户列表",httpMethod = "GET")
    public TableResultResponse getUserList(String searchVal,String isOperation,Integer page,Integer limit) {
        List<UserInfoVo> userListVos =  serverUserBiz.getUserList(searchVal, isOperation, page, limit);
        int total = serverUserBiz.getUserListTotal(searchVal, isOperation);
        return new TableResultResponse(total,userListVos  );
    }

    @GetMapping(value = "/getUserDetail")
    @ApiOperation(value = "获取运营用户详情", notes = "获取运营用户详情",httpMethod = "GET")
    public ObjectRestResponse getUserDetail(String id) {
        return serverUserBiz.getUserDetail(id);
    }

    @GetMapping(value = "/updateStatus")
    @ApiOperation(value = "启用/禁用用户", notes = "启用/禁用用户",httpMethod = "GET")
    public ObjectRestResponse updateStatus(String isOperation,String id) {
        return serverUserBiz.updateStatus(id,isOperation);
    }

    @DeleteMapping(value = "/deleteUser")
    @ApiOperation(value = "删除用户", notes = "删除用户",httpMethod = "GET")
    public ObjectRestResponse deleteUser(String id) {
        return serverUserBiz.deleteUser(id);
    }

    @PutMapping(value = "/createUser")
    @ApiOperation(value = "创建用户", notes = "创建用户",httpMethod = "PUT")
    public ObjectRestResponse createUser(String phone) {
        return serverUserBiz.createUser(phone);
    }
}
