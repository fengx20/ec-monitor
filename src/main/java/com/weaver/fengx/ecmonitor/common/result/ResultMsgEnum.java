package com.weaver.fengx.ecmonitor.common.result;

/**
 * @author Fengx
 * 全局返回结果枚举
 **/
public enum ResultMsgEnum {

    SUCCESS(200, "操作成功"),
    AUTHORIZEDFAIL(400, "认证失败"),
    UNAUTHORIZED(403, "无权限访问"),
    NOT_FOUND(404, "资源，服务未找到"),
    ERROR(500, "系统错误"),
    NOT_IMPLEMENTED(501, "接口未实现"),
    WARN(601, "警告信息");

    private int code;
    private String message;

    ResultMsgEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
