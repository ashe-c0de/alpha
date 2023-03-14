package org.ashe.alpha.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ashe.alpha.auth.domain.model.OauthClientDetails;
import org.ashe.alpha.auth.mapper.OauthClientDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

}
