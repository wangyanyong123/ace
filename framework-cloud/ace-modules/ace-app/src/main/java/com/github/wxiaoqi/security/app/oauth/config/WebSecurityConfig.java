package com.github.wxiaoqi.security.app.oauth.config;

import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BizUserWechatBiz;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.oauth.CustomAuthenticationFailureHandler;
import com.github.wxiaoqi.security.app.oauth.service.OauthUserDetailsService;
import com.github.wxiaoqi.security.app.oauth.TokenAuthenticationSuccessHandler;
import com.github.wxiaoqi.security.app.oauth.filter.WechatSmsCodeAuthenticationFilter;
import com.github.wxiaoqi.security.app.oauth.provider.NoPasswordEmcoderAuthenticationProvider;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by ace on 2017/8/11.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BaseAppClientUserBiz appClientUserBiz;
    @Autowired
    private BizUserWechatBiz bizUserWechatBiz;
    @Autowired
    private TokenAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private ToolFegin toolFegin;

    @Bean
    public WechatSmsCodeAuthenticationFilter smsCodeAuthenticationFilter() throws Exception {
        WechatSmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new WechatSmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setBizUserWechatBiz(bizUserWechatBiz);
        smsCodeAuthenticationFilter.setAppClientUserBiz(appClientUserBiz);
        smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        smsCodeAuthenticationFilter.setToolFegin(toolFegin);
        return  smsCodeAuthenticationFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers("/login", "/oauth/authorize") //允许访问首页
//                .and()
//                .authorizeRequests()   // 验证所有请求
//                .anyRequest().authenticated()
//                .and()
//                .authorizeRequests().antMatchers("/appUser/register", "/admin/**")
//                .permitAll()
//                .and().csrf().disable() // 禁用 CSRF 跨站伪造请求，便于测试
//                .formLogin().loginPage("/login").permitAll();
        http
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
        http.addFilterBefore(smsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/admin/**");
    }

    @Autowired
    private OauthUserDetailsService oauthUserDetailsService;
    @Autowired
    private NoPasswordEmcoderAuthenticationProvider smsCodeAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(smsCodeAuthenticationProvider);
        auth.userDetailsService(oauthUserDetailsService).passwordEncoder(new Sha256PasswordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}