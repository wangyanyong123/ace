package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.security.api.vo.store.pc.PCStore;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.store.biz.PCStoreBiz;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 库存管理
 */
@RestController
@RequestMapping("pc")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class PCStoreController extends BaseStoreController {

    @Autowired
    private PCStoreBiz pcStoreBiz;

    @PostMapping("add")
    @ApiOperation(value = "添加库存", notes = "添加库存信息",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse addStore(@RequestBody @Valid List<PCStore> pcStores){
        return pcStoreBiz.updateStore(pcStores, AceDictionary.STORE_PC_ADD,0);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改库存", notes = "修改库存信息",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse updateStore(@RequestBody @Valid List<PCStore> pcStores){
        return pcStoreBiz.updateStore(pcStores, AceDictionary.STORE_PC_UPDATE,0);
    }
}