package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizUserHouse;
import com.github.wxiaoqi.security.app.entity.BizUserProject;
import com.github.wxiaoqi.security.app.mapper.BizUserHouseMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserProjectMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.userproject.out.OtherHousesAndProjectDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userproject.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.List;

/**
 * 用户和项目关系表
 *
 * @author zxl
 * @Date 2018-11-22 15:22:31
 */
@Service
@Slf4j
public class BizUserProjectBiz extends BusinessBiz<BizUserProjectMapper,BizUserProject> {

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@Autowired
	private BizUserHouseMapper userHouseMapper;

	@Autowired
	private BizUserProjectMapper userProjectMapper;

	public ObjectRestResponse deleteProject(String projectId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(projectId)){
			response.setStatus(501);
			response.setMessage("projectId为空！");
			return response;
		}
		BizUserProject userProject = new BizUserProject();
		userProject.setUserId(BaseContextHandler.getUserID());
		userProject.setProjectId(projectId);
		userProject.setStatus("1");
		userProject = this.selectOne(userProject);
		if(StringUtils.isEmpty(userProject.getId())){
			response.setMessage("projectId错误！");
			response.setStatus(502);
			return response;
		}else {
				userProject.setStatus("0");
				userProject.setIsNow("0");
				userProject.setModifyTime(new Date());
				userProject.setModifyBy(BaseContextHandler.getUserID());
				this.updateSelectiveById(userProject);
		}
		setNowProjectByUserId(BaseContextHandler.getUserID());
		return ObjectRestResponse.ok("删除成功");
	}
	public void setNowProjectByUserId(String userId){
		BizUserProject project = new BizUserProject();
		project.setUserId(userId);
		project.setStatus("1");
		List<BizUserProject> userProjects = this.selectList(project);
		if(userProjects.size() < 1){
//			BaseAppClientUser appClientUser = clientUserBiz.selectById(userId);
//			appClientUser.setIsAuth("0");
//			appClientUser.setUpdTime(new Date());
//			clientUserBiz.updateSelectiveById(appClientUser);
		}else {
			if(!isHasNowProject(userId)){
				if(1 == userProjects.size()){
					userProjects.get(0).setIsNow("1");
					userProjects.get(0).setModifyTime(new Date());
					updateSelectiveById(userProjects.get(0));
				}else {
					setNowProject(userId);
				}
			}
		}
	}

	public boolean isHasNowProject(String userId){
		BizUserProject userProject = new BizUserProject();
		userProject.setUserId(userId);
		userProject.setIsNow("1");
		userProject.setStatus("1");
		return 0 < this.selectList(userProject).size();
	}

	public void setNowProject(String userId){
		userProjectMapper.setProjectIsNow(userId);
	}

	public ObjectRestResponse switchProject(String newProjectId,String autoSwitchHouse) {
		ObjectRestResponse response = new ObjectRestResponse();

		if (StringUtils.isEmpty(newProjectId)){
			response.setMessage("newProjectId不能为空！");
			response.setStatus(501);
			return response;
		}
		BizUserProject param = new BizUserProject();
		param.setUserId(BaseContextHandler.getUserID());
		param.setProjectId(newProjectId);
		param.setStatus("1");
		if (this.selectCount(param).intValue() < 1){
			response.setMessage("当前用户还没有关注要切换的社区！");
			response.setStatus(503);
			return response;
		}
		param.setId(null);
		param.setProjectId(null);
		param.setIsNow("1");
		BizUserProject bizUserProject = this.selectOne(param);
		String currentProjectId = null;
		if (null != bizUserProject && !StringUtils.isEmpty(bizUserProject.getProjectId())){
			if(newProjectId.equals(bizUserProject.getProjectId())){
				response.setMessage("当前社区id和要切换的社区id一致！");
				response.setStatus(502);
				return response;
			}else {
				currentProjectId = bizUserProject.getProjectId();
			}
		}else {
			response.setMessage("当前用户不在当前社区！");
			response.setStatus(504);
			return response;
		}
		BizUserHouse currentUserHouse = userHouseBiz.getCurrentHouseByProjectIdAndUserId(currentProjectId,BaseContextHandler.getUserID());
		if(null != currentUserHouse && "1".equals(currentUserHouse.getIsNow())){
			userHouseMapper.changOut(currentUserHouse.getHouseId(),BaseContextHandler.getUserID());
		}
		userProjectMapper.changOut(currentProjectId,BaseContextHandler.getUserID());
		userProjectMapper.changNow(newProjectId,BaseContextHandler.getUserID());
		if(null != autoSwitchHouse && "1".equals(autoSwitchHouse)){
			String houseId = userHouseMapper.getLastTimeOutHouseIdByProjectId(newProjectId,BaseContextHandler.getUserID());
			if(null != houseId && !StringUtils.isEmpty(houseId)){
				log.info("要切换的社区有房屋！*********************************************************");
				log.info("houseId："+houseId);
				log.info("userId："+BaseContextHandler.getUserID());
				userHouseMapper.changNow(houseId,BaseContextHandler.getUserID());
			}
		}
		return ObjectRestResponse.ok("切换成功！");
	}

	public ObjectRestResponse<ProjectInfoVo> getCurrentProject() {
		ObjectRestResponse<ProjectInfoVo> response = new ObjectRestResponse<>();
		BizUserProject userProject = new BizUserProject();
		userProject.setUserId(BaseContextHandler.getUserID());
		userProject.setIsNow("1");
		userProject.setStatus("1");
		userProject = this.selectOne(userProject);
		if(null == userProject || StringUtils.isEmpty(userProject.getId())){
			response.setMessage("该用户没有关注该社区！");
			response.setStatus(501);
			return response;
		}else {
			ProjectInfoVo userProjectVo = userProjectMapper.getCurrentProjectInfoById(userProject.getId());
			response.setData(userProjectVo);
		}
		return response;
	}

	public ObjectRestResponse<List<ProjectInfoVo>> getProjectLists() {
		return ObjectRestResponse.ok(userProjectMapper.getProjectListsByUserId(BaseContextHandler.getUserID()));
	}

	public BizUserProject getProjectByHouseIdAndUserId(String houseId, String userId) {
		return userProjectMapper.getProjectByHouseIdAndUserId(houseId,userId);
	}

	public ObjectRestResponse insertProject(String projectId) {
		ObjectRestResponse result = new ObjectRestResponse();
		if (StringUtils.isEmpty(projectId)){
			result.setMessage("社区id不能为空！");
			result.setStatus(501);
			return result;
		}
		BizUserProject param = new BizUserProject();
		param.setUserId(BaseContextHandler.getUserID());
		param.setProjectId(projectId);
		param.setStatus("1");
		if(this.selectCount(param).intValue()>0){
			result.setMessage("你已经添加过该社区了！");
			result.setStatus(502);
			return result;
		}
		userProjectMapper.changOutByUserId(BaseContextHandler.getUserID());
		BizUserHouse uHouse = new BizUserHouse();
		uHouse.setUserId(BaseContextHandler.getUserID());
		uHouse.setIsNow("1");
		uHouse.setStatus("1");
		uHouse.setIsDelete("0");
		uHouse = userHouseMapper.selectOne(uHouse);
		if(null != uHouse && !StringUtils.isEmpty(uHouse.getId())){
			userHouseMapper.changOut(uHouse.getHouseId(),BaseContextHandler.getUserID());
		}
		BizUserProject userProject = new BizUserProject();
		userProject.setId(UUIDUtils.generateUuid());
		userProject.setUserId(BaseContextHandler.getUserID());
		userProject.setProjectId(projectId);
		userProject.setIsNow("1");
		userProject.setStatus("1");
		userProject.setCreateBy(BaseContextHandler.getUserID());
		userProject.setCreateTime(new Date());
		this.insertSelective(userProject);
		result.setMessage("添加成功！");
		return result;
	}

	public CurrentUserInfosVo getCurrentUserInfos() {
		return userProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
	}

	public ObjectRestResponse<List<OtherHousesAndProjectDetailInfosVo>> getOtherHousesAndProjectDetailInfos() {
		ObjectRestResponse<List<OtherHousesAndProjectDetailInfosVo>> objectRestResponse = new ObjectRestResponse<>();
		objectRestResponse.setData(userProjectMapper.getOtherHousesAndProjectDetailInfos(BaseContextHandler.getUserID()));
		return objectRestResponse;
	}
}