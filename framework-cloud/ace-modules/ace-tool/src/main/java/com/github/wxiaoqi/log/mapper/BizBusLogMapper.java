package com.github.wxiaoqi.log.mapper;

import com.github.wxiaoqi.log.entity.BizBusLog;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-09 14:18:21
 */
public interface BizBusLogMapper extends CommonMapper<BizBusLog> {

	List<LogInfoVo> getBusLogs(@Param("busName")String busName, @Param("userName")String userName, @Param("account")String account, @Param("opt")String opt,@Param("uri")String uri,
							   @Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime,
							   @Param("page")Integer page, @Param("limit")Integer limit);

	int getBusLogsTotal(@Param("busName")String busName, @Param("userName")String userName, @Param("account")String account, @Param("opt")String opt,@Param("uri")String uri,
						@Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime);
}
