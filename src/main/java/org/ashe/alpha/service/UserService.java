package org.ashe.alpha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ashe.alpha.domain.dto.RegisterDTO;
import org.ashe.alpha.domain.model.User;
import org.ashe.alpha.domain.vo.resp.RespBody;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    RespBody<Void> register(RegisterDTO dto, HttpServletRequest request);

    User getUserByAccount(String account);
}
