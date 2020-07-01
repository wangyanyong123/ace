package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserHouse;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.user.UserInfo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.OtherHouseDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserInfoVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserHouseVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和房屋关系表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BizUserHouseMapper extends CommonMapper<BizUserHouse> {

	int setOwnerHouseIsNow(@Param("userId") String userId);

	int setHouseIsNow(@Param("userId") String userId);

	List<UserHouseVo> getHouseListsByUserId(@Param("userId") String userId);

	UserHouseVo getCurrentHouseInfoById(@Param("id") String id);

	List<UserHouseVo> selectUserAllHouse(@Param("userId") String userId,@Param("projectId") String projectId);

	UserInfoVo getOwnInfoById(@Param("id") String id);

	List<UserInfoVo> getOtherInfosByHouseId(@Param("houseId") String houseId,@Param("userId") String userId);

	List<OtherHouseDetailInfosVo> getOthenHousesInfoByUserId(@Param("userId") String userId);

	List<UserInfoVo> getAllUserInfosByHouseId(@Param("houseId") String houseId);

	UserInfoVo getOwnInfoByHouseId(@Param("houseId") String houseId,@Param("userId") String userId);

	int changOut(@Param("houseId")String houseId, @Param("userId")String userId);

	int changNow(@Param("houseId")String houseId, @Param("userId")String userId);

	BizUserHouse getCurrentHouseByProjectIdAndUserId(@Param("projectId") String projectId, @Param("userId") String userId);

	List<UserInfo> getUserInfoByHouseId(@Param("houseId")String houseId, @Param("searchVal") String searchVal,
										@Param("page") Integer page, @Param("limit") Integer limit);

	List<HouseInfoVO> getHouseInfosByUserId(@Param("userId") String userId);

	String getLastTimeOutHouseIdByProjectId(@Param("projectId") String projectId, @Param("userId") String userId);

	List<UserInfo> getUserInfoByHouseIdWeb(@Param("houseId")String houseId, @Param("searchVal") String searchVal,
										   @Param("page") Integer page, @Param("limit") Integer limit);

	String getUnitIdById(@Param("houseId") String houseId);

	BizUserHouse selectNow(@Param("userId") String userId);
}
