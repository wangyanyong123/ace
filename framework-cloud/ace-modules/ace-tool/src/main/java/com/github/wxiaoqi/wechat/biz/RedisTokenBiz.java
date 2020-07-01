package com.github.wxiaoqi.wechat.biz;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.gexin.fastjson.JSON;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: guohao
 * @create: 2020-04-12 12:12
 **/

@Slf4j
@Component
public class RedisTokenBiz implements CacheStorager<Token> {

    @Autowired
    private RedisTemplate redisTemplate;

    public Token lookup(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if (log.isInfoEnabled()) {
            log.info("lookup key:{}, value:{}", key, value);
        }
        if (StringUtils.isNotEmpty(value)) {
            JSONObject jsonObject = JSONObject.parseObject(value);
            String accessToken = jsonObject.getString("accessToken");
            Long createTime = jsonObject.getLong("createTime");
            Long expires = jsonObject.getLong("expires");
            Token token = new Token(accessToken, createTime, expires);
            return token;
        }
        return null;
    }

    public void caching(String key, Token cache) {

        if (log.isInfoEnabled()) {
            log.info("caching: key:{} , value:{}", key, JSON.toJSONString(cache));
        }
        String value = JSON.toJSONString(cache);
        if (cache.getExpires() > 0L) {
            redisTemplate.opsForValue().set(key, value, (cache.getExpires() - 60000L) / 1000, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.boundSetOps("weixin4j_cache_keys").add(new String[]{key});

    }

    public Token evict(String key) {
        Token cache = this.lookup(key);
        redisTemplate.delete(key);
        return cache;
    }

    public void clear() {
        Set weixin4j_cache_keys = redisTemplate.opsForSet().members("weixin4j_cache_keys");
        if (!weixin4j_cache_keys.isEmpty()) {
            redisTemplate.delete(weixin4j_cache_keys);
        }
    }


}
