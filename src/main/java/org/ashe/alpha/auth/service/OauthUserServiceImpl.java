package org.ashe.alpha.auth.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.ashe.alpha.auth.domain.constants.Register;
import org.ashe.alpha.auth.domain.dto.EditDTO;
import org.ashe.alpha.auth.domain.dto.RegisterDTO;
import org.ashe.alpha.auth.domain.dto.UserDTO;
import org.ashe.alpha.auth.domain.model.OauthUser;
import org.ashe.alpha.auth.domain.vo.UserVO;
import org.ashe.alpha.auth.mapper.OauthUserMapper;
import org.ashe.alpha.base.exc.BusinessException;
import org.ashe.alpha.base.resp.RespBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service
@Slf4j
public class OauthUserServiceImpl extends ServiceImpl<OauthUserMapper, OauthUser> implements OauthUserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBody<Void> register(RegisterDTO dto, HttpServletRequest request) {
        log.info("Little pigs, little pigs, let me come in." + request);
        // 判断账户是否已注册，若存在则响应提示
        OauthUser user = verifyAccount(dto);
        if (!ObjectUtils.isEmpty(user)) {
            throw new BusinessException("该账户已被注册");
        }
        // 持久化
        registerUser(dto);
        return RespBody.ok();
    }

    public void registerUser(RegisterDTO dto) {
        OauthUser user = new OauthUser();
        user.setAccount(dto.getAccount());
        // 密码加密
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodePassword);
        // clientId取account
        user.setClientId(dto.getAccount());
        this.save(user);
    }

    private OauthUser verifyAccount(RegisterDTO dto) {
        // 获取注册类型枚举
        Register registerEnum = Register.valueOf(dto.getType());
        switch (registerEnum) {
            case ACCOUNT: {
                if (ObjectUtils.isEmpty(dto.getAccount()) || ObjectUtils.isEmpty(dto.getPassword())) {
                    throw new BusinessException("账户或密码未填写");
                }
                return getUserByAccount(dto.getAccount());
            }
            case TEL:
            case WECHAT:
                throw new BusinessException("暂不支持的注册方式");
            default:
                break;
        }
        return null;
    }

    @Override
    public OauthUser getUserByAccount(String account) {
        return baseMapper.loadUserByUsername(account);
    }

    @Override
    public RespBody<Void> delete(RegisterDTO dto, HttpServletRequest request) {
        return null;
    }

    @Override
    public RespBody<Void> edit(EditDTO dto, HttpServletRequest request) {
        log.info("this is edit method!");
        return RespBody.ok();
    }

    @Override
    public IPage<UserVO> listUser(UserDTO dto) {
        Page<Void> page = new Page<>(dto.getCurrent(), dto.getSize());
        return baseMapper.listUser(page, dto);
    }
}
