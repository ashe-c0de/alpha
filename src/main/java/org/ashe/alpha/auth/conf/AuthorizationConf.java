package org.ashe.alpha.auth.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 授权服务器自定义配置
 */
@Configuration
public class AuthorizationConf extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private AuthorizationCodeServices authorizationCodeServices;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * configure(ClientDetailsServiceConfigurer clients)
     * 通过此方法，已经配置了————客户端信息对应实例
     */
    private final ClientDetailsService clientDetailsService;

    public AuthorizationConf(@Qualifier(value = "clientDetailsServiceImpl") ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }


    /**
     * 配置令牌（token）端点的安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                /*
                    /oauth/token_key    资源服务验证token接口放行
                    资源服务拿到token后，根据已知的算法和签名密钥去校验token
                    不必调用资源服务接口来验证token
                 */
                .tokenKeyAccess("permitAll()")
                /*
                    oauth/check_token    授权服务验证token接口放行
                 */
                .checkTokenAccess("permitAll()")
                /*
                     创建ClientCredentialsTokenEndpointFilter对请求auth/token拦截
                     并获取client_id和client_secret进行身份认证
                 */
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置客户端信息服务
     * clients
     *                 // 内存中配置客户端
     *                 .inMemory()
     *                 .withClient("c1")
     *                 .secret(passwordEncoder.encode("s1"))
     *                 // 可设置对应工号 ？
     *                 .resourceIds("app")
     *                 .authorizedGrantTypes("authorization_code",
     *                         "password",
     *                         "client_credentials",
     *                         "implicit",
     *                         "refresh_token")
     *                 .scopes("all")
     *                 .autoApprove(false)
     *                 .redirectUris("<a href="https://www.baidu.com">...</a>");
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
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
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                // 使用加入了自定义认证的tokenGranter——短信验证码
                .tokenGranter(tokenGranter(endpoints));
    }

    /**
     * 授权服务器token管理的配置
     */
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices(); // 使用默认的token服务
        services.setClientDetailsService(clientDetailsService); // 配置客户端信息--自定义基于内存
        services.setSupportRefreshToken(true); // 允许token自动刷新
        services.setTokenStore(tokenStore); // 设置令牌储存方式--此时注入的tokenStore是JWT令牌
        services.setTokenEnhancer(jwtAccessTokenConverter); // token增强
        services.setAccessTokenValiditySeconds(7200); // token有效期2小时
        services.setRefreshTokenValiditySeconds(259200); // 刷新token有效期3天
        return services;
    }

    /**
     * 添加自定义授权类型
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {

        // endpoints.getTokenGranter() 获取SpringSecurity OAuth2.0 现有的授权类型
        List<TokenGranter> granterList = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));

        // 构建短信验证授权类型
        SmsCodeTokenGranter smsCodeTokenGranter = new SmsCodeTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        // 向集合中添加短信授权类型
        granterList.add(smsCodeTokenGranter);

        // 返回所有类型
        return new CompositeTokenGranter(granterList);
    }
}
