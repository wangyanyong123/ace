package com.github.wxiaoqi.log.mapper;

import com.github.wxiaoqi.log.entity.BizLoginLog;
import com.github.wxiaoqi.log.vo.UserInfoVo;
import com.github.wxiaoqi.log.vo.liveness.UserLiveness;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-08 15:50:07
 */
public interface BizLoginLogMapper extends CommonMapper<BizLoginLog> {

	UserInfoVo getUserInfoById(String userId);

	List<LogInfoVo> getLoginLogs(@Param("logName") String logName, @Param("userName")String userName, @Param("account")String account, @Param("userType")String userType,
								 @Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime,
								 @Param("page")Integer page, @Param("limit")Integer limit);

	int getLoginLogsTotal(@Param("logName") String logName, @Param("userName")String userName, @Param("account")String account,
						  @Param("userType")String userType, @Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime);

    List<UserLiveness> getCertifiedUser(@Param("projectId") String projectId,@Param("startTime") String startTime,@Param("endTime") String endTime);

//	UserLiveness getCertifiedUser(@Param("projectId") String projectId,@Param("startTime") String startTime,@Param("endTime") String endTime);


	List<UserLiveness> getLivenessByYear(@Param("projectId") String projectId, @Param("startTime") String startTime,@Param("endTime") String endTime);

	List<UserLiveness> getLivenessBySeason(@Param("projectId") String projectId, @Param("startTime") String startTime,@Param("endTime") String endTime);

	List<UserLiveness> getLivenessByMouth(@Param("projectId") String projectId,@Param("startTime") String startTime,@Param("endTime") String endTime);

	List<UserLiveness> getLivenessByWeek(@Param("projectId") String projectId,@Param("startTime") String startTime,@Param("endTime") String endTime);

	List<UserLiveness> getLivenessByDay(@Param("projectId") String projectId,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
