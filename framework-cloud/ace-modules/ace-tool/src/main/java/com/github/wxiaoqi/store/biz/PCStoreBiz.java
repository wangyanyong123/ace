package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.constants.CommonConstant;
import com.github.wxiaoqi.constants.RedisPreFixConstant;
import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.pc.BeforePCStore;
import com.github.wxiaoqi.security.api.vo.store.pc.PCStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.store.entity.BizStore;
import com.github.wxiaoqi.store.mapper.BizStoreMapper;
import com.github.wxiaoqi.store.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 库存管理
 */
@Slf4j
@Service
public class PCStoreBiz extends BusinessBiz<BizStoreMapper, BizStore> {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BizStoreLogBiz bizStoreLogBiz;

    @Value("${store.times}")
    private Integer storeTimes;




    public ObjectRestResponse updateStore(List<PCStore> pcStores,Integer operationType,Integer times){
        List<BeforePCStore> beforePCStores = new ArrayList<>();
        for (PCStore pcStore : pcStores){
            ObjectRestResponse objectRestResponse = this.repeatTimes(pcStore,operationType,beforePCStores,times);
            // 添加日志
            bizStoreLogBiz.addStoreLogCheck(pcStore,operationType,objectRestResponse);
        }

        // 校验后处理
        ObjectRestResponse resultRestResponse = new ObjectRestResponse();
        if(beforePCStores.size() == pcStores.size()){
            beforePCStores.forEach(beforePCStore -> this.updateStore(beforePCStore));
        }else {
            resultRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_UPDATE_FAIL);
            try {
                log.error("PC批量操作类型：{}-{}，操作结果：{}-{}，参数信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),resultRestResponse.success(),resultRestResponse.getMessage(),JacksonJsonUtil.beanToJson(pcStores));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultRestResponse;
    }


    // 重试更新库存
    private ObjectRestResponse repeatTimes(PCStore pcStore,Integer operationType,List<BeforePCStore> beforePCStores,Integer times){
        ObjectRestResponse objectRestResponse = this.setRedisLock(pcStore,operationType,beforePCStores);
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
                    this.repeatTimes(pcStore,operationType,beforePCStores,times);
                    log.error("PC端：{}：锁设置失败，重试次数：{}",redisUtil.getKey(pcStore.getSpecId(),pcStore.getTimeSlot()),times);
                }
            }
        }
        return objectRestResponse;
    }

    private ObjectRestResponse setRedisLock(PCStore pcStore, Integer operationType, List<BeforePCStore> beforePCStores){
        String lockKey = redisUtil.getKey(pcStore.getSpecId(),pcStore.getTimeSlot());
        //设置锁
        if(redisUtil.setLock(lockKey, CommonConstant.SET_LOCK_START_INDEX)){
            // 校验库存是否充足
            CacheStore cacheStoreOld =  redisUtil.getRedisStore(lockKey);
            if(!ObjectUtils.isEmpty(cacheStoreOld)){
                if(cacheStoreOld.getIsLimit()){
                    if((cacheStoreOld.getStoreNum() + pcStore.getStoreNum()) < 0){
                        redisUtil.release(lockKey);
                        log.error("商品批量操作类型：{}-{}，redis库存数量不足：当前库存信息{}，更新库存数量：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),cacheStoreOld,pcStore.getStoreNum());
                        return redisUtil.errorRestResponse(ResponseCodeEnum.NOT_ENOUGH_STOCK);
                    }
                }
            }
            // 封装校验通过的数据
            BeforePCStore beforePCStore = new BeforePCStore();
            beforePCStore.setLockKey(lockKey);
            beforePCStore.setOperationType(operationType);
            beforePCStore.setPcStore(pcStore);
            beforePCStores.add(beforePCStore);
            return ObjectRestResponse.ok();
        }
        return redisUtil.errorRestResponse(ResponseCodeEnum.REdiS_LOCK_SET_FAIL);
    }

    // redis库存更新操作
    private ObjectRestResponse updateStore(BeforePCStore beforePCStore){

        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        if(setRedisStore(beforePCStore.getLockKey(),beforePCStore.getPcStore())){
            log.info("PC库存更新操作，redis库数更新成功：{}",beforePCStore.getPcStore());
            // 修改库存
            this.updateMysqlStore(beforePCStore.getPcStore());
        }else {
            objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_UPDATE_FAIL);
        }
        log.info("PC操作类型，redis更新：{}-{}，结果：{}-{}，参数信息：{}",beforePCStore.getOperationType(),AceDictionary.STORE_OPERATION_TYPE.get(beforePCStore.getOperationType()),objectRestResponse.success(),objectRestResponse.getMessage(),beforePCStore.getPcStore());
        bizStoreLogBiz.addStoreLogUpdate(beforePCStore.getPcStore(),CommonConstant.DEFAULT_STORE_NUM,beforePCStore.getPcStore().getStoreNum(),beforePCStore.getPcStore().getIsLimit(),beforePCStore.getOperationType(),objectRestResponse);
        // 释放锁
        redisUtil.release(beforePCStore.getLockKey());
        return objectRestResponse;


}

    // 设置redis库存信息
    private Boolean setRedisStore(String lockKey, PCStore pcStore){
        CacheStore cacheStore =  redisUtil.getRedisStore(lockKey);
        if(ObjectUtils.isEmpty(cacheStore)){
            cacheStore = new CacheStore();
            cacheStore.setIsLimit(pcStore.getIsLimit());
            cacheStore.setStoreNum(pcStore.getStoreNum());
        }else {
            cacheStore.setIsLimit(pcStore.getIsLimit());
            if(pcStore.getIsLimit()){
                // 限制库存的处理
                if(1 == pcStore.getProductType()){
                    // 商品库存处理
                    cacheStore.setStoreNum(pcStore.getStoreNum()+cacheStore.getStoreNum());
                }else {
                    // 服务库存处理
                    cacheStore.setStoreNum(pcStore.getStoreNum());
                }
            }else {
                // 无限制库存的处理
                cacheStore.setStoreNum(0);
            }

        }
        try {
            return redisUtil.hset(RedisPreFixConstant.ACE_STORE_KEY,lockKey, JacksonJsonUtil.beanToJson(cacheStore));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新MySQL数据库库存信息
    private void updateMysqlStore(PCStore pcStore){
        BizStore bizStore = new BizStore();
        bizStore.setProductId(pcStore.getProductId());
        bizStore.setSpecId(pcStore.getSpecId());
        bizStore.setTimeSlot(pcStore.getTimeSlot());
        bizStore.setStatus(Boolean.TRUE);
        bizStore = this.selectOne(bizStore);
        if(ObjectUtils.isEmpty(bizStore)){
            bizStore = new BizStore();
            BeanUtils.copyProperties(pcStore,bizStore);
            bizStore.setId(UUIDUtils.generateUuid());
            bizStore.setCreateTime(new Date());
            bizStore.setCreateBy(pcStore.getCreateBy());
            this.insertSelective(bizStore);
        }else {
            bizStore.setIsLimit(pcStore.getIsLimit());
            if(pcStore.getIsLimit()){
                // 限制库存的处理
                if(1 == pcStore.getProductType()){
                    // 商品库存处理
                    bizStore.setStoreNum(pcStore.getStoreNum() + bizStore.getStoreNum());
                }else {
                    // 服务库存处理
                    bizStore.setStoreNum(pcStore.getStoreNum());
                }
            }else {
                // 无限制库存的处理
                bizStore.setStoreNum(0);
            }
            bizStore.setModifyBy(pcStore.getCreateBy());
            bizStore.setModifyTime(new Date());
            this.updateSelectiveById(bizStore);
        }

    }
}