package org.ashe.alpha.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ashe.alpha.auth.domain.annotation.Auth;
import org.ashe.alpha.auth.domain.dto.EditDTO;
import org.ashe.alpha.auth.domain.dto.RegisterDTO;
import org.ashe.alpha.auth.domain.dto.UserDTO;
import org.ashe.alpha.auth.domain.vo.UserVO;
import org.ashe.alpha.base.resp.RespBody;
import org.ashe.alpha.auth.service.OauthUserService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 权限接口层
 * 需要登录甚至另需一定权限才能访问的接口
 */
@RestController
@EnableResourceServer // 标识这是一个资源服务器，需要授权认证
@RequestMapping("/auth")
public class PermissionController {

    @Resource
    private OauthUserService oauthUserService;

    /**
     * 查询
     */
    @GetMapping("/list")
    public RespBody<IPage<UserVO>> list(@Valid UserDTO dto) {
        return RespBody.ok(oauthUserService.listUser(dto));
    }

    /**
     * 修改，记日志
     */
    @PostMapping("/edit")
    @Auth // 具有某种权限才可访问
    public RespBody<Void> edit(@RequestBody @Valid EditDTO dto, HttpServletRequest request) {
        return oauthUserService.edit(dto, request);
    }

    /**
     * 删除，记日志
     */
    @PostMapping("/delete")
    @Auth // 具有某种权限才可访问
    public RespBody<Void> delete(@RequestBody @Valid RegisterDTO dto, HttpServletRequest request) {
        return oauthUserService.delete(dto, request);
    }
}
