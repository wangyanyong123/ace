package com.github.wxiaoqi.store.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.store.entity.BizStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存管理
 */
public interface BizStoreMapper extends CommonMapper<BizStore> {
    /**
     * 更新库存
     * @return
     */
    int updateStore(@Param("specId") String specId, @Param("currentNum") Integer currentNum, @Param("timeSlot") Integer timeSlot);

    /**
     * 获取库存信息
     * @param specId
     * @param timeSlot
     * @return
     */
    BizStore queryStoreNum(@Param("specId") String specId,@Param("timeSlot") Integer timeSlot);

    /**
     * 获取团购商品的规格ID
     * @return
     */
    List<String> queryGroupSpecId();

    /**
     * 获取存在重复库存的数据
     * @return
     */
    List<BizStore> getDuplicateStore();

    /**
     * 查询某一个重复库存进行修复
     * @param bizStore
     * @return
     */
    List<BizStore> getDuplicateStoreDetailList(@Param("query") BizStore bizStore);

    /**
     * 删除除新修复的数据外的其余重复数据
     * @param newStore
     * @return
     */
    int updateDuplicateStore(@Param("query") BizStore newStore);

}
