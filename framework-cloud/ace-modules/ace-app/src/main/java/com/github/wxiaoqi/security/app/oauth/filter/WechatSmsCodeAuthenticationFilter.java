package com.github.wxiaoqi.security.app.oauth.filter;

import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BizUserWechatBiz;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.oauth.bean.LoginUserSign;
import com.github.wxiaoqi.security.app.oauth.exception.CustomException;
import com.github.wxiaoqi.security.app.util.HttpRequestParamUtil;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.MD5;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 普通用户名，密码登陆
 *
 * @Auther: guohao
 * @Date: 2019/12/18 13:03
 * @Description:
 */
public class WechatSmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private String usernameParameter = "username";
    private String passwordParameter = "smsCode";
    private String openIdParameter = "openId";
    private boolean postOnly = true;

    private BaseAppClientUserBiz appClientUserBiz;

    private BizUserWechatBiz bizUserWechatBiz;

    private ToolFegin toolFegin;

    public static final String defaultFilterProcessesUrl = "/oauth/wechat/bindPhone";

    public WechatSmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String smsCode = this.obtainPassword(request);
            String openId = this.obtainOpenId(request);

            Integer appType = RequestHeaderUtil.getPlatformIntValue(request);
            LoginUserSign loginUserSign = LoginUserSign.buildLoginUserSign(username);

            //校验短信验证码
            ObjectRestResponse restResponse = toolFegin.checkCode(loginUserSign.getMobilePhone(), smsCode);
            if(!restResponse.success()){
                throw new CustomException(restResponse.getMessage());
            }
            BaseAppClientUser appClientUser = this.appClientUserBiz.getUserByMobile(loginUserSign.getMobilePhone());
            if (null == appClientUser) {
                if(LoginUserSign.CLIENT_USER_SIGN.equals(loginUserSign.getUserSign())){
                    appClientUser = doRegister(loginUserSign.getMobilePhone(),appType);
                }else{
                    //商业人员  TODO

                }
            }
            //绑定openId
            restResponse = bizUserWechatBiz.bindUser(appType, appClientUser.getId(), openId);
            if(!restResponse.success()){
                throw new CustomException(restResponse.getMessage());
            }
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, appClientUser.getPassword());
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openIdParameter);
    }

    private BaseAppClientUser doRegister(String mobile,Integer appType){
        BaseAppClientUser appuser = new BaseAppClientUser();
        appuser.setMobilePhone(mobile);
        appuser.setNickname("wx"+mobile);
        appuser.setPassword(MD5.getMD5Code("Sino+"+mobile.substring(7)));
        appuser.setRegistOs(appType.toString());
        appClientUserBiz.insertSelective(appuser);
        return appuser;
    }
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(HttpRequestParamUtil.getParameterMap(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public void setAppClientUserBiz(BaseAppClientUserBiz appClientUserBiz) {
        this.appClientUserBiz = appClientUserBiz;
    }

    public void setBizUserWechatBiz(BizUserWechatBiz bizUserWechatBiz) {
        this.bizUserWechatBiz = bizUserWechatBiz;
    }

    public void setToolFegin(ToolFegin toolFegin) {
        this.toolFegin = toolFegin;
    }

}
