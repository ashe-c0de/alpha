package org.ashe.alpha.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ashe.alpha.domain.model.User;
import org.ashe.alpha.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
