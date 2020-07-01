package com.github.wxiaoqi.code.lock;

import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:31 2019/1/28
 * @Modified By:
 */
@Component
@Slf4j
public class RedisLocker {

	private final static String LOCKER_PREFIX = "lock:";

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 加锁
	 * @return
	 */
	public boolean lock(String key,String value){
		if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){
			return true;
		}

		String currentValue = stringRedisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
			String oldValue =stringRedisTemplate.opsForValue().getAndSet(key,value);
			if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue) ){
				return true;
			}
		}
		return false;
	}


	/**
	 * 解锁
	 * @param key
	 * @param value
	 */
	public void unlock(String key,String value){
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value) ){
				stringRedisTemplate.opsForValue().getOperations().delete(key);//删除key
			}
		} catch (Exception e) {
			log.error("[Redis分布式锁] 解锁出现异常了，{}",e);
		}
	}
}
