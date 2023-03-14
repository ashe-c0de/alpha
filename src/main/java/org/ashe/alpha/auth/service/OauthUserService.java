package org.ashe.alpha.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ashe.alpha.auth.domain.dto.EditDTO;
import org.ashe.alpha.auth.domain.dto.RegisterDTO;
import org.ashe.alpha.auth.domain.dto.UserDTO;
import org.ashe.alpha.auth.domain.model.OauthUser;
import org.ashe.alpha.auth.domain.vo.UserVO;
import org.ashe.alpha.base.resp.RespBody;

import javax.servlet.http.HttpServletRequest;

public interface OauthUserService extends IService<OauthUser> {
    RespBody<Void> register(RegisterDTO dto, HttpServletRequest request);

    OauthUser getUserByAccount(String account);

    RespBody<Void> delete(RegisterDTO dto, HttpServletRequest request);

    RespBody<Void> edit(EditDTO dto, HttpServletRequest request);

    IPage<UserVO> listUser(UserDTO dto);
}
