package com.weaver.fengx.ecmonitor.common.netty.push;

/**
 * @author Fengx
 **/
public enum Cmd {

    START("000", "连接成功"),
    WMESSAGE("001", "消息提醒"),
    DOWN_START("002", "推送开始");

    private String cmd;
    private String desc;

    Cmd(String cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDesc() {
        return desc;
    }
}
