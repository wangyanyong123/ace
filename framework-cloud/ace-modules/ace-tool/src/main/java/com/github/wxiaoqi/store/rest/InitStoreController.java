package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.store.biz.InitStoreBiz;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 库存管理
 */
@RestController
@RequestMapping("store")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class InitStoreController extends BaseStoreController {

    @Autowired
    private InitStoreBiz initStoreBiz;
    @GetMapping("init")
    @ApiOperation(value = "初始化库存", notes = "初始化库存",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse addStore(){
        return initStoreBiz.initStore();
    }


    @GetMapping("update/group")
    @ApiOperation(value = "更新团购库存", notes = "更新团购库存",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse updateGroupStore(){
        return initStoreBiz.updateGroupStore();
    }

    @GetMapping("modify/store")
    @ApiOperation(value = "修复重复的库存数据", notes = "修复重复的库存数据",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse duplicateStoreHandle(){
        return initStoreBiz.duplicateStoreHandle();
    }
}