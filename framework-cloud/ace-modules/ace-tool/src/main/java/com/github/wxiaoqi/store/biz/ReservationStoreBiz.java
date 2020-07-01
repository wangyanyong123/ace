package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.constants.CommonConstant;
import com.github.wxiaoqi.constants.RedisPreFixConstant;
import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreListQuery;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreQuery;
import com.github.wxiaoqi.security.api.vo.store.reservation.BeforeReservationStore;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 库存管理
 */
@Slf4j
@Service
public class ReservationStoreBiz extends BusinessBiz<BizStoreMapper, BizStore> {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BizStoreLogBiz bizStoreLogBiz;

    @Value("${store.times}")
    private Integer storeTimes;


    /**
     * Redis预约服务出入库
     * @param reservationStore
     * @return
     */
    public ObjectRestResponse reservationStore(ReservationStore reservationStore, Integer operationType,Integer times){
        List<BeforeReservationStore> beforeReservationStores = new ArrayList<>();
        ObjectRestResponse objectRestResponse = this.repeatTimes(reservationStore,operationType,beforeReservationStores,times);
        if(objectRestResponse.success()){
            if(CollectionUtils.isNotEmpty(beforeReservationStores)){
                BeforeReservationStore beforeReservationStore = beforeReservationStores.get(0);
                objectRestResponse = this.updateRedisStore(beforeReservationStore);
            }
        }else {
            // 添加日志
            bizStoreLogBiz.addStoreLogCheck(reservationStore,operationType,objectRestResponse);
        }
        return objectRestResponse;
    }


