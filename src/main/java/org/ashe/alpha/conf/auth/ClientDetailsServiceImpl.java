package org.ashe.alpha.conf.auth;

import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.domain.model.OauthClientDetails;
import org.ashe.alpha.service.OauthClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * ClientDetailsService实例
 * 以数据库的ClientDetails为鉴权依据
 * AuthenticationManager鉴权所用
 */
@Component
@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private OauthClientDetailsService oauthClientDetailsService;


    /*clients
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
                .redirectUris("https://www.baidu.com");*/
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("进入mysql查询客户端");
        // 从数据库查询客户端信息
        OauthClientDetails dbObject = oauthClientDetailsService.getById(clientId);
        if (ObjectUtils.isEmpty(dbObject)) {
            throw new ClientRegistrationException("客户端未注册");
        }
        return new BaseClientDetails(
                dbObject.getResourceIds(),
                dbObject.getScope(),
                dbObject.getAuthorizedGrantTypes(),
                dbObject.getAuthorities(),
                dbObject.getWebServerRedirectUri());
    }

}
