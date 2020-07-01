package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.api.vo.store.product.ProductStore;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.store.biz.MysqlStoreAsync;
import com.github.wxiaoqi.store.biz.ProductStoreBiz;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 库存管理
 */
@RestController
@RequestMapping("product")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class ProductStoreController extends BaseStoreController {

    @Autowired
    private ProductStoreBiz productStoreBiz;

    @Autowired
    private MysqlStoreAsync mysqlStoreAsync;

    @PostMapping("sale")
    @ApiOperation(value = "销售出库", notes = "销售出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse sale(@RequestBody @Valid ProductStore productStore){
        return productStoreBiz.productStore(productStore, AceDictionary.STORE_SALE_REDIS_OUT,0);
    }

    @PostMapping("sale/batch")
    @ApiOperation(value = "销售批量出库", notes = "销售批量出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse saleBatch(@RequestBody @Valid List<ProductStore> productStores){
        return productStoreBiz.productStoreBatch(productStores, AceDictionary.STORE_SALE_REDIS_OUT,0);
    }

    @PostMapping("cancel")
    @ApiOperation(value = "未支付取消归还", notes = "未支付取消归还",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse cancel(@RequestBody @Valid ProductStore productStore){
        return productStoreBiz.productStore(productStore, AceDictionary.STORE_CANCEL_REDIS_IN,0);
    }

    @PostMapping("cancel/batch")
    @ApiOperation(value = "未支付取消批量归还", notes = "未支付取消批量归还",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse cancelBatch(@RequestBody @Valid List<ProductStore> productStores){
        return this.baseBiz.productStoreBatch(productStores, AceDictionary.STORE_CANCEL_REDIS_IN,0);
    }

    @PostMapping("pay/sale")
    @ApiOperation(value = "支付成功出库", notes = "支付成功出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse paySale(@RequestBody @Valid ProductStore productStore){
        return productStoreBiz.mysqlStore(productStore,AceDictionary.STORE_PAY_SUCCESS_DB_OUT);
    }

    @PostMapping("pay/sale/batch")
    @ApiOperation(value = "支付成功批量出库", notes = "支付成功批量出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse paySale(@RequestBody @Valid List<ProductStore> productStores){
        return productStoreBiz.mysqlStoreBatch(productStores,AceDictionary.STORE_PAY_SUCCESS_DB_OUT);
    }

    @PostMapping("pay/sale/async")
    @ApiOperation(value = "支付成功异步出库", notes = "支付成功异步出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public void paySaleAsync(@RequestBody @Valid ProductStore productStore){
        mysqlStoreAsync.mysqlStore(productStore,AceDictionary.STORE_PAY_SUCCESS_DB_OUT);
    }

    @PostMapping("pay/sale/async/batch")
    @ApiOperation(value = "支付成功异步批量出库", notes = "支付成功异步批量出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public void paySaleAsyncBatch(@RequestBody @Valid List<ProductStore> productStores){
        productStores.forEach(productStore -> mysqlStoreAsync.mysqlStore(productStore,AceDictionary.STORE_PAY_SUCCESS_DB_OUT));
    }


    @PostMapping("pay/cancel")
    @ApiOperation(value = "支付后取消入库", notes = "支付后取消入库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse payCancel(@RequestBody @Valid ProductStore productStore){
        return productStoreBiz.mysqlStore(productStore,AceDictionary.STORE_PAY_CANCEL_DB_IN);
    }

    @PostMapping("pay/cancel/batch")
    @ApiOperation(value = "支付后取消批量入库", notes = "支付后取消批量入库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse payCancel(@RequestBody @Valid List<ProductStore> productStores){
        return productStoreBiz.mysqlStoreBatch(productStores,AceDictionary.STORE_PAY_CANCEL_DB_IN);
    }

    @PostMapping("pay/cancel/async")
    @ApiOperation(value = "支付后取消异步入库", notes = "支付后取消异步入库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public void payCancelAsync(@RequestBody @Valid ProductStore productStore){
        mysqlStoreAsync.mysqlStore(productStore,AceDictionary.STORE_PAY_CANCEL_DB_IN);
    }

    @PostMapping("pay/cancel/async/batch")
    @ApiOperation(value = "支付后取消异步批量入库", notes = "支付后取消异步批量入库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public void payCancelAsyncBatch(@RequestBody @Valid List<ProductStore> productStores){
        productStores.forEach(productStore -> mysqlStoreAsync.mysqlStore(productStore,AceDictionary.STORE_PAY_CANCEL_DB_IN));
    }

    @GetMapping("redis/store")
    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public CacheStore getCacheStore(String specId){
        CacheStore cacheStore = this.baseBiz.getCacheStore(specId);
        return cacheStore;
    }

    @PostMapping("redis/store/batch")
    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public List<CacheStoreProduct> getCacheStoreBatch(@RequestBody List<String> specIds){
        List<CacheStoreProduct> cacheStoreList = this.baseBiz.getCacheStoreBatch(specIds);
        return cacheStoreList;
    }
}