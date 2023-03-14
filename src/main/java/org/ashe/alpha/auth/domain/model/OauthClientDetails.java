package org.ashe.alpha.auth.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@TableName("client_details")
public class OauthClientDetails implements ClientDetails, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     *     AUTO （自动增长策略）
     *     Input（自定义输入策略）
     *     ASSIGN_ID（雪花算法）
     *     ASSIGN_UUID（不含中划线的UUID）
     *     NONE（无状态）
     */
    @TableId(type = IdType.INPUT)
    private String clientId;

    /**
     * 接入资源列表
     */
    private String resourceIds;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 允许授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 重定向URI
     */
    private String webServerRedirectUri;

    /**
     * 权限值
     */
    private String authorities;

    /**
     * accessToken有效时间值(单位:秒)
     */
    private String accessTokenValidity;

    /**
     * refreshToken有效时间值(单位:秒)
     */
    private String refreshTokenValidity;

    /**
     * 预留字段
     */
    private String additionalInformation;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否可用,默认值为’0’(即已存档)'
     */
    private Integer archived;

    /**
     * 是否可信任,默认为’0’(即受信任的)
     */
    private Integer trusted;

    /**
     * 自动批准授权，默认false
     */
    private String autoApprove;

    private Set<String> composeSet(String str) {
        return new HashSet<>(Arrays.asList(str.split(",")));
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return composeSet(this.resourceIds);
    }

    /**
     * Whether a secret is required to authenticate this client.
     *
     * @return Whether a secret is required to authenticate this client.
     */
    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return composeSet(scope);
    }


    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return composeSet(this.authorizedGrantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return composeSet(this.webServerRedirectUri);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (authorities != null && authorities.trim().length() > 0) ?
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities) : Collections.emptyList();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return Integer.valueOf(this.accessTokenValidity);
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return Integer.valueOf(this.refreshTokenValidity);
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return Boolean.parseBoolean(this.autoApprove);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }
}
