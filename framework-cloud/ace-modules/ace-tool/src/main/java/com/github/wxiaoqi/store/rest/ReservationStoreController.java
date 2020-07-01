package com.github.wxiaoqi.store.rest;

import com.github.wxiaoqi.security.api.vo.store.CacheStoreListQuery;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.store.biz.ReservationStoreBiz;
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
@RequestMapping("reservation")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class ReservationStoreController extends BaseStoreController {

    @Autowired
    private ReservationStoreBiz reservationStoreBiz;

    @PostMapping("sale")
    @ApiOperation(value = "销售出库", notes = "销售出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse sale(@RequestBody @Valid ReservationStore reservationStore){
        return reservationStoreBiz.reservationStore(reservationStore, AceDictionary.STORE_SALE_REDIS_OUT,0);
    }

    @PostMapping("sale/batch")
    @ApiOperation(value = "销售批量出库", notes = "销售批量出库",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse saleBatch(@RequestBody @Valid List<ReservationStore> reservationStores){
        return reservationStoreBiz.reservationStoreBatch(reservationStores, AceDictionary.STORE_SALE_REDIS_OUT,0);
    }

    @PostMapping("cancel")
    @ApiOperation(value = "未支付取消归还", notes = "未支付取消归还",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse cancel(@RequestBody @Valid ReservationStore reservationStore){
        return reservationStoreBiz.reservationStore(reservationStore, AceDictionary.STORE_CANCEL_REDIS_IN,0);
    }

    @PostMapping("cancel/batch")
    @ApiOperation(value = "未支付取消批量归还", notes = "未支付取消批量归还",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse cancelBatch(@RequestBody @Valid List<ReservationStore> reservationStores){
        return reservationStoreBiz.reservationStoreBatch(reservationStores, AceDictionary.STORE_CANCEL_REDIS_IN,0);
    }

    @GetMapping("redis/store")
    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public List<CacheStoreQuery> getCacheStore(String specId){
        List<CacheStoreQuery> cacheStoreQueryList = reservationStoreBiz.getCacheStore(specId);
        return cacheStoreQueryList;
    }

    @PostMapping("redis/store/batch")
    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public List<CacheStoreListQuery> getCacheStoreBatch(@RequestBody List<String> specIds){
        List<CacheStoreListQuery> cacheStoreListQueryList = reservationStoreBiz.getCacheStoreBatch(specIds);
        return cacheStoreListQueryList;
    }


    @GetMapping("redis/sales/store")
    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
    @IgnoreUserToken
    @IgnoreClientToken
    public List<CacheStoreQuery> getSaleStore(String specId){
        List<CacheStoreQuery> cacheStoreQueryList = reservationStoreBiz.getSaleStore(specId);
        return cacheStoreQueryList;
    }

    @PostMapping("redis/sales/store/batch")
    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public List<CacheStoreListQuery> getSaleStoreBatch(@RequestBody List<String> specIds){
        List<CacheStoreListQuery> cacheStoreListQueryList = reservationStoreBiz.getSaleStoreBatch(specIds);
        return cacheStoreListQueryList;
    }
}