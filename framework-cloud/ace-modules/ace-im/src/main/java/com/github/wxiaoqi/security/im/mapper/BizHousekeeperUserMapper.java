package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.entity.BizHousekeeperUser;
import com.github.wxiaoqi.security.im.vo.housekeeperchat.out.*;
import com.github.wxiaoqi.security.im.vo.userchat.out.HouseKeeperVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管家和用户关系表
 * 
 * @author zxl
 * @Date 2018-12-18 15:03:50
 */
public interface BizHousekeeperUserMapper extends CommonMapper<BizHousekeeperUser> {

	List<UserInfoVo> getUserInfoList(@Param("userId") String userId, @Param("searchVal") String searchVal,
									 @Param("page") Integer page, @Param("limit") Integer limit);

	int getUserInfoNum(@Param("userId") String userId, @Param("searchVal") String searchVal);

	int isHasHouse(@Param("userId") String userId);

	HouseKeeperVo getHouseKeeperInfo(@Param("userId") String userId);

	List<BlockInfoVo> getBlockList(@Param("userId") String userId);

	List<BuildInfoVo> getBuildList(@Param("blockId") String blockId, @Param("userId") String userId);

	List<UnitInfoVo> getUnitList(@Param("buildId") String buildId);

	List<FloorInfoVo> getFloorList(@Param("unitId") String unitId);

	List<HouseInfoVo> getHouseList(@Param("floorId") String floorId);

	List<HouseholdVo> getHouseholdList(@Param("houseId") String houseId);
}
