package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizAdvertisingPosition;
import com.github.wxiaoqi.security.app.vo.advertising.out.AdvertisingInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优选商城广告位
 * 
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
public interface BizAdvertisingPositionMapper extends CommonMapper<BizAdvertisingPosition> {

    /**
     * 获取广告位信息
     * @return
     */
    List<AdvertisingInfo> selectAdvertList(@Param("projectIdList")List<String> projectIdList, @Param("position") Integer position);

    List<AdvertisingInfo>  findListOfAllProjectOptions(@Param("projectCount") int projectCount);

}
