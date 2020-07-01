package com.github.wxiaoqi.security.jinmao.feign;

/**
 *
 */

import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreListQuery;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.pc.PCStore;
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
public interface StoreFegin {

	@PostMapping("pc/add")
	@ApiOperation(value = "添加库存", notes = "添加库存信息",httpMethod = "POST")
	ObjectRestResponse addStore(@RequestBody @Valid List<PCStore> pcStores);

	@PostMapping("pc/update")
	@ApiOperation(value = "修改库存", notes = "修改库存信息",httpMethod = "POST")
	ObjectRestResponse updateStore(@RequestBody @Valid List<PCStore> pcStores);

	@GetMapping("product/redis/store")
	@ApiOperation(value = "获取商品库存信息", notes = "获取商品库存信息",httpMethod = "GET")
	CacheStore getProductCacheStore(@RequestParam("specId") String specId);

	@GetMapping("reservation/redis/store")
	@ApiOperation(value = "获取服务库存信息", notes = "获取服务库存信息",httpMethod = "GET")
	List<CacheStoreQuery> getReservationCacheStore(@RequestParam("specId") String specId);


	@PostMapping("product/redis/store/batch")
	@ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
	List<CacheStoreProduct> getCacheStoreBatch(@RequestBody List<String> specIds);

	@PostMapping("reservation/redis/store/batch")
	@ApiOperation(value = "批量获取库存信息", notes = "批量获取库存信息",httpMethod = "POST")
	List<CacheStoreListQuery> getReservationCacheStoreBatch(@RequestBody List<String> specIds);

}
