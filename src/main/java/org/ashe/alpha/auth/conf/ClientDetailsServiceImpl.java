package org.ashe.alpha.auth.conf;

import org.ashe.alpha.auth.domain.model.OauthClientDetails;
import org.ashe.alpha.auth.service.OauthClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Component
//@Primary
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private OauthClientDetailsService oauthClientDetailsService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails dbObject = oauthClientDetailsService.getById(clientId);
        if (ObjectUtils.isEmpty(dbObject)) {
            throw new ClientRegistrationException("客户端信息不存在");
        }
        return dbObject;
    }
}
