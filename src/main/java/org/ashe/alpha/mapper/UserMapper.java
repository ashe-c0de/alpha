package org.ashe.alpha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ashe.alpha.domain.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User loadUserByUsername(@Param("clientId") String clientId, @Param("account") String username);
}
