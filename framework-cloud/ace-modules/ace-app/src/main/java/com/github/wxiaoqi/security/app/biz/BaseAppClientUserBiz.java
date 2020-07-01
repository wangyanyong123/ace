package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizUserHouse;
import com.github.wxiaoqi.security.app.entity.BizUserInvite;
import com.github.wxiaoqi.security.app.entity.BizUserProject;
import com.github.wxiaoqi.security.app.fegin.QrProvideFegin;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizCrmHouseMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChangePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.RetrievePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.house.HouseAllInfoVo;
import com.github.wxiaoqi.security.app.vo.user.UpdateInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.common.validator.ValidatorUtils;
import com.github.wxiaoqi.security.common.validator.group.AddGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * app客户端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
@Slf4j
public class BaseAppClientUserBiz extends BusinessBiz<BaseAppClientUserMapper,BaseAppClientUser> {

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

	@Autowired
	private BizUserInviteBiz userInviteBiz;

	@Autowired
	private BaseAppClientUserMapper clientUserMapper;

	@Autowired
	private BizUserProjectBiz userProjectBiz;

	@Autowired
	private ToolFegin toolFegin;

	@Autowired
	private QrProvideFegin qrProvideFegin;

	@Autowired
	private BizCrmHouseMapper bizCrmHouseMapper;

	@Autowired
	private BaseAppServerUserMapper baseAppServerUserMapper;

	/**
	 * 修改密码
	 * @return
	 */
	public ObjectRestResponse changePassword(ChangePasswordVo changePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppClientUser appClientUser = this.getUserById(BaseContextHandler.getUserID());
		if (encoder.matches(changePasswordVo.getOldPassword(), appClientUser.getPassword())) {
			String password = encoder.encode(changePasswordVo.getNewPassword());
			appClientUser.setPassword(password);
			this.updateSelectiveById(appClientUser);
			result.setMessage("修改成功！");
			return result;
		}
		result.setStatus(504);
		result.setMessage("旧密码错误！");
		return result;
	}

	public BaseAppClientUser getUserByMobile(String mobile) {
		BaseAppClientUser appClientUser = new BaseAppClientUser();
		appClientUser.setMobilePhone(mobile);
		appClientUser.setStatus("1");
		return this.selectOne(appClientUser);
	}

	public BaseAppClientUser getUserById(String userId) {
		BaseAppClientUser appClientUser = new BaseAppClientUser();
		appClientUser.setId(userId);
		return this.selectOne(appClientUser);
	}

	@Override
	public void insertSelective(BaseAppClientUser entity) {

		ValidatorUtils.validateEntity(entity, AddGroup.class);
		String userId = UUIDUtils.generateUuid();
		String password = encoder.encode(entity.getPassword());
		entity.setId(userId);
		entity.setSex("0");
		entity.setPassword(password);
		entity.setStatus("1");
		entity.setCrtTime(new Date());
//		entity.setName("xxx_" + entity.getMobilePhone());
		entity.setTenantId(BaseContextHandler.getTenantID());
		entity.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
		entity.setIsAuth("0");
		entity.setRegistOs(entity.getRegistOs());

		Map<String, String> map = null;
		if(StringUtils.isEmpty(entity.getProfilePhoto())){
			List<String> list = Stream.of("http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo3@2x.png",
							"http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo4@2x.png",
							"http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo5@2x.png",
							"http://jmwy.oss-cn-beijing.aliyuncs.com/jinmao/test/default_photo/default_photo6@2x.png").collect(toList());
			Random random = new Random();
			int n = random.nextInt(list.size());
			entity.setProfilePhoto(list.get(n));
		}

		BizUserInvite userInvite = new BizUserInvite();
		userInvite.setInvitedPhone(entity.getMobilePhone());
		userInvite.setInviteStatus("0");
		userInvite.setStatus("1");
		List<BizUserInvite> userInviteList = userInviteBiz.selectList(userInvite);
		System.out.println("1111");
		if(userInviteList.size() > 0){
			for (BizUserInvite invite:userInviteList) {
				BizUserHouse userHouse = new BizUserHouse();
				userHouse.setId(UUIDUtils.generateUuid());
				userHouse.setUserId(userId);
				userHouse.setHouseId(invite.getHouseId());
				userHouse.setIdentityType(invite.getIdentityType());
				userHouse.setIsDelete("0");
				userHouse.setIsNow("1");
				userHouse.setStatus("1");
				userHouse.setCreateBy(invite.getInviterId());
				userHouse.setCreateTime(new Date());
				userHouseBiz.insertSelective(userHouse);
				invite.setInviteStatus("1");
				invite.setModifyBy(userId);
				invite.setModifyTime(new Date());
				userInviteBiz.updateSelectiveById(invite);
				HouseAllInfoVo houseAllInfoVo = bizCrmHouseMapper.getHouseAllInfoVoByHouseId(invite.getHouseId());
				BizUserProject userProject = new BizUserProject();
				userProject.setId(UUIDUtils.generateUuid());
				userProject.setUserId(userId);
				userProject.setProjectId(houseAllInfoVo.getProjectId());
				userProject.setStatus("1");
				userProject.setIsNow("1");
				userProject.setCreateTime(new Date());
				userProject.setCreateBy(invite.getInviterId());
				userProjectBiz.insertSelective(userProject);
				entity.setIsAuth("1");
			}
			try{
				qrProvideFegin.generateFormalPassQr(userId);
			}catch(Exception e){
				log.error("生成二维码报错！报错:{}"+e);
			}
		}
		super.insertSelective(entity);

	}

