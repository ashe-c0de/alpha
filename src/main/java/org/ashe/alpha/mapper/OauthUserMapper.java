package org.ashe.alpha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ashe.alpha.domain.model.OauthUser;

@Mapper
public interface OauthUserMapper extends BaseMapper<OauthUser> {

    OauthUser loadUserByUsername(@Param("account") String username);

}
