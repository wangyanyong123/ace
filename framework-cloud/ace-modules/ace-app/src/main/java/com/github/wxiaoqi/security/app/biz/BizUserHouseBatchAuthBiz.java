package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.mapper.BizCrmHouseMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserHouseBatchAuthMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChooseHouseVo;
import com.github.wxiaoqi.security.app.vo.crm.in.CheckMemberBody;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserHoseInfo;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
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
 * 批量认证表
 *
 * @author zxl
 * @Date 2019-09-25 15:19:18
 */
@Service
@Slf4j
public class BizUserHouseBatchAuthBiz extends BusinessBiz<BizUserHouseBatchAuthMapper,BizUserHouseBatchAuth> {

	@Autowired
	private CrmServiceBiz crmServiceBiz;

	@Autowired
	private BaseAppClientUserBiz clientUserBiz;

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@Autowired
	private QrProvideFegin qrProvideFegin;

	@Autowired
	private BizCrmHouseMapper houseMapper;

	@Autowired
	private BizUserProjectBiz userProjectBiz;

	public String batchAuth() {
		List<UserHoseInfo> userHoseInfos = mapper.getUserHoseInfos();
		if(userHoseInfos.size() > 0){
			for (UserHoseInfo hoseInfo: userHoseInfos) {
				String[] houseCodes = hoseInfo.getHouseCode().split(",");
				if(houseCodes.length>0){
					String msg = "";
					boolean flag = false;
					for (int i = 0; i < houseCodes.length; i++){
						BizCrmHouse house = houseMapper.getHouseByCode(houseCodes[i]);
						if(null == house){
							flag = true;
							msg = msg + houseCodes[i] +",";
						}else {
							ObjectRestResponse response = authOwner(hoseInfo.getUserId(),house.getHouseId(),hoseInfo.getUserName());
							if (response.getStatus() == 200) {
								BizUserHouseBatchAuth batchAuth = new BizUserHouseBatchAuth();
								batchAuth.setId(hoseInfo.getId());
								batchAuth.setIsSend("1");
								batchAuth.setIsSuc("1");
								batchAuth.setAuthResult(JSON.toJSONString(response));
								mapper.updateByPrimaryKeySelective(batchAuth);
								userProjectBiz.insertProject(hoseInfo.getProjectId());
							}else {
								flag = true;
								log.error("认证失败=》" + houseCodes[i] +":"+ JSON.toJSONString(response));
								msg = msg + houseCodes[i] +":"+ JSON.toJSONString(response) +",";
								BizUserHouseBatchAuth batchAuth = new BizUserHouseBatchAuth();
								batchAuth.setId(hoseInfo.getId());
								batchAuth.setIsSend("1");
								batchAuth.setIsSuc("0");
								batchAuth.setAuthResult(JSON.toJSONString(response));
								mapper.updateByPrimaryKeySelective(batchAuth);
							}
						}
					}
					if(flag){
						BizUserHouseBatchAuth batchAuth = new BizUserHouseBatchAuth();
						batchAuth.setId(hoseInfo.getId());
						batchAuth.setIsSend("1");
						batchAuth.setIsSuc("0");
						batchAuth.setAuthResult("房屋编码错误！=> " + msg);
						mapper.updateByPrimaryKeySelective(batchAuth);
					}
				}else {
					BizUserHouseBatchAuth batchAuth = new BizUserHouseBatchAuth();
					batchAuth.setId(hoseInfo.getId());
					batchAuth.setIsSend("1");
					batchAuth.setIsSuc("0");
					batchAuth.setAuthResult("房屋编码错误！");
					mapper.updateByPrimaryKeySelective(batchAuth);
				}
			}
		}
		return "success";
	}
	public ObjectRestResponse authOwner(String userId,String houseId,String name) {
		ObjectRestResponse response = new ObjectRestResponse();
		BaseAppClientUser clientUser = clientUserBiz.getUserById(userId);
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
		param.setHouseId(houseId);
		param.setUserId(userId);
		param.setStatus("1");
		param.setIsDelete("0");
		List<BizUserHouse> userHouseList = userHouseBiz.selectList(param);
		if(userHouseList.size() > 0){
			response.setStatus(506);
			response.setMessage("已经在当前房屋内了!");
			return response;
		}

		ObjectRestResponse checkMemberRes = crmServiceBiz.authUserHouseFromCrm(name, clientUser.getMobilePhone(), houseId);
		if (checkMemberRes.getStatus() == 200) {
			CheckMemberBody memberBody = (CheckMemberBody) checkMemberRes.getData();
			if("1".equals(memberBody.getIsOwner())){
				BizUserHouse param1 = new BizUserHouse();
				param1.setHouseId(houseId);
				param1.setUserId(userId);
				param1.setStatus("1");
				List<BizUserHouse> userHouses = userHouseBiz.selectList(param1);
				if(userHouses.size()>0){
					BizUserHouse userHouse = userHouses.get(0);
					userHouse.setIdentityType("3");
					userHouse.setHouseOwner(name);
					userHouse.setIsDelete("0");
					userHouse.setStatus("1");
					userHouse.setModifyBy(userId);
					userHouse.setModifyTime(new Date());
					userHouseBiz.updateSelectiveById(userHouse);
				}else {
					BizUserHouse userHouse = new BizUserHouse();
					userHouse.setId(UUIDUtils.generateUuid());
					userHouse.setUserId(userId);
					userHouse.setHouseId(houseId);
					userHouse.setIdentityType("3");
					userHouse.setHouseOwner(name);
					userHouse.setIsDelete("0");
					userHouse.setStatus("1");
					userHouse.setCreateBy(userId);
					userHouse.setCreateTime(new Date());
					userHouseBiz.insertSelective(userHouse);
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

}