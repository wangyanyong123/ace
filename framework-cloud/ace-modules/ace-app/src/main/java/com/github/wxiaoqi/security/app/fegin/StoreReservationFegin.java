package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.api.vo.store.CacheStoreListQuery;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface StoreReservationFegin {

    @PostMapping("reservation/sale")
    @ApiOperation(value = "销售出库", notes = "销售出库",httpMethod = "POST")
    ObjectRestResponse sale(@RequestBody @Valid ReservationStore reservationStore);

    @PostMapping("reservation/sale/batch")
    @ApiOperation(value = "销售批量出库", notes = "销售批量出库",httpMethod = "POST")
    ObjectRestResponse saleBatch(@RequestBody @Valid List<ReservationStore> reservationStores);

    @PostMapping("reservation/cancel")
    @ApiOperation(value = "未支付取消归还", notes = "未支付取消归还",httpMethod = "POST")
    ObjectRestResponse cancel(@RequestBody @Valid ReservationStore reservationStore);

    @PostMapping("reservation/cancel/batch")
    @ApiOperation(value = "未支付取消批量归还", notes = "未支付取消批量归还",httpMethod = "POST")
    ObjectRestResponse cancelBatch(@RequestBody @Valid List<ReservationStore> reservationStores);

//    @GetMapping("reservation/redis/store")
//    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
//    List<CacheStoreQuery> getCacheStore(@RequestParam("specId") String specId);
//
//    @PostMapping("reservation/redis/store/batch")
//    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
//    List<CacheStoreListQuery> getCacheStoreBatch(@RequestBody List<String> specIds);


    @GetMapping("reservation/redis/sales/store")
    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
    List<CacheStoreQuery> getSaleStore(@RequestParam("specId") String specId);

    @PostMapping("reservation/redis/sales/store/batch")
    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
    List<CacheStoreListQuery> getSaleStoreBatch(@RequestBody List<String> specIds);
}
