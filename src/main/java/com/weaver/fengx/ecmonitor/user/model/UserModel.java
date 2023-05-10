package com.weaver.fengx.ecmonitor.user.model;

import java.io.Serializable;

/**
 * @author Fengx
 * 用户实体类
 */
public class UserModel implements Serializable {

    private static final long serialVersionUID = 2930318970156067480L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 登录id
     */
    private String loginid;
    /**
     * 加密密码
     */
    private String passwd;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 角色
     */
    private String roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
