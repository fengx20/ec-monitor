package com.weaver.fengx.ecmonitor.user.controller;

import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.common.result.ResultMsgEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fengx
 * 用户登录
 **/
@RestController
public class LoginController {

    /**
     * 登录
     * @param loginid
     * @param password
     * @return
     */
    @PostMapping("/login")
    public AjaxResult<String> login(@RequestParam("loginid") String loginid, @RequestParam("password") String password) {
        if (loginid == null || password == null || "".equals(loginid.trim()) || "".equals(password.trim())) {
            return AjaxResult.warn("登录名或者密码为空");
        }
        loginid = loginid.trim();
        password = password.trim();
        // 从SecurityUtils里边创建一个subject，Shiro的一个抽象概念，包含了用户信息
        // Subject: 为认证主体。应用代码直接交互的对象是Subject,Subject代表了当前的用户。包含Principals和Credentials两个信息。
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(loginid, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (AuthenticationException ae) {
            return AjaxResult.warn("登录失败："+ae.getMessage());
        }
        // 如果此用户在其当前会话期间通过提供与系统已知凭据匹配的有效凭据来证明其身份，则返回true
        if (subject.isAuthenticated()) {
            return AjaxResult.success("登录成功");
        } else {
            return AjaxResult.warn("未通过认证");
        }
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public AjaxResult<String> logout() {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return AjaxResult.success();
    }

    /**
     * 未授权
     *
     * @return
     */
    @GetMapping("/noauth")
    public AjaxResult<String> unauthorized() {
        return AjaxResult.error(ResultMsgEnum.UNAUTHORIZED.getMessage());
    }

}
