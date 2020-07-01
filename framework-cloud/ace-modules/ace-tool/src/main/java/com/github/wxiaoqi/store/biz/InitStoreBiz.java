package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.constants.RedisPreFixConstant;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.store.entity.BizStore;
import com.github.wxiaoqi.store.mapper.BizStoreMapper;
import com.github.wxiaoqi.store.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存管理
 */
@Slf4j
@Service
public class InitStoreBiz extends BusinessBiz<BizStoreMapper, BizStore> {

    @Autowired
    private RedisUtil redisUtil;

    /**初始化库存
     */
    public ObjectRestResponse initStore(){

        BizStore store = new BizStore();
        store.setStatus(Boolean.TRUE);
        List<BizStore> bizStores = this.selectList(store);

        bizStores.forEach(bizStore -> {
            // 获取key
            String lockKey = redisUtil.getKey(bizStore.getSpecId(),bizStore.getTimeSlot());
            if(!this.hHasKey(lockKey)){
                setRedisStore(lockKey,bizStore.getIsLimit(),bizStore.getStoreNum());
            }

        });

        return ObjectRestResponse.ok();
    }

    /**更新团购商品为不限制库存
     */
    public ObjectRestResponse updateGroupStore(){

        List<String> specIds = this.mapper.queryGroupSpecId();

        specIds.forEach(specId -> {
            // 获取key
            String lockKey = redisUtil.getKey(specId,0);
            // 存在则覆盖，没有则添加
            setRedisStore(lockKey,Boolean.FALSE,0);

        });

        return ObjectRestResponse.ok();
    }


    /**
     * 修改库存重复的数据
     * @return
     */
    public ObjectRestResponse duplicateStoreHandle(){

        List<BizStore> duplicateStoreList = this.mapper.getDuplicateStore();

        duplicateStoreList.forEach(store -> {
            List<BizStore> storeList = this.mapper.getDuplicateStoreDetailList(store);
            if(storeList.size() > 1){
                BizStore newStore = storeList.get(0);
                BizStore oldStore = storeList.get(storeList.size()-1);
                newStore.setProductCode(oldStore.getProductCode());
                newStore.setTenantId(oldStore.getTenantId());
                newStore.setModifyBy(newStore.getCreateBy());
                newStore.setModifyTime(newStore.getCreateTime());
                newStore.setCreateBy(oldStore.getCreateBy());
                newStore.setCreateTime(oldStore.getCreateTime());
                this.updateSelectiveById(newStore);
                int num = this.mapper.updateDuplicateStore(newStore);
                log.info("规格ID:{},timeSlot:{},重复数据：{}条，删除重复数据：{}条",newStore.getSpecId(),newStore.getTimeSlot(),storeList.size(),num);
            }

        });

        return ObjectRestResponse.ok();
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

    // 设置redis库存信息
    private Boolean hHasKey(String lockKey){
        return redisUtil.hHasKey(RedisPreFixConstant.ACE_STORE_KEY,lockKey);
    }

}