	public UserVo getUserNameById(String userId) {
		return clientUserMapper.getUserNameById(userId);
	}

	/**
	 * 根据用户ID获取客户端或员工端用户信息
	 * @param userId
	 * @param clientType c=客户端，s=员工端
	 * @return
	 */
	public UserVo getUserNameById(String userId,String clientType){
		if(StringUtils.isNotEmpty(clientType)){
			if("s".equals(clientType.toLowerCase())){
				return baseAppServerUserMapper.getUserNameById(userId);
			}
		}
		UserVo userVo = clientUserMapper.getUserNameById(userId);
		if(userVo==null){
			userVo = baseAppServerUserMapper.getUserNameById(userId);
		}
		if(userVo==null){
			userVo = new UserVo();
		}
		return userVo;
	}

	public ObjectRestResponse<CurrentUserInfosVo> getCurrentUserInfos() {
		CurrentUserInfosVo currentUserInfosVo = userProjectBiz.getCurrentUserInfos();
		ObjectRestResponse<CurrentUserInfosVo> restResponse = new ObjectRestResponse<>();
		restResponse.setData(currentUserInfosVo);
		return restResponse;
	}

	public ObjectRestResponse retrievePassword(RetrievePasswordVo retrievePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isMobile(retrievePasswordVo.getMobilePhone())){
			result = toolFegin.checkCode(retrievePasswordVo.getMobilePhone(),retrievePasswordVo.getVolidCode());
			if(200 == result.getStatus()){
				BaseAppClientUser appClientUser = new BaseAppClientUser();
				appClientUser.setIsDeleted("1");
				appClientUser.setStatus("1");
				appClientUser.setMobilePhone(retrievePasswordVo.getMobilePhone());
				appClientUser = this.selectOne(appClientUser);
				if (null != appClientUser && !StringUtils.isEmpty(appClientUser.getId())){
					String password = encoder.encode(retrievePasswordVo.getNewPassword());
					appClientUser.setPassword(password);
					appClientUser.setUpdTime(new Date());
					this.updateSelectiveById(appClientUser);
					result.setMessage("重置密码成功！");
					return result;
				}else {
					result.setStatus(506);
					return result.data("账号不存在！");
				}
			}else {
				return result;
			}
		}else {
			result.setStatus(505);
			return result.data("手机号错误！");
		}
	}

	public ObjectRestResponse updateUserInfo(UpdateInfo updateInfo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isNotEmpty(updateInfo.getProfilePhoto())){
			ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(updateInfo.getProfilePhoto(), DocPathConstant.PROPERTY);
			if(restResponse.getStatus()==200){
				updateInfo.setProfilePhoto(restResponse.getData()==null ? "" : (String)restResponse.getData());
			}
		}
		BaseAppClientUser appClientUser = new BaseAppClientUser();
		BeanUtils.copyProperties(updateInfo,appClientUser);
		appClientUser.setId(updateInfo.getUserId());
		appClientUser.setUpdTime(new Date());
		this.updateSelectiveById(appClientUser);
		result.setMessage("更新成功！");
		return result;
	}
}