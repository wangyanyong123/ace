package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.app.entity.BizCrmHouse;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.city.out.*;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoTree;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.house.HouseTreeList;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 房屋表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Service
@Slf4j
public class BizCrmHouseBiz extends BusinessBiz<BizCrmHouseMapper,BizCrmHouse> {
	@Autowired
	private BizCrmHouseMapper crmHouseMapper;
	@Autowired
	private BizCrmBuildingMapper crmBuildingMapper;
	@Autowired
	private BizCrmUnitMapper crmUnitMapper;
	@Autowired
	private BizCrmFloorMapper crmFloorMapper;
	@Autowired
	private BizCrmBlockMapper crmBlockMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void updateHouse(List<BizCrmHouse> houseList) {
		if(null != houseList && houseList.size() > 0){
			List<String> houseIds = houseList.stream().map(bizCrmHouse -> bizCrmHouse.getHouseId()).distinct().collect(toList());
			List<String> needUpdateHouseIds = new ArrayList<>();
			List<BizCrmHouse> updateHouseList = new ArrayList<>();
			List<BizCrmHouse> addHouseList = new ArrayList<>();
			List<BizCrmHouse> crmHouseList = crmHouseMapper.getByIds(houseIds);
			if(null != crmHouseList && crmHouseList.size() > 0){
				List<String> oldHouseIds = crmHouseList.stream().map(bizCrmHouse -> bizCrmHouse.getHouseId()).distinct().collect(toList());
				needUpdateHouseIds = houseIds.stream().filter(item -> oldHouseIds.contains(item)).collect(toList());
			}

			if(null != needUpdateHouseIds && needUpdateHouseIds.size() > 0){
				Iterator<BizCrmHouse> it = houseList.iterator();
				while(it.hasNext()){
					BizCrmHouse x = it.next();
					if(needUpdateHouseIds.contains(x.getHouseId())){
						updateHouseList.add(x);
						it.remove();
					}else {
						addHouseList.add(x);
					}
				}
			}else {
				addHouseList = houseList;
			}
			if(null != updateHouseList && updateHouseList.size() > 0){
				log.info("批量更新房屋信息"+updateHouseList.size()+"条");
				int num = 0;
				for (BizCrmHouse updateHouse:updateHouseList) {
					updateHouse.setModifyTime(new Date());
					if(crmHouseMapper.updateByPrimaryKeySelective(updateHouse)>0){
						num++;
					}
				}
				log.info("成功更新房屋信息"+num+"条");
			}
			if(null != addHouseList && addHouseList.size() > 0){
				log.info("批量插入房屋信息"+updateHouseList.size()+"条");
				int num = 0;
				for (BizCrmHouse addHouse:addHouseList) {
					addHouse.setCreateTime(new Date());
					addHouse.setStatus("1");
					if(crmHouseMapper.insertSelective(addHouse)>0){
						num++;
					}
				}
				log.info("成功插入房屋信息"+num+"条");
			}
		}

	}

