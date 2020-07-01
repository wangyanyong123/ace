package com.github.wxiaoqi.security.api.vo.store;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheStoreQuery implements Serializable {

    // 时间段
    private Integer timeSlot;

    private CacheStore cacheStore;
}
