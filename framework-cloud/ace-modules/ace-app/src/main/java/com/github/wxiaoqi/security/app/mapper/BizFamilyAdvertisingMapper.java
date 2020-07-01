package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizFamilyAdvertising;
import com.github.wxiaoqi.security.app.vo.advertising.out.FamilyAdVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

import java.util.List;

/**
 * 家里人广告位
 * 
 * @author huangxl
 * @Date 2019-08-12 17:59:01
 */
public interface BizFamilyAdvertisingMapper extends CommonMapper<BizFamilyAdvertising> {

    /**
     * 查询app家里人广告位
     * @param projectId
     * @return
     */
    List<FamilyAdVo> selectAppFamilyAdList(String projectId);

    String getReserveName(String objectId);
}
