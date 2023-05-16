package com.weaver.fengx.ecmonitor.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fengx
 * 服务实体类
 **/
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 1894732455966424416L;

    // 服务id
    private String serviceId;

    // 服务描述
    private String serviceDesc;

    // 最后探测时间距离当前时间的差值状态，如果大于设定的值，则返回0，否则返回1
    private String checkTimeStatus;

    // 服务状态：0-离线，1-在线
    private String serviceStatus;

    // 最后在线时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastCheckTime;

    public String getCheckTimeStatus() {
        return checkTimeStatus;
    }

    public void setCheckTimeStatus(String checkTimeStatus) {
        this.checkTimeStatus = checkTimeStatus;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }


}
