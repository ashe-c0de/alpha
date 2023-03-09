package org.ashe.alpha.controller;

import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.domain.dto.RegisterDTO;
import org.ashe.alpha.domain.vo.resp.RespBody;
import org.ashe.alpha.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
public class HelloController {

    @Resource
    private UserService userService;

    @GetMapping("hello")
    public String hello() {
        return "hello spring-security";
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public RespBody<Void> register(@RequestBody @Valid RegisterDTO dto, HttpServletRequest request) {
        return userService.register(dto, request);
    }
}
