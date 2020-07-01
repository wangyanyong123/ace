package com.github.wxiaoqi.security.api.vo.store;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheStoreProduct implements Serializable {
    // 规格ID
    private String specId;
    // 库存数据
    private CacheStore cacheStore;
}
