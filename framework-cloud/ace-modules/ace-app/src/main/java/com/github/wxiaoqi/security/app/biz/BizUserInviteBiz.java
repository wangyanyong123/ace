package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.face.UserFaceInfo;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.app.vo.useraudit.in.AuditUserVo;
import com.github.wxiaoqi.security.app.vo.useraudit.in.DeleteUserVo;
import com.github.wxiaoqi.security.app.vo.useraudit.in.InviteUserVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户邀请成为房屋成员表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
@Slf4j
public class BizUserInviteBiz extends BusinessBiz<BizUserInviteMapper,BizUserInvite> {
	@Autowired
	private BaseAppClientUserBiz clientUserBiz;

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@Autowired
	private BizUserAuditBiz userAuditBiz;

	@Autowired
	private ToolFegin toolFegin;

	@Autowired
	private QrProvideFegin qrProvideFegin;

	@Autowired
	private BizCrmHouseBiz crmHouseBiz;

	@Autowired
	private BizUserProjectMapper userProjectMapper;

	@Autowired
	private BizCrmHouseMapper bizCrmHouseMapper;

	@Autowired
	private BizUserProjectBiz userProjectBiz;

	@Autowired
	private SysMsgNoticeBiz sysMsgNoticeBiz;

	@Autowired
	private BizUserHouseMapper userHouseMapper;

	@Autowired
	private BizAppUserFaceBiz appUserFaceBiz;

