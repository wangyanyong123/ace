package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChangePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.RetrievePasswordVo;
import com.github.wxiaoqi.security.app.vo.user.UpdateInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * app服务端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
public class BaseAppServerUserBiz extends BusinessBiz<BaseAppServerUserMapper,BaseAppServerUser> {

	private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

	@Autowired
	private ToolFegin toolFegin;

	@Autowired
	private BaseAppServerUserMapper baseAppServerUserMapper;
	/**
	 * 修改密码
	 */
	public ObjectRestResponse changePassword(ChangePasswordVo changePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppServerUser appServerUser = this.getUserById(BaseContextHandler.getUserID());
		if (encoder.matches(changePasswordVo.getOldPassword(), appServerUser.getPassword())) {
			appServerUser.setPassword(encoder.encode(changePasswordVo.getNewPassword()));
			this.updateSelectiveById(appServerUser);
			result.setMessage("修改成功！");
			return result;
		}
		result.setStatus(504);
		result.setMessage("旧密码错误！");
		return result;
	}

	public BaseAppServerUser getUserByMobile(String mobile) {
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setMobilePhone(mobile);
		appServerUser.setStatus("1");
		return this.selectOne(appServerUser);
	}

	public void activateAccount(BaseAppServerUser serverUser) {
		String password = encoder.encode(serverUser.getPassword());
		serverUser.setPassword(password);
		serverUser.setModifyTime(new Date());
		serverUser.setIsActive("1");
//		serverUser.setTenantId(BaseContextHandler.getTenantID());
		serverUser.setModifyBy(serverUser.getId());
		this.updateSelectiveById(serverUser);
	}

	public BaseAppServerUser getUserById(String userId) {
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setId(userId);
		return this.selectOne(appServerUser);
	}

	public ObjectRestResponse retrievePassword(RetrievePasswordVo retrievePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isMobile(retrievePasswordVo.getMobilePhone())){
			result = toolFegin.checkCode(retrievePasswordVo.getMobilePhone(),retrievePasswordVo.getVolidCode());
			if(200 == result.getStatus()){
				return updateNewPassword(retrievePasswordVo.getMobilePhone(),retrievePasswordVo.getNewPassword(),true);
			}else {
				return result;
			}
		}else {
			result.setStatus(505);
			return result.data("手机号错误！");
		}
	}

	public ObjectRestResponse updateNewPassword(String mobilePhone,String newPassword,boolean isNeedEncode){
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setIsActive("1");
		appServerUser.setStatus("1");
		appServerUser.setMobilePhone(mobilePhone);
		appServerUser = this.selectOne(appServerUser);
		if (null != appServerUser && !StringUtils.isEmpty(appServerUser.getId())){
			String password = newPassword;
			if(isNeedEncode){
				password = encoder.encode(newPassword);
			}
			appServerUser.setPassword(password);
			appServerUser.setModifyTime(new Date());
			appServerUser.setModifyBy(appServerUser.getId());
			this.updateSelectiveById(appServerUser);
			result.setMessage("重置密码成功！");
			return result;
		}else {
			result.setStatus(506);
			return result.data("账号不存在！");
		}
	}

	public ObjectRestResponse updateUserInfo(UpdateInfo updateInfo) {
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		BeanUtils.copyProperties(updateInfo,appServerUser);
		appServerUser.setId(updateInfo.getUserId());
		appServerUser.setModifyTime(new Date());
		appServerUser.setModifyBy(BaseContextHandler.getUserID());
		this.updateSelectiveById(appServerUser);
		result.setMessage("更新成功！");
		return result;
	}

	public String getProjectId(String userID) {
		return baseAppServerUserMapper.getProjectId(userID);
	}

	public BaseAppServerUser getUserByUsername(String username) {
		return baseAppServerUserMapper.selectUserByPhone(username);
	}

	public int isHasSupervision(String userID) {
		return baseAppServerUserMapper.isHasSupervision(userID);
	}

	public ObjectRestResponse getServerUserProject() {
		ObjectRestResponse response = new ObjectRestResponse();
		BaseAppServerUser userInfo = baseAppServerUserMapper.getUserInfo(BaseContextHandler.getUserID());
		if (userInfo.getIsBusiness().equals("1")) {
//			List<ProjectInfoVo> serverUserProject = baseAppServerUserMapper.getServerUserProject(BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
			response.setData(new ArrayList<>());
			return response;
		}
		List<ProjectInfoVo> serverUserProject = baseAppServerUserMapper.getServerUserProject(BaseContextHandler.getUserID(),null);
		if (serverUserProject == null || serverUserProject.size() == 0) {
			serverUserProject = new ArrayList<>();
		}
		response.setData(serverUserProject);
		return response;
	}
}