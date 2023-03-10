package org.ashe.alpha.conf.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * 授权服务器自定义配置
 */
@Configuration
public class AuthorizationConf extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private AuthorizationCodeServices authorizationCodeServices;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private TokenStore tokenStore;

    /**
     * configure(ClientDetailsServiceConfigurer clients)
     * 通过重写此方法，已经配置了————客户端信息对应实例
     */
    @Resource
    private ClientDetailsService clientDetailsService;


    /**
     * 配置令牌（token）端点的安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()") // ouath/token_key放行
                .checkTokenAccess("permitAll()") // oauth/check_token放行
                .allowFormAuthenticationForClients(); // 表单认证，申请令牌
    }

    /**
     * 配置客户端信息服务
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 内存中配置客户端
                .inMemory()
                .withClient("c1")
                .secret(passwordEncoder.encode("s1"))
                .resourceIds("app")
                .authorizedGrantTypes("authorization_code",
                        "password",
                        "client_credentials",
                        "implicit",
                        "refresh_token")
                .scopes("all")
                .autoApprove(false)
                .redirectUris("https://www.baidu.com");
    }

    /**
     * token端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 使用从Security继承的————认证管理
                .authenticationManager(authenticationManager)
                // 使用数据库的用户——已注入实例UserDetailsServiceImpl
                .userDetailsService(userDetailsService)
                // 使用基于内存的授权码服务实例
                .authorizationCodeServices(authorizationCodeServices)
                // 使用自定义的token管理服务
                .tokenServices(tokenServices())
                // 端点验证的请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 配置授权服务器token管理的设置
     */
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService); // 配置自定义的客户端信息--基于内存
        services.setSupportRefreshToken(true); // 允许token自动刷新
        services.setTokenStore(tokenStore); // 设置令牌储存方式--此时注入的tokenStore是基于内存的
        services.setAccessTokenValiditySeconds(7200); // token有效期2小时
        services.setRefreshTokenValiditySeconds(259200); // 刷新token有效期3天
        return services;
    }
}
