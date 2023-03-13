package org.ashe.alpha.conf.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 集中注入授权服务需要注入的bean
 */
@Configuration
public class BeanConf {

    private static final String SIGN_KEY = "Little pigs, little pigs, let me come in.";

    /**
     * 注入JWT令牌
     * <>p</>
     * 基于内存的普通令牌
     * return new InMemoryTokenStore();
     */
    @Bean
    public TokenStore tokenStore(){
        // jwt令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 注入jwt格式token转换器
     * 按规定私钥转换
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * 注入BCrypt密码编码器
     */
    @Bean
    public PasswordEncoder injectPE() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入基于内存的授权码服务实例至IOC
     */
    @Bean
    public AuthorizationCodeServices injectAuthorizationCode(){
        return new InMemoryAuthorizationCodeServices();
    }

}
