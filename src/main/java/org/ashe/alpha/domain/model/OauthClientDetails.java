package org.ashe.alpha.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@TableName("client_details")
public class OauthClientDetails implements Serializable {
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

}
