package com.weaver.fengx.ecmonitor.user.controller;

import com.weaver.fengx.ecmonitor.common.result.AjaxResult;
import com.weaver.fengx.ecmonitor.user.model.UserModel;
import com.weaver.fengx.ecmonitor.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fengx
 * 用户信息
 **/
// @RestController注解是@Controller和@ResponseBody的合集,表示这是个控制器 bean,并且是将函数的返回值直接填入 HTTP 响应体中,是 REST 风格的控制器。
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    /**
     * 更新密码
     * @param originPasswd
     * @param newPasswd
     * @return
     */
    // @PostMapping("users") 等价于@RequestMapping(value="/users",method=RequestMethod.POST)
    @PostMapping("/updatePasswd")
    public AjaxResult<String> updatePasswd(@RequestParam("originPasswd") String originPasswd, @RequestParam("newPasswd") String newPasswd) {
        if ("".equals(originPasswd)) {
            return AjaxResult.warn("原密码不能为空");
        }
        if ("".equals(newPasswd)) {
            return AjaxResult.warn("新密码不能为空");
        }
        Integer userId = ((UserModel) (SecurityUtils.getSubject().getPrincipal())).getId();
        // 判断用户是否存在
        UserModel dbUserModel = userService.findUserById(String.valueOf(userId));
        if (dbUserModel == null) {
            return AjaxResult.warn("用户不存在");
        }
        if (!DigestUtils.md5DigestAsHex(originPasswd.getBytes()).equals(dbUserModel.getPasswd())) {
            return AjaxResult.warn("原密码不正确");
        }
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        // md5加密密码
        userModel.setPasswd(DigestUtils.md5DigestAsHex(newPasswd.getBytes()));
        userService.updatePasswd(userModel);
        return AjaxResult.success();
    }
}
