package com.weaver.fengx.ecmonitor.user.mapper;

import com.weaver.fengx.ecmonitor.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fengx
 * 用户mapper
 **/
@Mapper
public interface UserMapper {

    UserModel findUserByLoginId(String loginId);

    UserModel findUserById(String id);

    void updatePasswd(UserModel userModel);

}