	public ObjectRestResponse inviteUser(InviteUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();

		if( !isOwner(userVo.getHouseId())){
			response.setStatus(508);
			response.setMessage("只有业主可以邀请房屋成员！");
			return response;
		}

		BizUserInvite bizUserInvite = new BizUserInvite();
		bizUserInvite.setInvitedPhone(userVo.getMobile());
		bizUserInvite.setHouseId(userVo.getHouseId());
		bizUserInvite.setInviteStatus("0");
		bizUserInvite.setStatus("1");
		if(this.selectList(bizUserInvite).size() > 0){
			response.setStatus(509);
			response.setMessage("该用户已被邀请成为该房屋的成员！");
			return response;
		}

		BaseAppClientUser clientUser = new BaseAppClientUser();
		clientUser.setMobilePhone(userVo.getMobile());
		clientUser.setStatus("1");
		clientUser.setIsDeleted("1");
		BaseAppClientUser cUser = clientUserBiz.selectOne(clientUser);
		BizUserInvite userInvite = new BizUserInvite();
		userInvite.setId(UUIDUtils.generateUuid());
		userInvite.setInviterId(BaseContextHandler.getUserID());
		userInvite.setInvitedPhone(userVo.getMobile());
		userInvite.setHouseId(userVo.getHouseId());
		userInvite.setIdentityType(userVo.getIdentityType());
		userInvite.setCreateBy(BaseContextHandler.getUserID());
		userInvite.setCreateTime(new Date());
		if(null == cUser){
			userInvite.setInviteStatus("0");
			userInvite.setStatus("1");
			this.insertSelective(userInvite);
			Map<String,String> param = new HashMap<>();
			param.put("userName",clientUserBiz.getUserNameById(BaseContextHandler.getUserID()).getName());
			String houseInfo = "";
			HouseInfoVO houseInfoVO = crmHouseBiz.getHouseInfoVoByHouseId(userVo.getHouseId());
			if(null != houseInfoVO){
				houseInfo = houseInfoVO.getCityName() + houseInfoVO.getProjectName()
						+ houseInfoVO.getBuildName() + houseInfoVO.getFloorName() + houseInfoVO.getHouseName();
			}
			param.put("houseInfo",houseInfo);
			String identity = "";
			if("1".equals(userVo.getIdentityType())){
				identity = "家属";
			}
			if("2".equals(userVo.getIdentityType())){
				identity = "租客";
			}
			param.put("identity",identity);
			toolFegin.getCode(userVo.getMobile(),null,null,"2", MsgThemeConstants.OWNER_INVITE,JSON.toJSONString(param));
		} else {
			BizUserHouse param = new BizUserHouse();
			param.setHouseId(userVo.getHouseId());
			param.setUserId(cUser.getId());
			param.setStatus("1");
			BizUserHouse house = userHouseBiz.selectOne(param);
			if(null == house){
				if(userHouseBiz.isHasNowHouse(cUser.getId())){
					param.setIsNow("0");
				}else {
					param.setIsNow("1");
				}
				param.setId(UUIDUtils.generateUuid());
				param.setIsDelete("0");
				param.setIdentityType(userVo.getIdentityType());
				param.setCreateBy(BaseContextHandler.getUserID());
				param.setCreateTime(new Date());
				userHouseBiz.insertSelective(param);
				BizUserProject userProject = userProjectMapper.getProjectByHouseIdAndUserId(userVo.getHouseId(),cUser.getId());
				if(null != userProject && !StringUtils.isEmpty(userProject.getId())){
					if("0".equals(userProject.getIsNow())){
						if(!userProjectBiz.isHasNowProject(cUser.getId())){
							userProject.setIsNow("1");
							userProject.setModifyBy(BaseContextHandler.getUserID());
							userProject.setModifyTime(new Date());
							userProjectBiz.updateSelectiveById(userProject);
						}
					}
				}else {
					BizUserProject bizUserProject = new BizUserProject();
					bizUserProject.setId(UUIDUtils.generateUuid());
					bizUserProject.setCreateBy(BaseContextHandler.getUserID());
					bizUserProject.setCreateTime(new Date());
					bizUserProject.setStatus("1");
					bizUserProject.setProjectId(bizCrmHouseMapper.getHouseAllInfoVoByHouseId(userVo.getHouseId()).getProjectId());
					bizUserProject.setUserId(cUser.getId());
					if(userProjectBiz.isHasNowProject(cUser.getId())){
						bizUserProject.setIsNow("0");
					}else {
						bizUserProject.setIsNow("1");
					}
					userProjectBiz.insertSelective(bizUserProject);
				}
			}else {
				if("0".equals(house.getIsDelete())){
					response.setStatus(506);
					response.setMessage("该用户已经在当前房屋内了！");
					return response;
				}else {
					if(userHouseBiz.isHasNowHouse(cUser.getId())){
						house.setIsNow("0");
					}else {
						house.setIsNow("1");
						BizUserProject userProject = userProjectMapper.getProjectByHouseIdAndUserId(userVo.getHouseId(),cUser.getId());
						if("0".equals(userProject.getIsNow())){
							if(userProjectBiz.isHasNowProject(cUser.getId())){
								CurrentUserInfosVo userInfos = userProjectMapper.getCurrentUserInfos(cUser.getId());
								userProjectMapper.changOut(userInfos.getProjectId(),BaseContextHandler.getUserID());
								userProjectMapper.changNow(userProject.getProjectId(),BaseContextHandler.getUserID());
							}else {
								userProjectMapper.changNow(userProject.getProjectId(),BaseContextHandler.getUserID());
							}
						}

					}
					house.setIsDelete("0");
					house.setModifyBy(BaseContextHandler.getUserID());
					house.setModifyTime(new Date());
					house.setIdentityType(userVo.getIdentityType());
					userHouseBiz.updateSelectiveById(house);
				}

			}
			userInvite.setInviteStatus("1");
			userInvite.setStatus("1");
			this.insertSelective(userInvite);
			Map<String,String> msgParam = new HashMap<>();
			msgParam.put("userName",clientUserBiz.getUserNameById(BaseContextHandler.getUserID()).getName());
			String houseInfo = "";
			HouseInfoVO houseInfoVO = crmHouseBiz.getHouseInfoVoByHouseId(userVo.getHouseId());
			if(null != houseInfoVO){
				houseInfo = houseInfoVO.getCityName() + houseInfoVO.getProjectName()
						+ houseInfoVO.getBuildName() + houseInfoVO.getFloorName() + houseInfoVO.getHouseName();
			}
			msgParam.put("houseInfo",houseInfo);
			String identity = "";
			if("1".equals(userVo.getIdentityType())){
				identity = "家属";
			}
			if("2".equals(userVo.getIdentityType())){
				identity = "租客";
			}
			msgParam.put("identity",identity);
			toolFegin.getCode(userVo.getMobile(),null,null,"2", MsgThemeConstants.OWNER_INVITE_EXIST,JSON.toJSONString(msgParam));
			try{
				qrProvideFegin.generateFormalPassQr(cUser.getId());
			}catch(Exception e){
				log.error("生成二维码报错！报错:{}"+e);
			}
		}
		response.setMessage("邀请成功！");
		return response;
	}

