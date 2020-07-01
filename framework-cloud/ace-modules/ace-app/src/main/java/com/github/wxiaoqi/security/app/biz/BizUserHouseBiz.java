package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizUserAudit;
import com.github.wxiaoqi.security.app.entity.BizUserHouse;
import com.github.wxiaoqi.security.app.entity.BizUserProject;
import com.github.wxiaoqi.security.app.fegin.LogService;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BizUserHouseMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserProjectMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChooseHouseVo;
import com.github.wxiaoqi.security.app.vo.crm.in.CheckMemberBody;
import com.github.wxiaoqi.security.app.vo.face.UserFaceInfo;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.user.UserInfo;
import com.github.wxiaoqi.security.app.vo.useraudit.out.HouseAllUserInfosVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户和房屋关系表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
@Slf4j
public class BizUserHouseBiz extends BusinessBiz<BizUserHouseMapper,BizUserHouse> {

	@Autowired
	private BaseAppClientUserBiz clientUserBiz;

	@Autowired
	private BizUserAuditBiz userAuditBiz;

	@Autowired
	private ToolFegin toolFegin;

	@Autowired
	private QrProvideFegin qrProvideFegin;

	@Autowired
	private BizUserHouseMapper userHouseMapper;

	@Autowired
	private BizUserProjectMapper userProjectMapper;

	@Autowired
	private BizUserProjectBiz userProjectBiz;

	@Autowired
	private BizCrmHouseBiz crmHouseBiz;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CrmConfig crmConfig;

	@Autowired
	private LogService logService;
	@Autowired
	private CrmServiceBiz crmServiceBiz;
	@Autowired
	private ZhongTaiServiceBiz zhongTaiServiceBiz;

	@Autowired
	private BizAppUserFaceBiz appUserFaceBiz;

