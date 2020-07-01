package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizRegion;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultNoDelivery;
import com.github.wxiaoqi.security.jinmao.vo.nodelivery.out.ResultRegionList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省市区区域表
 * 
 * @author zxl
 * @Date 2018-12-18 15:19:48
 */
public interface BizRegionMapper extends CommonMapper<BizRegion> {

    /**
     * 获取所有区域
     * @return
     */
    List<ResultRegionList> getAllRegionList();

    /**
     * 根据ID获取当前商户不配送区域
     * @param id
     * @return
     */
    List<String> selectNoDeliveryRegion(@Param("id") String id);

    /**
     * 根据区域ID查询区域信息
     * @param id
     * @return
     */
    ResultRegionList selectRegionInfoById(@Param("id") String id);

    /**
     * 根据ID删除不配送信息
     * @param id
     * @return
     */
    int deleteNoDeliveryById(@Param("id") String id);

    /**
     * 查询不配送区域列表
     * @param tenantId
     * @return
     */
    List<ResultNoDelivery> selectNoDeliveryRegionList(@Param("tenantId") String tenantId);
}
