package com.github.wxiaoqi.security.merchant.oauth.config;

import com.github.ag.core.constants.CommonConstants;
import com.github.ag.core.util.RsaKeyHelper;
import com.github.wxiaoqi.security.merchant.biz.BizUserHouseBiz;
import com.github.wxiaoqi.security.merchant.entity.BizUserHouse;
import com.github.wxiaoqi.security.merchant.fegin.LogService;
import com.github.wxiaoqi.security.merchant.mapper.BizUserHouseMapper;
import com.github.wxiaoqi.security.merchant.oauth.bean.OauthUser;
import com.github.wxiaoqi.security.merchant.vo.userhouse.out.HouseInfoVo;
import com.github.wxiaoqi.security.merchant.vo.userhouse.out.UserHouseVo;
import com.github.wxiaoqi.security.common.constant.RedisKeyConstants;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.ToolUtil;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.ObjectUtils;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private AuthServerConfig authServerConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RsaKeyHelper rsaKeyHelper;

	@Autowired
	private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Autowired
    private LogService logService;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private BizUserHouseBiz userHouseBiz;

    @Autowired
    private BizUserHouseMapper userHouseMapper;

    @Bean
    public JwtTokenStore jwtTokenStore() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    //管理令牌（Managing Token）
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security.allowFormAuthenticationForClients(); // 允许客户端发送表单来进行权限认证来获取令牌
//                .passwordEncoder(new Sha256PasswordEncoder())
                //允许所有资源服务器访问公钥端点（/oauth/token_key）
                //只允许验证用户访问令牌解析端点（/oauth/check_token）
//                .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        //需要更换成加密模式
//        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    //配置授权类型（Grant Types） 配置授权端点 URL（Endpoint URLs）
    //告诉Spring Security Token的生成方式
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints
                .authenticationManager(auth)
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(accessTokenConverter())
				.exceptionTranslator(customWebResponseExceptionTranslator)
        ;
    }

    //配置客户端详情
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //需要更换成加密模式
        clients.inMemory() // 使用in-memory存储客户端信息
                .withClient(authServerConfig.getClientId()) // client_id
                .secret(authServerConfig.getClientSecret())  // client_secret
                .authorizedGrantTypes(authServerConfig.getGrantTypes().split(",")) // 该client允许的授权类型
                // 访问token过期时长为一周过期
                .accessTokenValiditySeconds(7 * 24 * 60 * 60)
                // 刷新token过期时长为两个月
                .refreshTokenValiditySeconds(60 * 24 * 60 * 60)
                .scopes(authServerConfig.getScope()); // 允许的授权范围
    }

    //使用同一个密钥来编码 JWT 中的  OAuth2 令牌
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        byte[] pri, pub = null;
        try {
            pri = getKey(RedisKeyConstants.REDIS_USER_PRI_KEY);
            pub = getKey(RedisKeyConstants.REDIS_USER_PUB_KEY);
        } catch (Exception e) {
            log.error("初始化用户认证公钥/密钥异常...", e);
            throw new RuntimeException("redis异常 或 未启动auth 服务,并保证app和auth处于同一个redis集群当中...");
        }
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OauthUser user = (OauthUser) authentication.getUserAuthentication().getPrincipal();
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                Date expireTime = DateTime.now().plusSeconds(accessToken.getExpiresIn()).toDate();
                additionalInformation.put(CommonConstants.JWT_KEY_EXPIRE, expireTime);
                additionalInformation.put(CommonConstants.JWT_KEY_USER_ID, user.getId());
                additionalInformation.put(CommonConstants.JWT_KEY_TENANT_ID, user.getTenantId());
                additionalInformation.put(CommonConstants.JWT_KEY_NAME, user.getName());
                additionalInformation.put("sub", user.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
               addLoginilog(user);


                return enhancedToken;
            }

        };
        accessTokenConverter.setKeyPair(new KeyPair(new RSAPublicKeyImpl(pub), RSAPrivateCrtKeyImpl.newKey(pri)));
        return accessTokenConverter;
    }

    // 获取Redis中的key
    private byte[] getKey(String key) throws IOException {
        return rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(key).toString());
    }

    // 登录日志
    private void addLoginilog(OauthUser user){
        LogInfoVo logInfoVo = new LogInfoVo();
        logInfoVo.setLogType("1");
        logInfoVo.setLogName("登录");
        logInfoVo.setOs("2");
        logInfoVo.setCreateTime(new Date());
        logInfoVo.setCreateBy(user.getId());
        logInfoVo.setUserType(user.getUserType());
//                HouseInfoVo houseInfo = userHouseBiz.getCurrentHouse().getData();
        BizUserHouse userHouse = new BizUserHouse();
        userHouse.setUserId(user.getId());
        userHouse.setIsNow("1");
        userHouse.setStatus("1");
        userHouse.setIsDelete("0");
        userHouse = userHouseBiz.selectOne(userHouse);
        if(!ObjectUtils.isEmpty(userHouse) && StringUtils.isNotEmpty(userHouse.getId())){
            HouseInfoVo houseInfoVo = new HouseInfoVo();
            UserHouseVo userHouseVo = userHouseMapper.getCurrentHouseInfoById(userHouse.getId());
            BeanUtils.copyProperties(userHouseVo, houseInfoVo);
            logInfoVo.setProjectId(houseInfoVo.getProjectId());
        }
        try {
            String ip = ToolUtil.getIpAddr(request);
            logInfoVo.setIp(ip);
            logService.savelog(logInfoVo);
        }catch (Exception e){
        }
    }
}