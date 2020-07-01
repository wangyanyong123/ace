package com.github.wxiaoqi.security.app.fegin;

import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.api.vo.store.product.ProductStore;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface StoreProductFegin {

    @PostMapping("product/sale")
    @ApiOperation(value = "销售出库", notes = "销售出库",httpMethod = "POST")
    ObjectRestResponse sale(@RequestBody @Valid ProductStore productStore);

    @PostMapping("product/sale/batch")
    @ApiOperation(value = "销售批量出库", notes = "销售批量出库",httpMethod = "POST")
    ObjectRestResponse saleBatch(@RequestBody @Valid List<ProductStore> productStores);

    @PostMapping("product/cancel")
    @ApiOperation(value = "未支付取消归还", notes = "未支付取消归还",httpMethod = "POST")
    ObjectRestResponse cancel(@RequestBody @Valid ProductStore productStore);

    @PostMapping("product/cancel/batch")
    @ApiOperation(value = "未支付取消批量归还", notes = "未支付取消批量归还",httpMethod = "POST")
    ObjectRestResponse cancelBatch(@RequestBody @Valid List<ProductStore> productStores);

    @PostMapping("product/pay/sale")
    @ApiOperation(value = "支付成功出库", notes = "支付成功出库",httpMethod = "POST")
    ObjectRestResponse paySale(@RequestBody @Valid ProductStore productStore);

    @PostMapping("product/pay/sale/batch")
    @ApiOperation(value = "支付成功批量出库", notes = "支付成功批量出库",httpMethod = "POST")
    ObjectRestResponse paySaleBatch(@RequestBody @Valid List<ProductStore> productStores);

    @PostMapping("product/pay/sale/async")
    @ApiOperation(value = "支付成功异步出库", notes = "支付成功异步出库",httpMethod = "POST")
    void paySaleAsync(@RequestBody @Valid ProductStore productStore);

    @PostMapping("product/pay/sale/async/batch")
    @ApiOperation(value = "支付成功异步批量出库", notes = "支付成功异步批量出库",httpMethod = "POST")
    void paySaleAsyncBatch(@RequestBody @Valid List<ProductStore> productStores);

    @GetMapping("product/redis/store")
    @ApiOperation(value = "获取库存信息", notes = "获取库存信息",httpMethod = "GET")
    CacheStore getCacheStore(@RequestParam("specId") String specId);

    @PostMapping("product/redis/store/batch")
    @ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
    List<CacheStoreProduct> getCacheStoreBatch(@RequestBody List<String> specIds);

    @PostMapping("pay/cancel")
    @ApiOperation(value = "支付后取消入库", notes = "支付后取消入库",httpMethod = "POST")
    ObjectRestResponse payCancel(@RequestBody @Valid ProductStore productStore);

    @PostMapping("pay/cancel/batch")
    @ApiOperation(value = "支付后取消批量入库", notes = "支付后取消批量入库",httpMethod = "POST")
    ObjectRestResponse payCancel(@RequestBody @Valid List<ProductStore> productStores);

    @PostMapping("pay/cancel/async")
    @ApiOperation(value = "支付后取消异步入库", notes = "支付后取消异步入库",httpMethod = "POST")
    void payCancelAsync(@RequestBody @Valid ProductStore productStore);

    @PostMapping("pay/cancel/async/batch")
    @ApiOperation(value = "支付后取消异步批量入库", notes = "支付后取消异步批量入库",httpMethod = "POST")
    void payCancelAsyncBatch(@RequestBody @Valid List<ProductStore> productStores);
}
