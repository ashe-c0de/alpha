package org.ashe.alpha.auth.conf;

import org.ashe.alpha.auth.domain.model.OauthUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    /**
     * 在默认jwtToken中添加用户信息
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        /*
            设置过期时间      单位毫秒值
            1000 * 3600 * 24  --    24小时
            1000 * 60 * 2 —— 2分钟
         */
        defaultOAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2));
        /*
            由于授权认证的方式不同，获取认证主体的类型也不同
         */
        if (authentication.getPrincipal() instanceof OauthUser) {
            // 获取被认证的主体身份——用户
            OauthUser oauthUser = (OauthUser) authentication.getPrincipal();
            // 将用户工号添加至jwtToken
            defaultOAuth2AccessToken.getAdditionalInformation().put("u_code", oauthUser.getUCode());
        }
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }
}

