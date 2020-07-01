package com.github.wxiaoqi.security.api.vo.store.pc;

import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 库存修改前参数
 */
@Data
@ApiModel(description= "库存修改前参数")
public class BeforePCStore implements Serializable {

    private PCStore pcStore;
    private Integer operationType;
    private String lockKey;
}
