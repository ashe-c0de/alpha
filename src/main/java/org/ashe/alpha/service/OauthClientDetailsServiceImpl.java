package org.ashe.alpha.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ashe.alpha.domain.model.OauthClientDetails;
import org.ashe.alpha.mapper.OauthClientDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {
}