	private boolean isOwner(String houseId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setHouseId(houseId);
		userHouse.setUserId(BaseContextHandler.getUserID());
		userHouse.setStatus("1");
		userHouse.setIdentityType("3");
		return null != userHouseBiz.selectOne(userHouse);
	}
	private boolean isOwner(String houseId,String userId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setHouseId(houseId);
		userHouse.setUserId(userId);
		userHouse.setStatus("1");
		userHouse.setIdentityType("3");
		return null != userHouseBiz.selectOne(userHouse);
	}
	private boolean isMember(String houseId,String userId){
		BizUserHouse userHouse = new BizUserHouse();
		userHouse.setHouseId(houseId);
		userHouse.setUserId(userId);
		userHouse.setStatus("1");
		userHouse.setIsDelete("0");
		return null != userHouseBiz.selectOne(userHouse);
	}

	public ObjectRestResponse audit(AuditUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		BizUserAudit userAudit = userAuditBiz.selectById(userVo.getAuditId());
		if(null == userAudit){
			response.setStatus(504);
			response.setMessage("审核id错误！");
			return response;
		}
		if( !isOwner(userAudit.getHouseId())){
			response.setStatus(505);
			response.setMessage("只有该房屋的业主可以进行审核！");
			return response;
		}
		if(!"0".equals(userAudit.getAuditStatus()) || "0".equals(userAudit.getStatus())){
			response.setStatus(506);
			response.setMessage("该条信息已经被审核过了！");
			return response;
		}
		String receiverId = userAudit.getApplyId();
		String identity = userAudit.getIdentityType();
		Map<String,String> paramMap = new HashMap<>();
		String roominfo = "";
		HouseInfoVO houseInfoVO = crmHouseBiz.getHouseInfoVoByHouseId(userAudit.getHouseId());
		if(null != houseInfoVO){
			roominfo = houseInfoVO.getCityName() + houseInfoVO.getProjectName()
					+ houseInfoVO.getBuildName() + houseInfoVO.getFloorName() + houseInfoVO.getHouseName();
		}
		paramMap.put("roominfo",roominfo);
		if("1".equals(userAudit.getIdentityType())){
			identity = "家属";
		}
		if("2".equals(userAudit.getIdentityType())){
			identity = "租客";
		}
		paramMap.put("identity",identity);
		SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
		smsNoticeVo.setReceiverId(receiverId);
		smsNoticeVo.setParamMap(paramMap);
		if("1".equals(userVo.getIsPass())){
			smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.HOUSE_AUDIT_P));
			if(isMember(userAudit.getHouseId(),userAudit.getApplyId())){
				response.setStatus(507);
				response.setMessage("该用户已经在当前房屋内了！");
				return response;
			}
			BizUserHouse param1 = new BizUserHouse();
			param1.setHouseId(userAudit.getHouseId());
			param1.setUserId(userAudit.getApplyId());
			param1.setStatus("1");
			List<BizUserHouse> userHouses = userHouseBiz.selectList(param1);
			if(userHouses.size()>0){
				BizUserHouse house = userHouses.get(0);
				if(userHouseBiz.isHasNowHouse(userAudit.getApplyId())){
					house.setIsNow("0");
				}else {
					house.setIsNow("1");
				}
				house.setStatus("1");
				house.setIsDelete("0");
				house.setIdentityType(userAudit.getIdentityType());
				house.setModifyBy(BaseContextHandler.getUserID());
				house.setModifyTime(new Date());
				userHouseBiz.updateSelectiveById(house);
			}else {
				BizUserHouse house = new BizUserHouse();
				if(userHouseBiz.isHasNowHouse(userAudit.getApplyId())){
					house.setIsNow("0");
				}else {
					house.setIsNow("1");
				}
				house.setHouseId(userAudit.getHouseId());
				house.setUserId(userAudit.getApplyId());
				house.setStatus("1");
				house.setId(UUIDUtils.generateUuid());
				house.setIsDelete("0");
				house.setIdentityType(userAudit.getIdentityType());
				house.setCreateBy(BaseContextHandler.getUserID());
				house.setCreateTime(new Date());
				userHouseBiz.insertSelective(house);
			}
			BaseAppClientUser appClientUser = new BaseAppClientUser();
			appClientUser.setId(userAudit.getApplyId());
			appClientUser.setIsAuth("1");
			appClientUser.setUpdTime(new Date());
			clientUserBiz.updateSelectiveById(appClientUser);

