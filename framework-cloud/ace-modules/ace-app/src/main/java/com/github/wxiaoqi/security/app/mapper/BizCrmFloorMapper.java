package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmFloor;
import com.github.wxiaoqi.security.app.vo.city.out.FloorInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼层表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmFloorMapper extends CommonMapper<BizCrmFloor> {

	List<BizCrmFloor> getByIds(@Param("ids")List<String> floorIds);

	List<FloorInfoVo> getFloorInfoListByUnitId(@Param("unitId") String unitId,@Param("type") int type);
}
