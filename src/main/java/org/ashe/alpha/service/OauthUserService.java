package org.ashe.alpha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ashe.alpha.domain.dto.RegisterDTO;
import org.ashe.alpha.domain.model.OauthUser;
import org.ashe.alpha.domain.vo.resp.RespBody;

import javax.servlet.http.HttpServletRequest;

public interface OauthUserService extends IService<OauthUser> {
    RespBody<Void> register(RegisterDTO dto, HttpServletRequest request);

    OauthUser getUserByAccount(String account);
}
