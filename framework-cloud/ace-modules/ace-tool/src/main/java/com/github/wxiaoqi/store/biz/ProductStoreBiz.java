package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.constants.CommonConstant;
import com.github.wxiaoqi.constants.RedisPreFixConstant;
import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreListQuery;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.product.BeforeProductStore;
import com.github.wxiaoqi.security.api.vo.store.product.ProductStore;
import com.github.wxiaoqi.security.api.vo.store.reservation.BeforeReservationStore;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.store.entity.BizStore;
import com.github.wxiaoqi.store.mapper.BizStoreMapper;
import com.github.wxiaoqi.store.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存管理
 */
@Slf4j
@Service
public class ProductStoreBiz extends BusinessBiz<BizStoreMapper, BizStore> {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BizStoreLogBiz bizStoreLogBiz;

    @Value("${store.times}")
    private Integer storeTimes;

    /**
     * Redis出入库
     * @param productStore
     * @return
     */
    @Transactional
    public ObjectRestResponse productStore(ProductStore productStore, Integer operationType,Integer times){
        List<BeforeProductStore> beforeProductStores = new ArrayList<>();
        ObjectRestResponse objectRestResponse = this.repeatTimes(productStore,operationType,beforeProductStores,times);
        if(objectRestResponse.success()){
            if(CollectionUtils.isNotEmpty(beforeProductStores)){
                BeforeProductStore beforeProductStore = beforeProductStores.get(0);
                objectRestResponse = this.updateRedisStore(beforeProductStore);
            }
        }else {
            // 添加日志
            bizStoreLogBiz.addStoreLogCheck(productStore,operationType,objectRestResponse);
        }
        return objectRestResponse;
    }




    /**
     * redis出入库 批量
     * @param productStoreDTOS
     * @return
     */
    @Transactional
    public ObjectRestResponse productStoreBatch(List<ProductStore> productStoreDTOS, Integer operationType,Integer times){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<BeforeProductStore> beforeProductStores = new ArrayList<>();
        for (ProductStore productStore : productStoreDTOS){
            ObjectRestResponse resultRestResponse  = this.repeatTimes(productStore,operationType,beforeProductStores,times);
            // 添加日志
            bizStoreLogBiz.addStoreLogCheck(productStore,operationType,resultRestResponse);
        }

        // 校验后处理
        if(beforeProductStores.size() == productStoreDTOS.size()){
            beforeProductStores.forEach(beforeProductStore -> this.updateRedisStore(beforeProductStore));
        }else {
            objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.NOT_ENOUGH_STOCK);
        }
        try {
            log.info("商品批量操作类型：{}-{}，操作结果：{}-{}，参数信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),objectRestResponse.success(),objectRestResponse.getMessage(),JacksonJsonUtil.beanToJson(beforeProductStores));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectRestResponse;
    }


    /**
     * mysql出库
     * @param productStore
     * @return
     */
    @Transactional
    public ObjectRestResponse mysqlStore(ProductStore productStore,Integer oprationType){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BizStore bizStore = this.mapper.queryStoreNum(productStore.getSpecId(),0);
        if(ObjectUtils.isEmpty(bizStore)){
            return redisUtil.errorRestResponse(ResponseCodeEnum.MYSQL_STORE_UPDATE_FAIL);
        }
        int currentNum = 0;
        if(bizStore.getIsLimit()){
            currentNum = productStore.getAccessNum() + bizStore.getStoreNum();
            int num = this.mapper.updateStore(productStore.getSpecId(),currentNum,0);
            if(num < 0){
                objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.MYSQL_STORE_UPDATE_FAIL);
                log.error("商品支付成功，操作结果：{}-{}，参数信息：{}",objectRestResponse.success(),objectRestResponse.getMessage(),productStore);
            }
        }
        bizStoreLogBiz.addStoreLogUpdate(productStore,bizStore.getStoreNum(),currentNum,bizStore.getIsLimit(),oprationType,objectRestResponse);
        return objectRestResponse;
    }


    /**
     * mysql出入库 批量
     * @param productStoreDTOS
     * @return
     */
    @Transactional
    public ObjectRestResponse mysqlStoreBatch(List<ProductStore> productStoreDTOS,Integer oprationType){
        ObjectRestResponse result = new ObjectRestResponse();
        productStoreDTOS.forEach(productStoreDTO -> this.mysqlStore(productStoreDTO,oprationType));
        return result;
    }


    public CacheStore getCacheStore(String specId){
        CacheStore cacheStoreOld =  null;
        String lockKey = specId;
        if(StringUtils.isNotEmpty(lockKey)){
            cacheStoreOld = redisUtil.getRedisStore(lockKey);
        }
        return cacheStoreOld;
    }

