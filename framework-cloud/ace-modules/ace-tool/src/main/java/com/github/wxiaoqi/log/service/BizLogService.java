package com.github.wxiaoqi.log.service;

import com.github.wxiaoqi.log.entity.BizBusLog;
import com.github.wxiaoqi.log.entity.BizLoginLog;
import com.github.wxiaoqi.log.entity.BizSysLog;
import com.github.wxiaoqi.log.mapper.BizBusLogMapper;
import com.github.wxiaoqi.log.mapper.BizLoginLogMapper;
import com.github.wxiaoqi.log.mapper.BizSysLogMapper;
import com.github.wxiaoqi.log.vo.UserInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:31 2019/4/8
 * @Modified By:
 */
@Service
public class BizLogService {
	@Autowired
	private BizLoginLogMapper loginLogMapper;
	@Autowired
	private BizBusLogMapper busLogMapper;
	@Autowired
	private BizSysLogMapper sysLogMapper;

	public void savelog(LogInfoVo logInfoVo) {
		if(logInfoVo != null){
			String id = UUIDUtils.generateUuid();
			if("1".equals(logInfoVo.getLogType())){
				BizLoginLog loginLog = new BizLoginLog();
				BeanUtils.copyProperties(logInfoVo,loginLog);
				loginLog.setId(id);
				loginLogMapper.insertSelective(loginLog);
			}else if("2".equals(logInfoVo.getLogType())){
				BizSysLog sysLog = new BizSysLog();
				BeanUtils.copyProperties(logInfoVo,sysLog);
				sysLog.setId(id);
				sysLogMapper.insertSelective(sysLog);
			}else if("3".equals(logInfoVo.getLogType())){
				BizBusLog busLog = new BizBusLog();
				BeanUtils.copyProperties(logInfoVo,busLog);
				busLog.setId(id);
				busLogMapper.insertSelective(busLog);
			}
		}
	}

	public ObjectRestResponse<UserInfoVo> getUserInfoById(String userId) {
		ObjectRestResponse<UserInfoVo> response = new ObjectRestResponse<>();
		response.setData(loginLogMapper.getUserInfoById(userId));
		return response;
	}

	public TableResultResponse<LogInfoVo> getLoginLogs(String logName, String userName, String account, String userType, String ip, String startTime, String endTime, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
		List<LogInfoVo> logInfoVos = loginLogMapper.getLoginLogs(logName,userName,account,userType,ip,startTime,endTime,startIndex,limit);
		int total = loginLogMapper.getLoginLogsTotal(logName,userName,account,userType,ip,startTime,endTime);
		return new TableResultResponse<LogInfoVo>(total,logInfoVos);
	}

	public TableResultResponse<LogInfoVo> getBusLogs( String busName, String userName, String account, String opt, String uri, String ip, String startTime, String endTime, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
		List<LogInfoVo> logInfoVos = busLogMapper.getBusLogs(busName,userName,account,opt,uri,ip,startTime,endTime,startIndex,limit);
		int total = busLogMapper.getBusLogsTotal(busName,userName,account,opt,uri,ip,startTime,endTime);
		return new TableResultResponse<LogInfoVo>(total,logInfoVos);
	}

	public TableResultResponse<LogInfoVo> getSysLogs(String logName, String userName, String account, String type, String ip, String startTime, String endTime, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
		List<LogInfoVo> logInfoVos = sysLogMapper.getSysLogs(logName,userName,account,type,ip,startTime,endTime,startIndex,limit);
		int total = sysLogMapper.getSysLogsTotal(logName,userName,account,type,ip,startTime,endTime);
		return new TableResultResponse<LogInfoVo>(total,logInfoVos);
	}

	public ObjectRestResponse getBusLogDetial(String id) {
		return ObjectRestResponse.ok(busLogMapper.selectByPrimaryKey(id).getParams());
	}

	public ObjectRestResponse getSysLogDetail(String id) {
		return ObjectRestResponse.ok(sysLogMapper.selectByPrimaryKey(id).getMessage());
	}
}
