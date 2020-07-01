package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BaseAppHousekeeperArea;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.BuildInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.HouseKeeper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管家管理楼栋表
 * 
 * @author zxl
 * @Date 2018-12-10 10:16:12
 */
public interface BaseAppHousekeeperAreaMapper extends CommonMapper<BaseAppHousekeeperArea> {

	List<HouseKeeper> getHouseKeeperList(@Param("tenantId") String tenantId, @Param("searchVal") String searchVal,
										 @Param("page") Integer page, @Param("limit") Integer limit);

	int getHouseKeeperCount(@Param("tenantId") String tenantId,@Param("searchVal") String searchVal);

	int delBuild(@Param("userId") String userId, @Param("builds") List<String> builds, @Param("muserId") String muserId, @Param("tenantId")String tenantId);

	List<BuildInfoVo> getAllBuilds(@Param("tenantId") String tenantId, @Param("page") Integer page, @Param("limit") Integer limit);

	int getAllBuildsNum(@Param("tenantId") String tenantId);

	List<BuildInfoVo> getBuildsByUserId(@Param("userId")String userId, @Param("tenantId") String tenantId, @Param("page") Integer page, @Param("limit") Integer limit);

	int getNum(@Param("builds")List<String> builds, @Param("tenantId")String tenantId);

	void deleteHouseKeeper(@Param("userId") String userId, @Param("muserId") String muserId, @Param("tenantId")String tenantId);


	List<String> getAllIsChooseBuilds(@Param("tenantId") String tenantId);

	List<BuildInfoVo> getGroupBuildsByUserId(@Param("userId")String userId, @Param("tenantId") String tenantId, @Param("page") Integer page, @Param("limit") Integer limit);

	List<BaseAppHousekeeperArea> getAreasByUserId(@Param("userId")String userId);

}