	public ObjectRestResponse   authOwner(ChooseHouseVo chooseHouseVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		BaseAppClientUser clientUser = clientUserBiz.getUserById(BaseContextHandler.getUserID());
		if(null == clientUser){
			response.setStatus(504);
			response.setMessage("未获取到当前用户的信息！");
			return response;
		}
		if(StringUtils.isEmpty(clientUser.getMobilePhone())){
			response.setStatus(505);
			response.setMessage("未获取到当前用户的手机号！");
			return response;
		}
		BizUserHouse param = new BizUserHouse();
		param.setHouseId(chooseHouseVo.getHouseId());
		param.setUserId(BaseContextHandler.getUserID());
		param.setStatus("1");
//		param.setIdentityType(chooseHouseVo.getIdentityType());
		param.setIsDelete("0");
		List<BizUserHouse> userHouseList = this.selectList(param);
		if(userHouseList.size() > 0){
			response.setStatus(506);
			response.setMessage("你已经在当前房屋内了！");
			return response;
		}
		if("3".equals(chooseHouseVo.getIdentityType())){
			BizUserAudit auditParam = new BizUserAudit();
			auditParam.setHouseId(chooseHouseVo.getHouseId());
			auditParam.setApplyId(BaseContextHandler.getUserID());
			auditParam.setAuditStatus("0");
			if(userAuditBiz.selectList(auditParam).size() > 0){
				response.setStatus(508);
				response.setMessage("你已经申请过该房屋了，请耐心等待业主审核！");
				return response;
			}
			String flag = com.github.wxiaoqi.security.common.util.BeanUtils.getActiveProfile();
//			if("dev".equals(flag) ){
			if("dev".equals(flag) || "test".equals(flag)){
				BizUserHouse userHouse = new BizUserHouse();
				userHouse.setId(UUIDUtils.generateUuid());
				userHouse.setUserId(BaseContextHandler.getUserID());
				userHouse.setHouseId(chooseHouseVo.getHouseId());
				userHouse.setIdentityType("3");
				userHouse.setHouseOwner(chooseHouseVo.getHouseOwner());
				userHouse.setIsDelete("0");
				userHouse.setStatus("1");
				//userHouse.setIsNow("1");
				userHouse.setCreateBy(BaseContextHandler.getUserID());
				userHouse.setCreateTime(new Date());
				this.insertSelective(userHouse);
				clientUser.setIsAuth("1");
				clientUser.setUpdTime(new Date());
				clientUserBiz.updateSelectiveById(clientUser);
				try{
					qrProvideFegin.generateFormalPassQr(null);
				}catch(Exception e){
					log.error("生成二维码报错！报错:{}"+e);
				}
				response.setMessage("添加房屋成功成功");
				return response;
			}else {
				ObjectRestResponse checkMemberRes = crmServiceBiz.authUserHouseFromCrm(chooseHouseVo.getHouseOwner(), clientUser.getMobilePhone(), chooseHouseVo.getHouseId());
				if (checkMemberRes.getStatus() == 200) {
					CheckMemberBody memberBody = (CheckMemberBody) checkMemberRes.getData();
					if("1".equals(memberBody.getIsOwner())){
						BizUserHouse param1 = new BizUserHouse();
						param1.setHouseId(chooseHouseVo.getHouseId());
						param1.setUserId(BaseContextHandler.getUserID());
						param1.setStatus("1");
						List<BizUserHouse> userHouses = this.selectList(param1);
						if(userHouses.size()>0){
							BizUserHouse userHouse = userHouses.get(0);
							userHouse.setIdentityType("3");
							userHouse.setHouseOwner(chooseHouseVo.getHouseOwner());
							userHouse.setIsDelete("0");
							userHouse.setStatus("1");
							//userHouse.setIsNow("1");
							userHouse.setModifyBy(BaseContextHandler.getUserID());
							userHouse.setModifyTime(new Date());
							this.updateSelectiveById(userHouse);
						}else {
							BizUserHouse userHouse = new BizUserHouse();
							userHouse.setId(UUIDUtils.generateUuid());
							userHouse.setUserId(BaseContextHandler.getUserID());
							userHouse.setHouseId(chooseHouseVo.getHouseId());
							userHouse.setIdentityType("3");
							userHouse.setHouseOwner(chooseHouseVo.getHouseOwner());
							userHouse.setIsDelete("0");
							userHouse.setStatus("1");
							//userHouse.setIsNow("1");
							userHouse.setCreateBy(BaseContextHandler.getUserID());
							userHouse.setCreateTime(new Date());
							this.insertSelective(userHouse);
						}
						clientUser.setIsAuth("1");
						clientUser.setUpdTime(new Date());

						clientUser.setMemberId(memberBody.getMemberId());
						clientUser.setCrmUserId(memberBody.getCrmUserId());
						if(StringUtils.isNotEmpty(memberBody.getIsSignService())){
							clientUser.setIsSignService(memberBody.getIsSignService());
						}else{
							clientUser.setIsSignService(memberBody.getIsSigned());
						}
						clientUserBiz.updateSelectiveById(clientUser);
						try{
							qrProvideFegin.generateFormalPassQr(null);
						}catch(Exception e){
							log.error("生成二维码报错！报错:{}"+e);
						}
						response.setMessage("添加房屋成功成功");
						return response;

					}else {
						response.setStatus(513);
						response.setMessage(memberBody.getTips());
						return response;
					}
				}else {
					return checkMemberRes;
				}

			}

		}else {
			param.setId(null);
			param.setIdentityType("3");
			param.setUserId(null);
			List<BizUserHouse> userHouses = this.selectList(param);
			if(userHouses.size() < 1){
				response.setStatus(507);
				response.setMessage("当前房屋还没有业主！");
				return response;
			}

			BizUserAudit userAudit = new BizUserAudit();
			userAudit.setHouseId(chooseHouseVo.getHouseId());
			userAudit.setApplyId(BaseContextHandler.getUserID());
			userAudit.setAuditStatus("0");
			if(userAuditBiz.selectList(userAudit).size() > 0){
				response.setStatus(508);
				response.setMessage("你已经申请过该房屋了，请耐心等待业主审核！");
				return response;
			}
			userAudit.setId(UUIDUtils.generateUuid());
			userAudit.setIdentityType(chooseHouseVo.getIdentityType());
			userAudit.setStatus("1");
			userAudit.setCreateBy(BaseContextHandler.getUserID());
			userAudit.setCreateTime(new Date());
			userAuditBiz.insertSelective(userAudit);

			Map<String,String> msgParam = new HashMap<>();
			msgParam.put("userName",clientUserBiz.getUserNameById(BaseContextHandler.getUserID()).getName());
			String houseInfo = "";
			HouseInfoVO houseInfoVO = crmHouseBiz.getHouseInfoVoByHouseId(chooseHouseVo.getHouseId());
			if(null != houseInfoVO){
				houseInfo = houseInfoVO.getCityName() + houseInfoVO.getProjectName()
						+ houseInfoVO.getBuildName() + houseInfoVO.getFloorName() + houseInfoVO.getHouseName();
			}
			msgParam.put("houseInfo",houseInfo);
			String identity = "";
			if("1".equals(chooseHouseVo.getIdentityType())){
				identity = "家属";
			}
			if("2".equals(chooseHouseVo.getIdentityType())){
				identity = "租客";
			}
			msgParam.put("identity",identity);
			for (BizUserHouse userHouse : userHouses) {
				BaseAppClientUser user = new BaseAppClientUser();
				user.setId(userHouse.getUserId());
				user.setIsAuth("1");
				user.setIsDeleted("1");
				user.setStatus("1");
				BaseAppClientUser appClientUser = clientUserBiz.selectOne(user);
				if(StringUtils.isEmpty(appClientUser.getMobilePhone())){
					toolFegin.getCode(appClientUser.getMobilePhone(),null,null,"2", MsgThemeConstants.OWNER_AUDIT, JSON.toJSONString(msgParam));
				}
			}
			response.setMessage("申请成功，等待业主审核！");
		}
		return response;
	}

