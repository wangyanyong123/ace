package com.github.wxiaoqi.security.api.vo.store;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CacheStoreListQuery implements Serializable {
    // 标签ID
    private String specId;
    // 时间段
    private List<CacheStoreQuery> CacheStoreQueryList;
}
