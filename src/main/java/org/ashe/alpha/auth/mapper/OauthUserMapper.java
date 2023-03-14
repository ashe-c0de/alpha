package org.ashe.alpha.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ashe.alpha.auth.domain.dto.UserDTO;
import org.ashe.alpha.auth.domain.model.OauthUser;
import org.ashe.alpha.auth.domain.vo.UserVO;

@Mapper
public interface OauthUserMapper extends BaseMapper<OauthUser> {

    OauthUser loadUserByUsername(@Param("account") String username);

    IPage<UserVO> listUser(Page<Void> page, @Param("dto") UserDTO dto);
}
