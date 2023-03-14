package org.ashe.alpha.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.auth.domain.dto.RegisterDTO;
import org.ashe.alpha.base.resp.RespBody;
import org.ashe.alpha.auth.service.OauthUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/access")
public class AccessController {

    @Resource
    private OauthUserService oauthUserService;

    @GetMapping("hello")
    public String hello() {
        return "hello spring-security";
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public RespBody<Void> register(@RequestBody @Valid RegisterDTO dto, HttpServletRequest request) {
        return oauthUserService.register(dto, request);
    }
}
