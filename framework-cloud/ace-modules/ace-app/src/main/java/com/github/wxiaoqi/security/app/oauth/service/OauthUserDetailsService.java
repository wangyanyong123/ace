package com.github.wxiaoqi.security.app.oauth.service;

import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.app.oauth.bean.OauthUser;
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
    private BaseAppClientUserBiz clientUserBiz;
    @Autowired
    private BaseAppServerUserBiz serverUserBiz;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        if (StringUtils.isBlank(mobile)) {
            throw new UsernameNotFoundException("手机号不为空!");
        }
        String[] infos = mobile.split("#");
        if(infos.length == 2){
            if("c".equals(infos[1])){
                BaseAppClientUser clientUser = clientUserBiz.getUserByMobile(infos[0]);
                if (clientUser == null) {
					if(infos[0].length() != 11){
						throw new UsernameNotFoundException("手机号错误!");
					}
					throw new UsernameNotFoundException("手机号不存在!");
                }
                if( "0".equals(clientUser.getIsDeleted()) || "0".equals(clientUser.getStatus())){
                    throw new UsernameNotFoundException("账号被禁用!");
                }
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new OauthUser(clientUser.getId().toString(), clientUser.getId(), clientUser.getPassword(), clientUser.getNickname(), clientUser.getTenantId(),"2",
                        authorities);
            }
            if("s".equals(infos[1])){
                BaseAppServerUser serverUser = serverUserBiz.getUserByMobile(infos[0]);
                if (serverUser == null) {
					if(infos[0].length() != 11){
						throw new UsernameNotFoundException("手机号错误!");
					}
					throw new UsernameNotFoundException("手机号不存在!");
                }
                if( "0".equals(serverUser.getIsActive())){
                    throw new UsernameNotFoundException("账号未激活!");
                }
                if( "0".equals(serverUser.getEnableStatus()) || "0".equals(serverUser.getStatus())){
                    throw new UsernameNotFoundException("账号被禁用!");
                }
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new OauthUser(serverUser.getId().toString(), serverUser.getId(), serverUser.getPassword(), serverUser.getName(), serverUser.getTenantId(),"3",
                        authorities);
            }
        }else if(infos.length < 1) {
            throw new UsernameNotFoundException("手机号不存在!");
        }

       return null;
    }
}
