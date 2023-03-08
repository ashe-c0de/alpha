package org.ashe.alpha.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id; // mybatis-plus主键默认生成策略即雪花算法

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;

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

    /**
     * 微信
     */
    private String wechat;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 删除
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
