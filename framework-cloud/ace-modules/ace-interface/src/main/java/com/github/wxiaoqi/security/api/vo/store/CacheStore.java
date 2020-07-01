package com.github.wxiaoqi.security.api.vo.store;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheStore implements Serializable {
    // 限制标识
    private Boolean isLimit;
    // 库存数据
    private Integer storeNum;
}
