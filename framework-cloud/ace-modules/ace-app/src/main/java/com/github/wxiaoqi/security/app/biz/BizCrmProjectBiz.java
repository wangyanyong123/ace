package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.api.vo.face.LandInfoVo;
import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoTwoVo;
import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoVo;
import com.github.wxiaoqi.security.api.vo.face.UnitInfoVo;
import com.github.wxiaoqi.security.app.entity.BizCrmProject;
import com.github.wxiaoqi.security.app.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 项目表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmProjectBiz extends BusinessBiz<BizCrmProjectMapper,BizCrmProject> {

	@Autowired
	private BizCrmProjectMapper crmProjectMapper;

	@Autowired
	private BizCrmUnitMapper crmUnitMapper;

	public void updateProject(List<BizCrmProject> projectList) {
		if(null != projectList && projectList.size() > 0){
			List<String> projectIds = projectList.stream().map(bizCrmProject -> bizCrmProject.getProjectId()).distinct().collect(toList());
			List<String> needUpdateProjectIds = new ArrayList<>();
			List<BizCrmProject> updateProjectList = new ArrayList<>();
			List<BizCrmProject> addProjectList = new ArrayList<>();
			List<BizCrmProject> crmProjectList = crmProjectMapper.getByIds(projectIds);
			if(null != crmProjectList && crmProjectList.size() > 0){
				List<String> oldProjectIds = crmProjectList.stream().map(bizCrmProject -> bizCrmProject.getProjectId()).distinct().collect(toList());
				needUpdateProjectIds = projectIds.stream().filter(item -> oldProjectIds.contains(item)).collect(toList());
			}

			if(null != needUpdateProjectIds && needUpdateProjectIds.size() > 0){
				Iterator<BizCrmProject> it = projectList.iterator();
				while(it.hasNext()){
					BizCrmProject x = it.next();
					if(needUpdateProjectIds.contains(x.getProjectId())){
						updateProjectList.add(x);
						it.remove();
					}else {
						addProjectList.add(x);
					}
				}
			}else {
				addProjectList = projectList;
			}
			if(null != updateProjectList && updateProjectList.size() > 0){
				log.info("批量更新项目信息"+updateProjectList.size()+"条");
				int num = 0;
				for (BizCrmProject updateProject:updateProjectList) {
					updateProject.setModifyTime(new Date());
					if(crmProjectMapper.updateByPrimaryKeySelective(updateProject)>0){
						num++;
					}
				}
				log.info("成功更新项目信息"+num+"条");
			}
			if(null != addProjectList && addProjectList.size() > 0){
				log.info("批量插入项目信息"+updateProjectList.size()+"条");
				int num = 0;
				for (BizCrmProject addProject:addProjectList) {
					addProject.setCreateTime(new Date());
					addProject.setStatus("1");
					if(crmProjectMapper.insertSelective(addProject)>0){
						num++;
					}
				}
				log.info("成功插入项目信息"+num+"条");
			}
		}
	}

	public ObjectRestResponse<List<ProjectInfoVo>> getProjectList(String cityId) {
		ObjectRestResponse<List<ProjectInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(cityId)){
			restResponse.setStatus(501);
			restResponse.setMessage("cityId不能为空！");
			return restResponse;
		}
		restResponse.setData(crmProjectMapper.getProjectListByCityId(cityId));
		return restResponse;
	}

	public ObjectRestResponse<List<SysProjectInfoVo>> sysProjectInfo() {
		List<SysProjectInfoVo> sysProjectInfoVos = crmProjectMapper.sysProjectInfo();
		if(null != sysProjectInfoVos && sysProjectInfoVos.size() > 0){
			for (SysProjectInfoVo sysProjectInfoVo: sysProjectInfoVos) {
				sysProjectInfoVo.setUnitList(crmUnitMapper.getUnitInfoListByProjectId(sysProjectInfoVo.getProjectId()));
			}
		}
		return ObjectRestResponse.ok(sysProjectInfoVos);
	}

	public ObjectRestResponse<List<SysProjectInfoTwoVo>> getSysProjectInfo() {
		List<SysProjectInfoTwoVo> sysProjectInfoVos = new ArrayList<>();
		List<Map<String,String>> result = crmUnitMapper.getUnitInfoList();
		if(result!=null && result.size()>0){
			Map<String,Map<String,List<UnitInfoVo>>> projectListMap = new TreeMap<>();
			Map<String,List<UnitInfoVo>> landListMap = null;
			List<UnitInfoVo> unitList = null;
			UnitInfoVo unitInfoVo = null;
			//组装树层级的map
			for (Map<String,String> map : result){
				String projectId = map.get("projectId");
				String projectName = map.get("projectName");
				String blockId = map.get("blockId");
				String blockName = map.get("blockName");
				String unitId = map.get("unitId");
				String unitName = map.get("unitName");

				String projectIdKey = projectId + "$$" + projectName;
				String blockIdKey = blockId + "$$" + blockName;
				landListMap = projectListMap.get(projectIdKey);
				if(landListMap==null){
					landListMap = new TreeMap<>();
					projectListMap.put(projectIdKey,landListMap);
				}
				unitList = landListMap.get(blockIdKey);
				if(unitList==null){
					unitList = new ArrayList<>();
					landListMap.put(blockIdKey,unitList);
				}
				unitInfoVo = new  UnitInfoVo();
				unitInfoVo.setUnitId(unitId);
				unitInfoVo.setUnitName(unitName);
				unitList.add(unitInfoVo);
			}

			//map转换成list
			if(projectListMap!=null && projectListMap.size()>0){
				SysProjectInfoTwoVo sysProjectInfoTwoVo = null;
				List<LandInfoVo> landList = null;
				LandInfoVo landInfoVo = null;
				List<UnitInfoVo> unitListTemp = null;
				for(String projectId : projectListMap.keySet()){
					sysProjectInfoTwoVo = new SysProjectInfoTwoVo();
					sysProjectInfoTwoVo.setProjectId(projectId.split("\\$\\$")[0]);
					sysProjectInfoTwoVo.setProjectName(projectId.split("\\$\\$")[1]);

					landListMap = projectListMap.get(projectId);
					if(landListMap!=null && landListMap.size()>0){
						landList = new ArrayList<LandInfoVo>();
						for(String landId : landListMap.keySet()){
							landInfoVo = new LandInfoVo();
							landInfoVo.setLandId(landId.split("\\$\\$")[0]);
							landInfoVo.setLandName(landId.split("\\$\\$")[1]);
							unitListTemp = landListMap.get(landId);
							if(unitListTemp!=null && unitListTemp.size()>0){
								landInfoVo.setUnitList(unitListTemp);
							}
							landList.add(landInfoVo);
						}
						sysProjectInfoTwoVo.setLandList(landList);
					}
					sysProjectInfoVos.add(sysProjectInfoTwoVo);
				}
			}
		}
		return ObjectRestResponse.ok(sysProjectInfoVos);
	}

	/**
	 * 按照标准城市编码查询项目id
	 * @param cityCodeList 标准市场编码
	 */
	public List<String> findProjectIdListByCCodeList(List<String> cityCodeList) {
		if(CollectionUtils.isEmpty(cityCodeList)){
			return Collections.emptyList();
		}
		return this.mapper.selectProjectIdListByCCodeList(cityCodeList);
	}

	public int count() {
		return this.mapper.count();
	}
}
