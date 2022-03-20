package com.example.loginservice.service.serviceImpl;

import com.example.loginservice.mapper.UserMapper;
import com.example.loginservice.service.IUserService;
import com.example.loginservice.pojo.User;
import com.example.loginservice.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
