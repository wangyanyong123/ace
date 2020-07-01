package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizCrmHouse;
import com.github.wxiaoqi.security.app.entity.BizPlanWoEq;
import com.github.wxiaoqi.security.app.vo.city.out.HouseInfoVo;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.house.HouseTreeList;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房屋表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
public interface BizCrmHouseMapper extends CommonMapper<BizCrmHouse> {

	List<BizCrmHouse> getByIds(@Param("ids")List<String> houseIds);

	List<HouseInfoVo> getHouseInfoListByFloorId(@Param("floorId")String floorId,@Param("type") int type);

	HouseInfoVO getHouseInfoVoByHouseId(@Param("houseId") String houseId);

	List<HouseTreeList> getHouseInfoTree(@Param("projectId") String projectId,@Param("type") int type);

	List<HouseInfoVo> getHouseInfoListByFloorIds(@Param("ids")List<String> ids,@Param("type") int type);

	HouseAllInfoVo getHouseAllInfoVoByHouseId(@Param("houseId") String houseId);

	BizCrmHouse getHouseByCode(@Param("houseCode")String houseCode);

    BizCrmHouse getRegionInfo(String queryId);

	BizPlanWoEq getEqInfo(String queryId);
}