    /**
     * redis出入库 批量
     * @param reservationStores
     * @return
     */
    public ObjectRestResponse reservationStoreBatch(List<ReservationStore> reservationStores, Integer operationType,Integer times){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<BeforeReservationStore> beforeReservationStores = new ArrayList<>();
        for (ReservationStore reservationStore : reservationStores){
            objectRestResponse = this.repeatTimes(reservationStore,operationType,beforeReservationStores,times);
            // 添加日志
            bizStoreLogBiz.addStoreLogCheck(reservationStore,operationType,objectRestResponse);
        }

        // 校验后处理
        ObjectRestResponse resultRestResponse = new ObjectRestResponse();
        if(beforeReservationStores.size() == reservationStores.size()){
            beforeReservationStores.forEach(beforeReservationStore -> this.updateRedisStore(beforeReservationStore));
        }else {
            resultRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.NOT_ENOUGH_STOCK);
            try {
                log.error("服务批量操作类型：{}-{}，操作结果：{}-{}，参数信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),resultRestResponse.success(),resultRestResponse.getMessage(),JacksonJsonUtil.beanToJson(reservationStores));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultRestResponse;
    }

    /**
     * 获取库存信息
     * @param specId
     * @return
     */
    public List<CacheStoreQuery> getCacheStore(String specId){
        List<CacheStoreQuery> cacheStoreQueryList = new ArrayList<>();
        for (int i=1;i<3;i++){
            String lockKey = redisUtil.getKey(specId,i);
            if(StringUtils.isNotEmpty(lockKey)){
                CacheStore cacheStoreOld = redisUtil.getRedisStore(lockKey);
                if(!ObjectUtils.isEmpty(cacheStoreOld)){
                    CacheStoreQuery cacheStoreQuery = new CacheStoreQuery();
                    cacheStoreQuery.setTimeSlot(i);
                    cacheStoreQuery.setCacheStore(cacheStoreOld);
                    cacheStoreQueryList.add(cacheStoreQuery);
                }
            }
        }
        return cacheStoreQueryList;
    }

    /**
     * 批量获取库存信息
     * @param specIds
     * @return
     */
    public List<CacheStoreListQuery> getCacheStoreBatch(List<String> specIds){
        List<CacheStoreListQuery> cacheStoreListQueryList = new ArrayList<>();
        specIds.forEach(specId ->{
            List<CacheStoreQuery> cacheStoreQueryList = this.getCacheStore(specId);
            if(cacheStoreQueryList.size() > 0){
                CacheStoreListQuery cacheStoreListQuery = new CacheStoreListQuery();
                cacheStoreListQuery.setSpecId(specId);
                cacheStoreListQuery.setCacheStoreQueryList(cacheStoreQueryList);
                cacheStoreListQueryList.add(cacheStoreListQuery);
            }
        });
        return cacheStoreListQueryList;
    }


    /**
     * 获取当日销售后库存信息
     * @param specId
     * @return
     */
    public List<CacheStoreQuery> getSaleStore(String specId){
        List<CacheStoreQuery> cacheStoreQueryList = new ArrayList<>();
        for (int i=1;i<3;i++){
            String lockKey = redisUtil.getKey(specId,i);
            if(StringUtils.isNotEmpty(lockKey)){
                CacheStore cacheStoreOld = redisUtil.getRedisStore(lockKey);
                if(!ObjectUtils.isEmpty(cacheStoreOld)){
                    CacheStoreQuery cacheStoreQuery = new CacheStoreQuery();
                    cacheStoreQuery.setTimeSlot(i);
                    cacheStoreQuery.setCacheStore(cacheStoreOld);
                    cacheStoreQueryList.add(cacheStoreQuery);
                }
            }
        }
        return cacheStoreQueryList;
    }

    /**
     * 批量获取当日销售后库存信息
     * @param specIds
     * @return
     */
    public List<CacheStoreListQuery> getSaleStoreBatch(List<String> specIds){
        List<CacheStoreListQuery> cacheStoreListQueryList = new ArrayList<>();
        specIds.forEach(specId ->{
            List<CacheStoreQuery> cacheStoreQueryList = this.getSaleStore(specId);
            if(cacheStoreQueryList.size() > 0){
                CacheStoreListQuery cacheStoreListQuery = new CacheStoreListQuery();
                cacheStoreListQuery.setSpecId(specId);
                cacheStoreListQuery.setCacheStoreQueryList(cacheStoreQueryList);
                cacheStoreListQueryList.add(cacheStoreListQuery);
            }
        });
        return cacheStoreListQueryList;
    }


    // 重试更新库存
    private ObjectRestResponse repeatTimes(ReservationStore reservationStore,Integer operationType,List<BeforeReservationStore> beforeReservationStores,Integer times){
        ObjectRestResponse objectRestResponse = this.setRedisLock(reservationStore,operationType,beforeReservationStores);
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
                    this.repeatTimes(reservationStore,operationType,beforeReservationStores,times);
                    log.error("服务：{}：锁设置失败，重试次数：{}",redisUtil.getKey(reservationStore.getSpecId(),reservationStore.getTimeSlot()),times);
                }
            }
        }
        return objectRestResponse;
    }

    // 库存更新前参数校验
    private ObjectRestResponse setRedisLock(ReservationStore reservationStore,Integer operationType,List<BeforeReservationStore> beforeReservationStores){
        String lockKey = redisUtil.getKey(reservationStore.getSpecId(),reservationStore.getTimeSlot());
        String saleKey = redisUtil.getKey(reservationStore.getReservationTime(),reservationStore.getSpecId(),reservationStore.getTimeSlot());
        //设置锁
        if(redisUtil.setLock(lockKey, CommonConstant.SET_LOCK_START_INDEX)){
            // 获取redis缓存
            CacheStore cacheStoreOld = redisUtil.getRedisStore(lockKey);
            if(ObjectUtils.isEmpty(cacheStoreOld)){
                redisUtil.release(lockKey);
                log.error("服务操作类型：{}-{}，redis出入库时未找到库存信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),reservationStore);
                return redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_NOT_EXISTS);
            }

            Integer sales = Integer.parseInt(getSale(saleKey,reservationStore.getReservationTime()));
            Integer currentNum = sales + reservationStore.getAccessNum();
            if(cacheStoreOld.getIsLimit()){
                if((currentNum + cacheStoreOld.getStoreNum()) < 0){
                    redisUtil.release(lockKey);
                    log.error("服务批量操作类型：{}-{}，redis库存数量不足：当前库存信息{}，更新库存信息：{}",operationType,AceDictionary.STORE_OPERATION_TYPE.get(operationType),cacheStoreOld,reservationStore);
                    return redisUtil.errorRestResponse(ResponseCodeEnum.NOT_ENOUGH_STOCK);
                }
            }

            // 封装校验通过的数据
            BeforeReservationStore beforeReservationStore = new BeforeReservationStore();
            beforeReservationStore.setCacheStoreOld(cacheStoreOld);
            beforeReservationStore.setCurrentNum(currentNum);
            beforeReservationStore.setLockKey(lockKey);
            beforeReservationStore.setOperationType(operationType);
            beforeReservationStore.setReservationStore(reservationStore);
            beforeReservationStore.setSaleKey(saleKey);
            beforeReservationStores.add(beforeReservationStore);
            return ObjectRestResponse.ok();
        }
        return redisUtil.errorRestResponse(ResponseCodeEnum.REdiS_LOCK_SET_FAIL);
    }

    // redis库存更新操作
    private ObjectRestResponse updateRedisStore(BeforeReservationStore beforeReservationStore){

        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        if(!setRedisStore(beforeReservationStore.getSaleKey(),beforeReservationStore.getCurrentNum(),beforeReservationStore.getReservationStore().getReservationTime())){
            objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.REDIS_STORE_UPDATE_FAIL);
            log.error("服务操作类型：{}-{}，结果：{}-{}，参数信息：{}",beforeReservationStore.getOperationType(),AceDictionary.STORE_OPERATION_TYPE.get(beforeReservationStore.getOperationType()),objectRestResponse.success(),objectRestResponse.getMessage(),beforeReservationStore.getReservationStore());

        }
        bizStoreLogBiz.addStoreLogUpdate(beforeReservationStore.getReservationStore(),beforeReservationStore.getCacheStoreOld().getStoreNum(),beforeReservationStore.getCurrentNum(),beforeReservationStore.getCacheStoreOld().getIsLimit(),beforeReservationStore.getOperationType(),objectRestResponse);
        // 释放锁
        redisUtil.release(beforeReservationStore.getLockKey());
        return objectRestResponse;
    }

    // 设置redis库存信息
    private Boolean setRedisStore(String saleKey,Integer saleNum,Date reservationTime){
        return redisUtil.set(getSaleKey(saleKey),saleNum.toString(),getTime(reservationTime));
    }

    // 获取redis库存信息
    private String getSale(String saleKey,Date reservationTime){
        Object saleObject = redisUtil.get(getSaleKey(saleKey));
        String saleNum = "0";
        // 判断库存信息是否存
        if(!ObjectUtils.isEmpty(saleObject)){
            saleNum = (String) saleObject;
        }else{
            redisUtil.set(getSaleKey(saleKey),saleNum,getTime(reservationTime));
        }
        return saleNum;
    }

    private String getSaleKey(String saleKey){
       return RedisPreFixConstant.ACE_STORE_SALE+":"+saleKey;
    }


    /**
     * 获取当前时间距离预约服务时间凌晨多少秒
     * @param reservationTime
     * @return
     */
    private static long getTime(Date reservationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservationTime);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
        Long timeout = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return timeout;
    }
}