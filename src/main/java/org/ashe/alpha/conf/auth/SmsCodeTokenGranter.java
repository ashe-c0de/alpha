package org.ashe.alpha.conf.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义短信验证码授权
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {

    /**
     * 申明一个匿名的授权token，供所有验证码登录的用户使用
     */
    private static final AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken("s1", "app", AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));

    /**
     * 自定义授权类型————sms_code
     */
    private static final String GRANT_TYPE = "sms_code";

    public SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                               ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                                  ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                           TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        // 客户端提交的手机号码
        String tel = parameters.get("tel");
        // 客户端提交的验证码
        String smsCode = parameters.get("code");
        if (ObjectUtils.isEmpty(tel) || ObjectUtils.isEmpty(smsCode)) {
            throw new InvalidGrantException("验证码错误");
        }
        // TODO 在Redis中查询手机号对应的验证码即可
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, anonymousAuthenticationToken);
    }
}
