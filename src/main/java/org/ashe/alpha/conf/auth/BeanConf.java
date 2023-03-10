package org.ashe.alpha.conf.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Component;

/**
 * 集中注入授权服务需要注入的bean
 */
@Component
public class BeanConf {

    /**
     * 注入普通令牌
     */
    @Bean
    public TokenStore tokenStore(){
        // 基于内存的普通令牌
        return new InMemoryTokenStore();
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
