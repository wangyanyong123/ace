package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.im.entity.BizHousekeeperUser;
import com.github.wxiaoqi.security.im.mapper.BizHousekeeperUserMapper;
import com.github.wxiaoqi.security.im.vo.housekeeperchat.out.*;
import com.github.wxiaoqi.security.im.vo.userchat.out.HouseKeeperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 管家和用户关系表
 *
 * @author zxl
 * @Date 2018-12-18 15:03:50
 */
@Service
public class BizHousekeeperUserBiz extends BusinessBiz<BizHousekeeperUserMapper,BizHousekeeperUser> {

	@Autowired
	private BizHousekeeperUserMapper housekeeperUserMapper;

	public List<UserInfoVo> getUserInfoList(String searchVal, Integer page, Integer limit) {
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
		List<UserInfoVo> houseKeeperList =  housekeeperUserMapper.getUserInfoList(BaseContextHandler.getUserID(),searchVal, startIndex, limit);
		return houseKeeperList;
	}

	public int getUserInfoNum(String searchVal) {
		return housekeeperUserMapper.getUserInfoNum(BaseContextHandler.getUserID(),searchVal);
	}

	public ObjectRestResponse<HouseKeeperVo> getHouseKeeperInfo() {
		ObjectRestResponse<HouseKeeperVo> response = new ObjectRestResponse<>();
		int num = housekeeperUserMapper.isHasHouse(BaseContextHandler.getUserID());
		if (num < 1){
			response.setStatus(501);
			response.setMessage("请先添加房屋信息，才能和管家聊天哦！");
			return response;
		}else if (num > 1){
			response.setStatus(502);
			response.setMessage("数据异常，该用户同时在两个房屋内！");
			return response;
		}else {
			HouseKeeperVo houseKeeperVo = housekeeperUserMapper.getHouseKeeperInfo(BaseContextHandler.getUserID());
			if(houseKeeperVo != null){
				houseKeeperVo.setIdentity("金茂物业");
				houseKeeperVo.setDutyName("管家");
			}
			response.setData(houseKeeperVo);
			return response;
		}
	}

	public ObjectRestResponse<List<BlockInfoVo>> getBlockList() {
		return new ObjectRestResponse<>().data(housekeeperUserMapper.getBlockList(BaseContextHandler.getUserID()));
	}

	public List<BuildInfoVo> getBuildList(String blockId) {
		return housekeeperUserMapper.getBuildList(blockId,BaseContextHandler.getUserID());
	}

	public List<UnitInfoVo> getUnitList(String buildId) {
		return housekeeperUserMapper.getUnitList(buildId);
	}

	public List<FloorInfoVo> getFloorList(String unitId) {
		return housekeeperUserMapper.getFloorList(unitId);
	}

	public List<HouseInfoVo> getHouseList(String floorId) {
		return housekeeperUserMapper.getHouseList(floorId);
	}

	public List<HouseholdVo> getHouseholdList(String houseId) {
		return housekeeperUserMapper.getHouseholdList(houseId);
	}
}