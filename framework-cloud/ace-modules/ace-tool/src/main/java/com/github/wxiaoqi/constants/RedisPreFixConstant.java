package com.github.wxiaoqi.constants;

/**
 * Redis的key命名前缀
 */
public interface RedisPreFixConstant {

    String LOCK_PROFIX_KEY = "key:store:"; // 分布式锁前缀

    String CACHE_STORE_PROFIX_KEY = "seckill:store:"; // 库存缓存前缀

    String ACE_STORE_KEY = "ace:store"; // 缓存库存数

    String ACE_STORE_SALE = "ace:store:sale"; // 销售数量
}
