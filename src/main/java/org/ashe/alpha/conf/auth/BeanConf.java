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
     * 注入普通令牌/JWT令牌
     */
    @Bean
    public TokenStore tokenStore(){
        // 基于内存的普通令牌
//        return new InMemoryTokenStore();
        // jwt令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 自定义私钥加密
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * 向IOC注入BCrypt密码编码器
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
