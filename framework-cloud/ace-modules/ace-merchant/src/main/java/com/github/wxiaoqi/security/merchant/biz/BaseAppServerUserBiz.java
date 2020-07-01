package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.FileListVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.merchant.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.merchant.fegin.ToolFegin;
import com.github.wxiaoqi.security.merchant.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.merchant.response.MerchantObjectResponse;
import com.github.wxiaoqi.security.merchant.vo.user.ChangePasswordVo;
import com.github.wxiaoqi.security.merchant.vo.user.ResetPasswordVO;
import com.github.wxiaoqi.security.merchant.vo.user.ServerUserVo;
import com.github.wxiaoqi.security.merchant.vo.user.UpdateInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * app服务端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
public class BaseAppServerUserBiz extends BusinessBiz<BaseAppServerUserMapper,BaseAppServerUser> {

	@Autowired
	private ToolFegin toolFegin;

    /**
     * 发送验证码
     */
	public ObjectRestResponse sendCode(String mobilePhone){
	    if(StringUtils.isEmpty(mobilePhone)){
            return MerchantObjectResponse.error(ResponseCodeEnum.PARAMETER_EXCEPTION);
        }
		return toolFegin.sendMsg(mobilePhone,4,60,"1",null,null,"FORGET_PASSWORD",null);
	}

	/**
	 * 重置密码
	 * @param resetPasswordVO
	 * @return
	 */
	public ObjectRestResponse resetPassword(ResetPasswordVO resetPasswordVO) {
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setEnableStatus("1");
		appServerUser.setStatus("1");
		appServerUser.setMobilePhone(resetPasswordVO.getMobilePhone());
		appServerUser = this.selectOne(appServerUser);
		if(ObjectUtils.isEmpty(appServerUser)){
			return MerchantObjectResponse.error(ResponseCodeEnum.USER_INFO_ERROR);
		}
		if (resetPasswordVO.getNewPassword().equals(appServerUser.getPassword())) {
			return MerchantObjectResponse.error(ResponseCodeEnum.NEW_OLD_PASSWORD_ERROR);
		}
		if(StringUtils.isMobile(resetPasswordVO.getMobilePhone())){
			result = toolFegin.checkCode(resetPasswordVO.getMobilePhone(),resetPasswordVO.getVolidCode());
			if(200 == result.getStatus()){
				return updateNewPassword(resetPasswordVO.getMobilePhone(),resetPasswordVO.getNewPassword(),appServerUser);
			}
			return result;
		}else {
			result.setStatus(505);
			return result.data("手机号错误！");
		}
	}

	/**
	 * 登录时获取用户信息
	 * @param mobile
	 * @return
	 */
	public BaseAppServerUser getUserByMobile(String mobile) {
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setMobilePhone(mobile);
		appServerUser.setStatus("1");
		return this.selectOne(appServerUser);
	}


	/**
	 * 修改密码
	 */
	public ObjectRestResponse changePassword(ChangePasswordVo changePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		BaseAppServerUser appServerUser = this.selectById(BaseContextHandler.getUserID());
		if (changePasswordVo.getNewPassword().equals(appServerUser.getPassword())) {
			return MerchantObjectResponse.error(ResponseCodeEnum.NEW_OLD_PASSWORD_ERROR);
		}
		if (changePasswordVo.getOldPassword().equals(appServerUser.getPassword())) {
			appServerUser.setPassword(changePasswordVo.getNewPassword());
			this.updateSelectiveById(appServerUser);
			return result;
		}
		return MerchantObjectResponse.error(ResponseCodeEnum.OLD_PASSWORD_ERROR);
	}

	// 修改密码
	private ObjectRestResponse updateNewPassword(String mobilePhone,String newPassword,BaseAppServerUser appServerUser){
		ObjectRestResponse result = new ObjectRestResponse();
		if (null != appServerUser && !StringUtils.isEmpty(appServerUser.getId())){
			appServerUser.setPassword(newPassword);
			appServerUser.setModifyTime(new Date());
			appServerUser.setModifyBy(appServerUser.getId());
			this.updateSelectiveById(appServerUser);
			result.setMessage("重置密码成功！");
			return result;
		}
		result.setStatus(506);
		result.setMessage("账号不存在！");
		return result;
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	public ObjectRestResponse getInfo(){
		ObjectRestResponse result = new ObjectRestResponse();
		String userId = BaseContextHandler.getUserID();
		if(StringUtils.isEmpty(userId)){
			return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
		}
		BaseAppServerUser serverUser = this.getUserById(BaseContextHandler.getUserID());
		String companyName = this.mapper.selectCompanyNameById(BaseContextHandler.getUserID());
		ServerUserVo userVo = new ServerUserVo();
		BeanUtils.copyProperties(serverUser,userVo);
		userVo.setCompanyName(companyName);
		int num = this.mapper.isHasSupervision(BaseContextHandler.getUserID());
		if(num>0){
			userVo.setIsSupervision("1");
		}else {
			userVo.setIsSupervision("0");
		}
		if(StringUtils.isEmpty(serverUser.getProfilePhoto())){
			serverUser.setProfilePhoto("");
		}
		result.setData(userVo);
		return result;
	}

	/**
	 * 修改用户信息
	 * @return
	 */
	public ObjectRestResponse updateInfo(UpdateInfo updateInfo){
		ObjectRestResponse result = new ObjectRestResponse();
		String userId = BaseContextHandler.getUserID();
		if(StringUtils.isEmpty(userId)){
			return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
		}
		String profilePhoto = null;
		Map<String,String> uploadPhono = new HashMap<>();
		uploadPhono.put("filebinary",updateInfo.getProfilePhoto());
		List<Map<String,String>> uploadPhonoList = new ArrayList<>();
		uploadPhonoList.add(uploadPhono);
		FileListVo fileListVo = new FileListVo();
		fileListVo.setFileList(uploadPhonoList);
		ObjectRestResponse restResponse = toolFegin.uploadBase64Images(fileListVo);
		if(restResponse.getStatus()==200){
			profilePhoto = restResponse.getData()==null ? "" : (String)restResponse.getData();
		}
		BaseAppServerUser serverUser = this.getUserById(BaseContextHandler.getUserID());
		if(StringUtils.isEmpty(profilePhoto)){
			return MerchantObjectResponse.error(ResponseCodeEnum.USER_PHOTO_UPLOAD_ERROR);
		}
		serverUser.setProfilePhoto(profilePhoto);
		this.updateById(serverUser);
		result.setData(profilePhoto);
		return result;
	}

	private BaseAppServerUser getUserById(String userId) {
		BaseAppServerUser appServerUser = new BaseAppServerUser();
		appServerUser.setId(userId);
		return this.selectOne(appServerUser);
	}

}