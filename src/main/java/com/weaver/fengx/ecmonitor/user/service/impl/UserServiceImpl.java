package com.weaver.fengx.ecmonitor.user.service.impl;

import com.weaver.fengx.ecmonitor.user.mapper.UserMapper;
import com.weaver.fengx.ecmonitor.user.entity.UserModel;
import com.weaver.fengx.ecmonitor.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Fengx
 * 用户接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserModel findUserByLoginId(String loginId) {
        return userMapper.findUserByLoginId(loginId);
    }

    @Override
    public UserModel findUserById(String id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void updatePasswd(UserModel userModel) {
        userMapper.updatePasswd(userModel);
    }
}
