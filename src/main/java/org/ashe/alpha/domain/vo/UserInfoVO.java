package org.ashe.alpha.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息
 */
@Data
public class UserInfoVO implements Serializable {

    /**
     * 账户
     */
    private String account;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 工号
     */
    private String uCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String mail;

}