			try{
				qrProvideFegin.generateFormalPassQr(userAudit.getApplyId());
			}catch(Exception e){
				log.error("生成二维码报错！报错:{}"+e);
			}
		}else {
			smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.HOUSE_AUDIT_F));
		}
		userAudit.setAuditId(BaseContextHandler.getUserID());
		userAudit.setAuditStatus(userVo.getIsPass());
		userAudit.setModifyBy(BaseContextHandler.getUserID());
		userAudit.setModifyTime(new Date());
		userAuditBiz.updateSelectiveById(userAudit);
		response.setMessage("操作成功！");
		try {
			log.info(">>>>>>>>>>>>>>>>>>>>审核消息通知:" + JSON.toJSONString(smsNoticeVo));
			sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
			String userId = smsNoticeVo.getReceiverId();
			String msgParam = "";
			ObjectRestResponse restResponse = toolFegin.sendMsg(null, null, null, null, null, userId, MsgThemeConstants.MSG_LIST_NOTICE, msgParam);
			log.info("发送App消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());

		}catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public ObjectRestResponse deleteUser(DeleteUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if( !isOwner(userVo.getHouseId())){
			response.setStatus(505);
			response.setMessage("只有该房屋的业主可以进行删除操作！");
			return response;
		}
		if(isOwner(userVo.getHouseId(),userVo.getUserId())){
			response.setStatus(506);
			response.setMessage("不能删除业主！");
			return response;
		}
		BizUserHouse param = new BizUserHouse();
		param.setUserId(userVo.getUserId());
		param.setHouseId(userVo.getHouseId());
		param.setStatus("1");
		param.setIsDelete("0");
		List<BizUserHouse> userHouseList = userHouseBiz.selectList(param);
		if(userHouseList.size() < 1){
			response.setStatus(507);
			response.setMessage("该用户已经不是该房屋的成员！");
			return response;
		}else {
			for (BizUserHouse userHouse: userHouseList) {
				userHouse.setIsDelete("1");
				userHouse.setIsNow("0");
				userHouse.setModifyTime(new Date());
				userHouse.setModifyBy(BaseContextHandler.getUserID());
				userHouseBiz.updateSelectiveById(userHouse);
			}
			try{
				qrProvideFegin.deletePassQr(userVo.getUserId());
				log.info("调用删除人脸权限接口开始");
				UserFaceInfo userFaceInfo = new UserFaceInfo();
				userFaceInfo.setUnitId(userHouseMapper.getUnitIdById(userVo.getHouseId()));
				userFaceInfo.setUserId(userVo.getUserId());
				ObjectRestResponse rest = appUserFaceBiz.deleteFaceByUser(userFaceInfo);
				log.info("调用删除人脸权限接口结束,调用结果：" + JSONObject.toJSONString(rest));
			}catch(Exception e){
				log.error("删除二维码报错！报错:{}"+e);
			}
		}
		userHouseBiz.setNowHouseByUserId(userVo.getUserId());
		response.setMessage("操作成功！");
		return response;
	}
}