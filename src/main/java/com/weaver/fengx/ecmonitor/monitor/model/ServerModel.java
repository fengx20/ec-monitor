package com.weaver.fengx.ecmonitor.monitor.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fengx
 * 服务器实体类
 **/
public class ServerModel implements Serializable {

    private static final long serialVersionUID = -2594006136914036062L;

    // 服务id
    private String serverIp;

    // 服务描述
    private String serverDesc;

    // 最后在线时间
    // @JsonFormat：格式化时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastCheckTime;

    // 最后探测时间距离当前时间的差值状态，如果大于设定的值，则返回0，否则返回1
    private String checkTimeStatus;

    // cpu使用率
    private String cpuRate;

    // 内存使用率
    private String memRate;

    // 磁盘使用率
    private String diskRate;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerDesc() {
        return serverDesc;
    }

    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc;
    }

    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public String getCheckTimeStatus() {
        return checkTimeStatus;
    }

    public void setCheckTimeStatus(String checkTimeStatus) {
        this.checkTimeStatus = checkTimeStatus;
    }

    public String getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(String cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getMemRate() {
        return memRate;
    }

    public void setMemRate(String memRate) {
        this.memRate = memRate;
    }

    public String getDiskRate() {
        return diskRate;
    }

    public void setDiskRate(String diskRate) {
        this.diskRate = diskRate;
    }

}
