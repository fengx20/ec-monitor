package com.weaver.fengx.ecmonitor.user.service;

import com.weaver.fengx.ecmonitor.user.model.UserModel;

/**
 * @author Fengx
 * 用户接口
 */
public interface UserService {

    /**
     * 根据登录id查询用户
     * @param loginId
     * @return
     */
    UserModel findUserByLoginId(String loginId);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    UserModel findUserById(String id);

    /**
     * 根据id查询用户
     * @param userModel
     */
    void updatePasswd(UserModel userModel);
}
