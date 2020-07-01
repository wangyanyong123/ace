package com.github.wxiaoqi.store.redis;

import com.github.wxiaoqi.constants.CommonConstant;
import com.github.wxiaoqi.constants.RedisPreFixConstant;
import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${store.lock.expire}")
    private String expire;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param by 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param by 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */

    public long hdel(String key, Object... item) {
        long offset = redisTemplate.opsForHash().delete(key, item);
        return offset;
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增或递减 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param num 大于0递增,小于0递减
     * @return
     */
    public boolean hincr(String key, String item, long num) {
        try {
            long backNum = redisTemplate.opsForHash().increment(key, item, num);
            System.out.printf("backNum:"+backNum);
            if (backNum == num) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置锁
     * @param productId
     * @param index
     * @return
     */
    public Boolean setLock(String productId,int index) {
        String lockKey = RedisPreFixConstant.LOCK_PROFIX_KEY + productId;
        log.info("set lock key:{}",lockKey);
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                redisTemplate.opsForValue().setIfAbsent(lockKey, productId);
                redisTemplate.expire(lockKey, Long.parseLong(expire), TimeUnit.MILLISECONDS);
                exec = operations.exec();
                if(exec.size() > 0) {
                    return (Boolean) exec.get(0);
                }
                return false;
            }
        };
        boolean flag = redisTemplate.execute(sessionCallback);
        if (!flag && index < CommonConstant.SET_LOCK_TIMES) {
            this.setLock(productId,++index);
        }
        return flag;
    }

    /**
     * 释放锁
     * @param productId
     * @return
     */
    public Boolean release(String productId) {
        String lockKey = RedisPreFixConstant.LOCK_PROFIX_KEY + productId;
        log.info("delete lock key:{}",lockKey);
        return redisTemplate.opsForValue().getOperations().delete(lockKey);
    }


    // 获取key
    public String getKey(String specId,Integer timeSlot){
        return this.getKey(null,specId,timeSlot);
    }

    /**
     * 获取key
     * @param reservationTime 预约服务时间
     * @param specId
     * @param timeSlot
     * @return
     */
    public String getKey(Date reservationTime, String specId, Integer timeSlot){
        if(!ObjectUtils.isEmpty(timeSlot) && timeSlot > 0){
            String key = specId+"_"+timeSlot;
            if(!ObjectUtils.isEmpty(reservationTime)){
                key = DateUtils.formatDateTime(reservationTime,DateUtils.COMPACT_DATE_FORMAT)+"_"+specId+"_"+timeSlot;
            }
            return key;
        }
        return specId;
    }

    // 获取redis库存信息
    public CacheStore getRedisStore(String lockKey){
        Object cacheStoreObject = this.hget(RedisPreFixConstant.ACE_STORE_KEY,lockKey);
        CacheStore cacheStoreOld = null;
        // 判断库存信息是否存在
        if(!ObjectUtils.isEmpty(cacheStoreObject)){
            String cacheStoreJson = (String) cacheStoreObject;
            try {
                cacheStoreOld = JacksonJsonUtil.jsonToBean(cacheStoreJson,CacheStore.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cacheStoreOld;
    }

    // 返回错误信息
    public ObjectRestResponse errorRestResponse(ResponseCodeEnum responseCodeEnum){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setStatus(responseCodeEnum.getKey());
        objectRestResponse.setMessage(responseCodeEnum.getValue());
        return objectRestResponse;
    }
}
