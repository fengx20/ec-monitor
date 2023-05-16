package com.weaver.fengx.ecmonitor.common.shiro;

import com.weaver.fengx.ecmonitor.user.model.UserModel;
import com.weaver.fengx.ecmonitor.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义核心组件Realm，开发者自定义的模块，根据项目的需求，验证和授权的逻辑在Realm中实现，
 * 继承抽象类AuthorizingRealm，实现两个抽象方法分别完成授权和认证的逻辑
 */

/**
 * @author Fengx
 * 自定义核心组件Realm
 **/
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    public UserService userService;

    /**
     * 认证
     * AuthenticationInfo用户的角色信息集合，认证时使用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginid = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        UserModel userModel;
        if ("".equals(loginid)) {
            throw new AccountException("用户名不能为空");
        } else if ("".equals(userPwd)) {
            throw new AccountException("密码不能为空");
        } else {
            // 验证
            userModel = userService.findUserByLoginId(loginid);
            if (userModel == null) {
                throw new AccountException("账号不存在");
            } else if (!userModel.getPasswd().equals(DigestUtils.md5DigestAsHex(userPwd.getBytes()))) {
                throw new AccountException("密码不正确");
            }
        }
        return new SimpleAuthenticationInfo(userModel, userPwd, getName());
    }

    /**
     * 授权
     * AuthorizationInfo角色的权限信息集合，授权时使用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return null;
    }


}
