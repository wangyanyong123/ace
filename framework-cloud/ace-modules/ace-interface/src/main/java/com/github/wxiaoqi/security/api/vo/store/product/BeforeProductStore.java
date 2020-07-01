package com.github.wxiaoqi.security.api.vo.store.product;

import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 库存修改前参数
 */
@Data
@ApiModel(description= "库存修改前参数")
public class BeforeProductStore implements Serializable {

    private ProductStore productStore;
    private Integer operationType;
    private CacheStore cacheStoreOld;
    private Integer currentNum;
    private String lockKey;
}
