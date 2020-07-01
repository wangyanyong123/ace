package com.github.wxiaoqi.security.app.oauth.provider;

import com.github.wxiaoqi.security.app.oauth.bean.OauthUser;
import com.github.wxiaoqi.security.app.oauth.service.OauthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: guohao
 * @create: 2020-04-29 11:39
 **/
@Component
public class NoPasswordEmcoderAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private OauthUserDetailsService userDetailsService;

    /**
     * 添加自定义身份认证检查
     *
     * @param userDetails                         用户信息
     * @param authentication 用户名密码令牌
     * @throws AuthenticationException 异常
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //校验密码
            String presentedPassword = authentication.getCredentials().toString();
            if (!presentedPassword.equals( userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }


    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(userName);
    }

}
