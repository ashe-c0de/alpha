package org.ashe.alpha.conf.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security自定义配置
 */
@Configuration
// 基于方法的安全策略？
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

    /**
     * 从父类继承的鉴权管理注入至IOC
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf跨域
                .csrf().disable()
                .authorizeHttpRequests()
                // 放行指定的请求
                .antMatchers("/access/**").permitAll()
                // 其他请求都需要认证
                .anyRequest().authenticated().and()
                // 开启表单提交
                .formLogin();
    }
}
