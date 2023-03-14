package org.ashe.alpha.auth.conf;

import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.auth.domain.model.OauthUser;
import org.ashe.alpha.auth.service.OauthUserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * 实现UserDetailsService接口的实例
 * 以数据库的用户为鉴权依据
 * AuthenticationManager鉴权所用
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private OauthUserService oauthUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从mysql查询
        OauthUser oauthUser = oauthUserService.getUserByAccount(username);
        log.info("进入mysql查询用户");
        if (ObjectUtils.isEmpty(oauthUser)) {
            throw new UsernameNotFoundException("用户或密码错误");
        }
        return new User(oauthUser.getAccount(), oauthUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
    }

}
