package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.jinmao.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.BuildInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.HouseKeeper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BaseAppHousekeeperArea;
import com.github.wxiaoqi.security.jinmao.mapper.BaseAppHousekeeperAreaMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 管家管理楼栋表
 *
 * @author zxl
 * @Date 2018-12-10 10:16:12
 */
@Service
@Slf4j
public class BaseAppHousekeeperAreaBiz extends BusinessBiz<BaseAppHousekeeperAreaMapper,BaseAppHousekeeperArea> {

	@Autowired
	private BaseAppHousekeeperAreaMapper housekeeperAreaMapper;

	@Autowired
	private BaseAppServerUserMapper appServerUserMapper;

	public List<HouseKeeper> getHouseKeeperList(String searchVal, Integer page, Integer limit) {
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
		List<HouseKeeper> houseKeeperList =  housekeeperAreaMapper.getHouseKeeperList(BaseContextHandler.getTenantID(),searchVal, startIndex, limit);
		return houseKeeperList;
	}

	public int getHouseKeeperCount(String searchVal) {
		int total = housekeeperAreaMapper.getHouseKeeperCount(BaseContextHandler.getTenantID(), searchVal);
		return total;
	}

	@Transactional
	public ObjectRestResponse delHouseKeeper(String id) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)){
			msg.setStatus(501);
			msg.setMessage("id不能为空!");
			return msg;
		}
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setId(id);
		appServerUser.setIsHousekeeper("0");
		appServerUser.setModifyTime(new Date());
		appServerUser.setModifyBy(BaseContextHandler.getUserID());
		if(appServerUserMapper.updateByPrimaryKeySelective(appServerUser) < 1){
			msg.setStatus(502);
			msg.setMessage("id错误!");
			return msg;
		}else{
			housekeeperAreaMapper.deleteHouseKeeper(id,BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
		}
		BaseAppServerUser serverUser = appServerUserMapper.selectByPrimaryKey(id);
		if(null != serverUser && serverUser.getIsBusiness().equals("0")
				&& serverUser.getIsCustomer().equals("0")
				&& serverUser.getIsService().equals("0")
				&& serverUser.getIsHousekeeper().equals("0")){
			serverUser.setId(id);
			serverUser.setStatus("0");
			serverUser.setModifyTime(new Date());
			serverUser.setModifyBy(BaseContextHandler.getUserID());
			appServerUserMapper.updateByPrimaryKeySelective(serverUser);
		}
		msg.setMessage("删除成功!");
		return msg;
	}

	public ObjectRestResponse delBuild(String userId,String buildIds) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(StringUtils.isEmpty(userId)){
			msg.setStatus(501);
			msg.setMessage("id不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(buildIds)){
			msg.setStatus(502);
			msg.setMessage("buildIds不能为空!");
			return msg;
		}else {
			if (buildIds.startsWith(",")) {
				buildIds = buildIds.substring(1);
			}
			if (buildIds.endsWith(",")) {
				buildIds = buildIds.substring(0,buildIds.length() - 1);
			}
			if(StringUtils.isEmpty(buildIds)){
				msg.setStatus(503);
				msg.setMessage("buildIds错误!");
				return msg;
			}
		}
		List<String> builds = Arrays.asList(buildIds.split(","));
		if(builds.size() > 0){
			if(housekeeperAreaMapper.delBuild(userId,builds,BaseContextHandler.getUserID(),BaseContextHandler.getTenantID()) < 1){
				msg.setStatus(505);
				msg.setMessage("userId错误!");
				return msg;
			}
		}else {
			msg.setStatus(504);
			msg.setMessage("buildIds错误!");
		}
		msg.setMessage("删除成功!");
		return msg;
	}

	@Transactional
	public ObjectRestResponse addBuild(String userId, String buildIds) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(StringUtils.isEmpty(userId)){
			msg.setStatus(501);
			msg.setMessage("id不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(buildIds)){
			msg.setStatus(503);
			msg.setMessage("buildIds不能为空!");
			return msg;
		}else {
			if (buildIds.startsWith(",")) {
				buildIds = buildIds.substring(1);
			}
			if (buildIds.endsWith(",")) {
				buildIds = buildIds.substring(0,buildIds.length() - 1);
			}
			if(StringUtils.isEmpty(buildIds)){
				msg.setStatus(502);
				msg.setMessage("buildIds错误!");
				return msg;
			}
		}
		List<String> builds = Arrays.asList(buildIds.split(","));
		if(builds.size() > 0){
			int num = housekeeperAreaMapper.getNum(builds,BaseContextHandler.getTenantID());
			if(num > 0){
				msg.setStatus(505);
				msg.setMessage("有楼栋已经被选择了，请重新选择!");
				return msg;
			}
			for (String buildId:builds) {
				BaseAppHousekeeperArea housekeeperArea = new BaseAppHousekeeperArea();
				housekeeperArea.setId(UUIDUtils.generateUuid());
				housekeeperArea.setUserId(userId);
				housekeeperArea.setStatus("1");
				housekeeperArea.setBuildingId(buildId);
				housekeeperArea.setTenantId(BaseContextHandler.getTenantID());
				housekeeperArea.setCreateBy(BaseContextHandler.getUserID());
				housekeeperArea.setCreateTime(new Date());
				if(housekeeperAreaMapper.insertSelective(housekeeperArea) < 1){
					throw new BusinessException("新增失败!");
				}
			}

		}else {
			msg.setStatus(504);
			msg.setMessage("buildIds错误!");
		}
		msg.setMessage("添加成功！");
		return msg;
	}

	public List<BuildInfoVo> getAllBuilds() {
//		if (page == null || page.equals("")) {
//			page = 1;
//		}
//		if (limit == null || limit.equals("")) {
//			limit = 10;
//		}
//		if(page == 0) {
//			page = 1;
//		}
//		//分页
//		Integer startIndex = (page - 1) * limit;
//		List<BuildInfoVo> buildInfoVos = housekeeperAreaMapper.getAllBuilds(BaseContextHandler.getTenantID(),page,limit);
//		int count = housekeeperAreaMapper.getAllBuildsNum(BaseContextHandler.getTenantID());
		List<BuildInfoVo> buildInfoVos = housekeeperAreaMapper.getAllBuilds(BaseContextHandler.getTenantID(),null,null);
		return buildInfoVos;
	}

	public List<BuildInfoVo> getBuildsByUserId(String userId) {
		List<BuildInfoVo> buildInfoVos = housekeeperAreaMapper.getBuildsByUserId(userId,BaseContextHandler.getTenantID(),null,null);
		return buildInfoVos;
	}

	public List<String> getAllIsChooseBuilds() {
		List<String> buildIds = housekeeperAreaMapper.getAllIsChooseBuilds(BaseContextHandler.getTenantID());
		return buildIds;
	}

	public List<BuildInfoVo> getGroupBuildsByUserId(String userId) {
		List<BuildInfoVo> buildInfoVos = housekeeperAreaMapper.getGroupBuildsByUserId(userId,BaseContextHandler.getTenantID(),null,null);
		return buildInfoVos;
	}
}