	public void saveLog(ResponseEntity<String> responseEntity){
		try {
			InetAddress address = InetAddress.getLocalHost();
			LogInfoVo logInfoVo = new LogInfoVo();
			logInfoVo.setLogType("2");
			logInfoVo.setLogName("认证房屋业主");
			logInfoVo.setIp(address.getHostAddress());
			logInfoVo.setType("2");
			logInfoVo.setMessage(responseEntity.toString());
			logInfoVo.setCreateTime(new Date());
			logInfoVo.setCreateBy(BaseContextHandler.getUserID());
			logService.savelog(logInfoVo);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void saveLogEx(Exception parm){
		try {
			InetAddress address = InetAddress.getLocalHost();
			LogInfoVo logInfoVo = new LogInfoVo();
			logInfoVo.setLogType("2");
			logInfoVo.setLogName("认证房屋业主");
			logInfoVo.setIp(address.getHostAddress());
			logInfoVo.setType("2");
			logInfoVo.setMessage(parm.toString());
			logInfoVo.setCreateTime(new Date());
			logInfoVo.setCreateBy(BaseContextHandler.getUserID());
			logService.savelog(logInfoVo);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public ObjectRestResponse<UserHouseVo> getHouseLists() {
		return ObjectRestResponse.ok(userHouseMapper.getHouseListsByUserId(BaseContextHandler.getUserID()));
	}

	public boolean isHasHouse(String userId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(userId);
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		return 0 < this.selectList(userHouse).size();
	}
	public boolean isHasNowHouse(String userId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(userId);
		userHouse.setIsNow("1");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		return 0 < this.selectList(userHouse).size();
	}
	public boolean isHasHouseOwner(String userId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(userId);
		userHouse.setIdentityType("3");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		return 0 < this.selectList(userHouse).size();
	}

	public void setNowHouse(String userId){
		if(isHasHouseOwner(userId)){
			userHouseMapper.setOwnerHouseIsNow(userId);
		}else {
			userHouseMapper.setHouseIsNow(userId);
		}
	}

	public ObjectRestResponse<CurrentHouseDetailInfosVo> getCurrentHouseDetailInfos() {
		ObjectRestResponse<CurrentHouseDetailInfosVo> response = new ObjectRestResponse<>();
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setIsNow("1");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		userHouse = this.selectOne(userHouse);
		if(null == userHouse || StringUtils.isEmpty(userHouse.getId())){
			response.setMessage("该用户没有当前房屋！");
			response.setStatus(501);
			return response;
		}else {
			CurrentHouseDetailInfosVo currentHouseDetailInfosVo = new CurrentHouseDetailInfosVo();
			UserHouseVo userHouseVo = userHouseMapper.getCurrentHouseInfoById(userHouse.getId());
			BeanUtils.copyProperties(userHouseVo, currentHouseDetailInfosVo);
			UserInfoVo ownInfo = userHouseMapper.getOwnInfoById(userHouse.getId());
			currentHouseDetailInfosVo.setOwnInfo(ownInfo);
			List<UserInfoVo> otherInfos = userHouseMapper.getOtherInfosByHouseId(userHouse.getHouseId(),BaseContextHandler.getUserID());
			currentHouseDetailInfosVo.setOtherInfos(otherInfos);
			response.setData(currentHouseDetailInfosVo);
		}
		return response;
	}

	public ObjectRestResponse<List<OtherHouseDetailInfosVo>> getOtherHousesDetailInfos() {
		ObjectRestResponse<List<OtherHouseDetailInfosVo>> objectRestResponse = new ObjectRestResponse<>();
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setIsNow("0");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		if(this.selectCount(userHouse).intValue() < 1){
			objectRestResponse.setMessage("该用户没有其他房屋！");
			objectRestResponse.setStatus(501);
			return objectRestResponse;
		}else {
			List<OtherHouseDetailInfosVo> otherHouseDetailInfosVos = userHouseMapper.getOthenHousesInfoByUserId(BaseContextHandler.getUserID());
			for (OtherHouseDetailInfosVo houseInfo: otherHouseDetailInfosVos) {
				if(!StringUtils.isEmpty(houseInfo.getHouseId())){
					List<UserInfoVo> userInfoVos = userHouseMapper.getAllUserInfosByHouseId(houseInfo.getHouseId());
					houseInfo.setOtherInfos(userInfoVos);
				}
			}
			objectRestResponse.setData(otherHouseDetailInfosVos);
		}
		return objectRestResponse;
	}

	public ObjectRestResponse<HouseAllUserInfosVo> getHouseAllUserInfos(String houseId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(houseId)){
			response.setStatus(501);
			response.setMessage("houseId为空！");
			return response;
		}
		HouseAllUserInfosVo userInfosVo = new HouseAllUserInfosVo();
		UserInfoVo ownInfo = userHouseMapper.getOwnInfoByHouseId(houseId,BaseContextHandler.getUserID());
		userInfosVo.setOwnInfo(ownInfo);
		List<UserInfoVo> otherInfos = userHouseMapper.getOtherInfosByHouseId(houseId,BaseContextHandler.getUserID());
		userInfosVo.setOtherInfos(otherInfos);
		return ObjectRestResponse.ok(userInfosVo);
	}

	public ObjectRestResponse deleteHouse(String houseId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(houseId)){
			response.setStatus(501);
			response.setMessage("houseId为空！");
			return response;
		}
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setHouseId(houseId);
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		userHouse = this.selectOne(userHouse);
		if(StringUtils.isEmpty(userHouse.getId())){
			response.setMessage("houseId错误！");
			response.setStatus(502);
			return response;
		}else {
			if("3".equals(userHouse.getIdentityType())){
				response.setMessage("该用户是业主，不能删除房屋！");
				response.setStatus(503);
				return response;
			}else {
				userHouse.setStatus("0");
				userHouse.setIsNow("0");
				userHouse.setModifyTime(new Date());
				userHouse.setModifyBy(BaseContextHandler.getUserID());
				this.updateSelectiveById(userHouse);
				try{
					qrProvideFegin.deletePassQr(BaseContextHandler.getUserID());
					log.info("调用删除人脸权限接口开始");
					UserFaceInfo userFaceInfo = new UserFaceInfo();
					userFaceInfo.setUnitId(userHouseMapper.getUnitIdById(houseId));
					userFaceInfo.setUserId(BaseContextHandler.getUserID());
					ObjectRestResponse rest = appUserFaceBiz.deleteFaceByUser(userFaceInfo);
					log.info("调用删除人脸权限接口结束,调用结果：" + JSONObject.toJSONString(rest));
				}catch(Exception e){
					log.error("删除二维码报错！报错:{}"+e);
				}
			}
		}
		setNowHouseByUserId(BaseContextHandler.getUserID());
		return ObjectRestResponse.ok("删除成功");
	}

	public void setNowHouseByUserId(String userId){
		BizUserHouse house = new BizUserHouse();
		house.setUserId(userId);
		house.setStatus("1");
		house.setIsDelete("0");
		List<BizUserHouse> userHouses = this.selectList(house);
		if(userHouses.size() < 1){
			BaseAppClientUser appClientUser = clientUserBiz.selectById(userId);
			appClientUser.setIsAuth("0");
			appClientUser.setUpdTime(new Date());
			clientUserBiz.updateSelectiveById(appClientUser);
		}else {
			if(!isHasNowHouse(userId)){
				if(1 == userHouses.size()){
					userHouses.get(0).setIsNow("1");
					userHouses.get(0).setModifyTime(new Date());
					updateSelectiveById(userHouses.get(0));
				}else {
					setNowHouse(userId);
				}
			}
		}
	}

	public ObjectRestResponse switchHouse(String newHouseId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if (StringUtils.isEmpty(newHouseId)){
			response.setMessage("newHouseId不能为空！");
			response.setStatus(502);
			return response;
		}
		BizUserHouse param = new BizUserHouse();
		param.setUserId(BaseContextHandler.getUserID());
		param.setHouseId(newHouseId);
		param.setIsDelete("0");
		param.setStatus("1");
		if (this.selectCount(param).intValue() < 1){
			response.setMessage("当前用户不是要切换的房屋的住户！");
			response.setStatus(504);
			return response;
		}
		String currentHouseId = null;
		param.setId(null);
		param.setHouseId(null);
		param.setIsNow("1");
		BizUserHouse userHouse = this.selectOne(param);
		if(null != userHouse && !StringUtils.isEmpty(userHouse.getId())){

			if(newHouseId.equals(userHouse.getHouseId())){
				response.setMessage("当前房屋id和要切换的房屋id一致！");
				response.setStatus(503);
				return response;
			}else {
				currentHouseId = userHouse.getHouseId();
			}
		}
		BizUserProject userProject = new BizUserProject();
		userProject.setUserId(BaseContextHandler.getUserID());
		userProject.setIsNow("1");
		userProject.setStatus("1");
		userProject = userProjectBiz.selectOne(userProject);
		if(userProject != null && !StringUtils.isEmpty(userProject.getProjectId())){
			userProjectMapper.changOut(userProject.getProjectId(),BaseContextHandler.getUserID());
		}
		if(!StringUtils.isEmpty(currentHouseId)){
			userHouseMapper.changOut(currentHouseId,BaseContextHandler.getUserID());
		}
		BizUserProject newProjecct = userProjectBiz.getProjectByHouseIdAndUserId(newHouseId,BaseContextHandler.getUserID());
		if(null != newProjecct && !StringUtils.isEmpty(newProjecct.getProjectId())){
			userProjectMapper.changNow(newProjecct.getProjectId(),BaseContextHandler.getUserID());
		}
		userHouseMapper.changNow(newHouseId,BaseContextHandler.getUserID());
		return ObjectRestResponse.ok("切换成功！");
	}

	public ObjectRestResponse<HouseInfoVo> getCurrentHouse() {
		ObjectRestResponse<HouseInfoVo> response = new ObjectRestResponse<>();
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setIsNow("1");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		userHouse = this.selectOne(userHouse);
		if(userHouse ==null || StringUtils.isEmpty(userHouse.getId())){
			response.setMessage("该用户没有当前房屋！");
			response.setStatus(501);
			return response;
		}else {
			HouseInfoVo houseInfoVo = new HouseInfoVo();
			UserHouseVo userHouseVo = userHouseMapper.getCurrentHouseInfoById(userHouse.getId());
			BeanUtils.copyProperties(userHouseVo, houseInfoVo);
			response.setData(houseInfoVo);
		}
		return response;
	}

	public ObjectRestResponse<List<HouseInfoVo>> getCurrentUserAllHouse(String projectId) {
		ObjectRestResponse<List<HouseInfoVo>> response = new ObjectRestResponse<>();
		List<UserHouseVo> userHouseVo = null;
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setIsNow("1");
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		userHouse = this.selectOne(userHouse);
		if(StringUtils.isEmpty(userHouse.getId())){
			response.setMessage("该用户没有当前房屋！");
			response.setStatus(501);
			return response;
		}else {
			userHouseVo = userHouseMapper.selectUserAllHouse(userHouse.getUserId(),projectId);
		}
		return ObjectRestResponse.ok(userHouseVo);
	}

	public BizUserHouse getCurrentHouseByProjectIdAndUserId(String projectId, String userId) {
		return userHouseMapper.getCurrentHouseByProjectIdAndUserId(projectId,userId);
	}

	public List<UserInfo> getUserInfoByHouseId(String houseId, String searchVal, Integer page,Integer limit) {
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
		return userHouseMapper.getUserInfoByHouseId(houseId, searchVal, startIndex, limit);

	}

	public ObjectRestResponse<List<HouseInfoVO>> getHomesHouses() {
		ObjectRestResponse<List<HouseInfoVO>> response = new ObjectRestResponse<>();
		List<HouseInfoVO> houseInfoVos = userHouseMapper.getHouseInfosByUserId(BaseContextHandler.getUserID());
		response.setData(houseInfoVos);
		return response;
	}

	public List<UserInfo> getUserInfoByHouseIdWeb(String houseId, String searchVal, Integer page, Integer limit) {
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
		return userHouseMapper.getUserInfoByHouseIdWeb(houseId, searchVal, startIndex, limit);
	}


}