	public ObjectRestResponse<List<BlockInfoVo>> getHouseInfoList(String projectId, int type) {
		ObjectRestResponse<List<BlockInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(projectId)){
			restResponse.setStatus(501);
			restResponse.setMessage("projectId不能为空");
			return restResponse;
		}
		List<BlockInfoVo> results = new ArrayList<>();
		results = JSON.parseArray(redisTemplate.opsForValue().get("GetHouseInfoListByProjectId:" + projectId + ":" + type), BlockInfoVo.class);
		if(results == null || results.size() < 1){
			List<BlockInfoVo> blockInfoVos = crmBlockMapper.getBlockInfoListByProjectId(projectId,type);
			for (BlockInfoVo blockInfoVo:blockInfoVos) {
//				List<BuildInfoVo> buildInfoVos = crmBuildingMapper.getBuilInfoListByProjectId(projectId,type);
				List<BuildInfoVo> buildInfoVos = crmBuildingMapper.getBuilInfoListByBlockId(blockInfoVo.getBlockId());
				for (BuildInfoVo buildInfo:buildInfoVos) {
					List<UnitInfoVo> unitInfoVos = crmUnitMapper.getUnitInfoListByBuildId(buildInfo.getBuildId(),type);
					for (UnitInfoVo unitInfo:unitInfoVos) {
						List<FloorInfoVo> floorInfoVos = crmFloorMapper.getFloorInfoListByUnitId(unitInfo.getUnitId(),type);
						Map<String, FloorInfoVo> floorInfoVoMap = floorInfoVos.stream().collect(Collectors.toMap(floorVo -> floorVo.getFloorId(), floorVo -> floorVo));
	//					List<FloorInfoVo> floorVos = new ArrayList<>();
						List<String> ids = floorInfoVos.stream().map(floorInfo -> floorInfo.getFloorId()).collect(Collectors.toList());
						if(null != ids && ids.size() > 0){
							List<HouseInfoVo> houseInfoVos = crmHouseMapper.getHouseInfoListByFloorIds(ids,type);
							if(null != houseInfoVos && houseInfoVos.size()>0){
								Map<String, List<HouseInfoVo>> houseInfoMap = houseInfoVos.stream().collect(Collectors.groupingBy(HouseInfoVo::getFloorId));
								houseInfoMap.keySet().forEach(key -> {
									floorInfoVoMap.get(key).setHouseInfoVos(houseInfoMap.get(key));
	//								floorVos.add(floorInfoVoMap.get(key));
									for(FloorInfoVo floorInfoVo : floorInfoVos){
										if(floorInfoVo.getFloorId().equals(key)){
											floorInfoVo.setHouseInfoVos(houseInfoMap.get(key));
											break;
										}
									}
								});
							}
						}
	//					for (FloorInfoVo floorInfo:floorInfoVos) {
	//						List<HouseInfoVo> houseInfoVos = crmHouseMapper.getHouseInfoListByFloorId(floorInfo.getFloorId(),type);
	//						floorInfo.setHouseInfoVos(houseInfoVos);
	//					}
						unitInfo.setFloorInfoVos(floorInfoVos);
					}
					buildInfo.setUnitInfoVos(unitInfoVos);
				}
				blockInfoVo.setBuildInfoVos(buildInfoVos);
			}
			restResponse.setData(blockInfoVos);
//			System.out.println("GetHouseInfoListByProjectId:"+projectId);
//			System.out.println(JSON.toJSONString(buildInfoVos));
			redisTemplate.opsForValue().set("GetHouseInfoListByProjectId:"+projectId + ":" + type,JSON.toJSONString(blockInfoVos),30, TimeUnit.DAYS);
		}else {
			restResponse.setData(results);
		}
		return restResponse;
	}

	public HouseInfoVO getHouseInfoVoByHouseId(String houseId) {
		return crmHouseMapper.getHouseInfoVoByHouseId(houseId);
	}

	public TableResultResponse<HouseInfoTree> getHouseInfoTree(String projectId, int type) {
		TableResultResponse<HouseInfoTree> restResponse = new TableResultResponse<>();
		if(StringUtils.isEmpty(projectId)){
			restResponse.setStatus(501);
			restResponse.setMessage("projectId不能为空");
			return restResponse;
		}
		List<HouseInfoTree> houseInfoTrees = new ArrayList<>();
		houseInfoTrees = JSON.parseArray(redisTemplate.opsForValue().get("GetHouseInfoTreeByProjectId:" + projectId + ":" + type), HouseInfoTree.class);
		if(houseInfoTrees == null || houseInfoTrees.size() < 1){
			List<HouseTreeList> houseTrees = crmHouseMapper.getHouseInfoTree(projectId,type);
			if (houseTrees != null) {
				List<HouseInfoTree> trees = new ArrayList<>();
				houseTrees.forEach(houseTree -> {
					trees.add(new HouseInfoTree(houseTree.getId(), houseTree.getParentId(), houseTree.getName(), houseTree.getCode()));
				});
				long time = System.currentTimeMillis();
				System.out.println("start====>"+time);
	//			houseInfoTrees = TreeUtil.bulid(trees, "1", null);
				houseInfoTrees = TreeUtil.buildByRecursive(trees, projectId);
				System.out.println("end====>"+(System.currentTimeMillis()-time));
			}
			redisTemplate.opsForValue().set("GetHouseInfoTreeByProjectId:"+projectId + ":" + type,JSON.toJSONString(houseInfoTrees),30, TimeUnit.DAYS);
		}
		return new TableResultResponse<HouseInfoTree>(houseInfoTrees.size(),houseInfoTrees);
	}

	public ObjectRestResponse<List<HouseInfoVo>> getUnitInfoListByFloorId(String floorId, int type) {
		ObjectRestResponse<List<HouseInfoVo>> restResponse = new ObjectRestResponse<>();
		if(StringUtils.isEmpty(floorId)){
			restResponse.setStatus(501);
			restResponse.setMessage("floorId不能为空");
			return restResponse;
		}
		List<String> ids = new ArrayList<>();
		ids.add(floorId);
		List<HouseInfoVo> houseInfoVos = crmHouseMapper.getHouseInfoListByFloorIds(ids,type);
		restResponse.setData(houseInfoVos);
		return restResponse;
	}
}