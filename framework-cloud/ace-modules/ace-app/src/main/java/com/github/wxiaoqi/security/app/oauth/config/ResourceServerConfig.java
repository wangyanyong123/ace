package com.github.wxiaoqi.security.app.oauth.config;

import com.github.wxiaoqi.security.app.oauth.AuthExceptionEntryPoint;
import com.github.wxiaoqi.security.app.oauth.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ace
 * @create 2018/3/21.
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private AuthIgnoreConfig authIgnoreConfig;

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
				.accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/appUser/register")
//                .permitAll().and()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();
//        String[] urls = authIgnoreConfig.getIgnoreUrls().stream().distinct().toArray(String[]::new);
//        http .authorizeRequests().antMatchers(urls).permitAll()
//                .anyRequest().authenticated();

        http.authorizeRequests().anyRequest().permitAll();

    }
}