    public List<CacheStoreProduct> getCacheStoreBatch(List<String> specIds){
        List<CacheStoreProduct> cacheStoreProductList = new ArrayList<>();
        specIds.forEach(specId ->{
            CacheStoreProduct cacheStoreProduct = new CacheStoreProduct();
            CacheStore cacheStoreOld = this.getCacheStore(specId);
           if(!ObjectUtils.isEmpty(cacheStoreOld)){
               cacheStoreProduct.setSpecId(specId);
               cacheStoreProduct.setCacheStore(cacheStoreOld);
               cacheStoreProductList.add(cacheStoreProduct);
           }
        });
        return cacheStoreProductList;
    }

    // 重试更新库存
    private ObjectRestResponse repeatTimes(ProductStore productStore,Integer operationType,List<BeforeProductStore> beforeProductStores,Integer times){
        ObjectRestResponse objectRestResponse = this.setRedisLock(productStore,operationType,beforeProductStores);
        if(!objectRestResponse.success()){
            // 如果是锁设置失败 则进行重试
            if(objectRestResponse.getStatus() == ResponseCodeEnum.REdiS_LOCK_SET_FAIL.getKey()){
                if(times < storeTimes){
                    times++;
                    try {
                        // 休眠一秒后重试
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.repeatTimes(productStore,operationType,beforeProductStores,times);
                    log.error("商品：{}：锁设置失败，重试次数：{}",redisUtil.getKey(productStore.getSpecId(),0),times);
                }
            }
        }
        return objectRestResponse;
    }

    // 库存更新前参数校验
    private ObjectRestResponse setRedisLock(ProductStore productStore, Integer operationType, List<BeforeProductStore> beforeProductStores){
        String lockKey = productStore.getSpecId();

        //设置锁
        if(redisUtil.setLock(lockKey, CommonConstant.SET_LOCK_START_INDEX)){

            // 获取redis缓存
            CacheStore cacheStoreOld = redisUtil.getRedisStore(lockKey);
            if(ObjectUtils.isEmpty(cacheStoreOld)){
                redisUtil.release(lockKey);
                log.error("商品批量操作类型：{}-{}，redis出入库时未找到库存信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),productStore);
                return redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_NOT_EXISTS);
            }

            Integer currentNum = cacheStoreOld.getStoreNum();
            if(cacheStoreOld.getIsLimit()){
                if((cacheStoreOld.getStoreNum() + productStore.getAccessNum()) < 0){
                    redisUtil.release(lockKey);
                    log.error("商品批量操作类型：{}-{}，redis库存数量不足：当前库存信息{}，更新库存信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),cacheStoreOld,productStore);
                    return redisUtil.errorRestResponse(ResponseCodeEnum.NOT_ENOUGH_STOCK);
                }
                // 计算更新后的库存
                currentNum = cacheStoreOld.getStoreNum() + productStore.getAccessNum();
            }

            BeforeProductStore beforeProductStore = new BeforeProductStore();
            beforeProductStore.setCacheStoreOld(cacheStoreOld);
            beforeProductStore.setCurrentNum(currentNum);
            beforeProductStore.setLockKey(lockKey);
            beforeProductStore.setOperationType(operationType);
            beforeProductStore.setProductStore(productStore);
            beforeProductStores.add(beforeProductStore);
            return ObjectRestResponse.ok();
        }
        return redisUtil.errorRestResponse(ResponseCodeEnum.REdiS_LOCK_SET_FAIL);
    }


    // redis库存更新操作
    private ObjectRestResponse updateRedisStore(BeforeProductStore beforeProductStore){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if(!setRedisStore(beforeProductStore.getLockKey(),beforeProductStore.getCacheStoreOld().getIsLimit(),beforeProductStore.getCurrentNum())){
            objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_UPDATE_FAIL);
            log.error("商品操作类型：{}-{}，结果：{}-{}，参数信息：{}",beforeProductStore.getOperationType(),AceDictionary.STORE_OPERATION_TYPE.get(beforeProductStore.getOperationType()),objectRestResponse.success(),objectRestResponse.getMessage(),beforeProductStore.getProductStore());
        }
         bizStoreLogBiz.addStoreLogUpdate(beforeProductStore.getProductStore(),beforeProductStore.getCacheStoreOld().getStoreNum(),beforeProductStore.getCurrentNum(),beforeProductStore.getCacheStoreOld().getIsLimit(),beforeProductStore.getOperationType(),objectRestResponse);
        // 释放锁
        redisUtil.release(beforeProductStore.getLockKey());
        return objectRestResponse;
    }

    // 设置redis库存信息
    private Boolean setRedisStore(String lockKey,Boolean isLimit,Integer accessNum){
        CacheStore cacheStore = new CacheStore();
        cacheStore.setIsLimit(isLimit);
        cacheStore.setStoreNum(accessNum);
        try {
            return redisUtil.hset(RedisPreFixConstant.ACE_STORE_KEY,lockKey, JacksonJsonUtil.beanToJson(cacheStore));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}