package com.github.wxiaoqi.security.merchant.oauth.service;

import com.github.wxiaoqi.security.merchant.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.merchant.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.merchant.oauth.bean.OauthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ace on 2017/8/11.
 */
@Component
public class OauthUserDetailsService implements UserDetailsService {


    @Autowired
    private BaseAppServerUserBiz serverUserBiz;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        if (StringUtils.isBlank(mobile)) {
            throw new UsernameNotFoundException("手机号不为空!");
        }

        BaseAppServerUser serverUser = serverUserBiz.getUserByMobile(mobile);
        if (serverUser == null) {
            if(mobile.length() != 11){
                throw new UsernameNotFoundException("手机号错误!");
            }
            throw new UsernameNotFoundException("手机号不存在!");
        }
//        if( "0".equals(serverUser.getIsActive())){
//            throw new UsernameNotFoundException("账号未激活!");
//        }
        if( "0".equals(serverUser.getEnableStatus()) || "0".equals(serverUser.getStatus())){
            throw new UsernameNotFoundException("账号被禁用!");
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new OauthUser(serverUser.getId().toString(), serverUser.getId(), serverUser.getPassword(), serverUser.getName(), serverUser.getTenantId(),"3",
                authorities);
    }
}
