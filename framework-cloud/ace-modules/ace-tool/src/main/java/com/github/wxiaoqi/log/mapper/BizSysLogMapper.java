package com.github.wxiaoqi.log.mapper;

import com.github.wxiaoqi.log.entity.BizSysLog;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-09 16:40:10
 */
public interface BizSysLogMapper extends CommonMapper<BizSysLog> {

	List<LogInfoVo> getSysLogs(@Param("logName") String logName, @Param("userName")String userName, @Param("account")String account, @Param("type")String type,
							   @Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime,
							   @Param("page")Integer page, @Param("limit")Integer limit);

	int getSysLogsTotal(@Param("logName") String logName, @Param("userName")String userName, @Param("account")String account, @Param("type")String type,
						@Param("ip")String ip, @Param("startTime")String startTime, @Param("endTime")String endTime);
}
