package edu.czjt.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjt.reggie.entity.User;
import edu.czjt.reggie.mapper.UserMapper;
import edu.czjt.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
