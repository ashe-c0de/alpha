package org.ashe.alpha.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ashe.alpha.auth.domain.model.OauthClientDetails;

@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

}
