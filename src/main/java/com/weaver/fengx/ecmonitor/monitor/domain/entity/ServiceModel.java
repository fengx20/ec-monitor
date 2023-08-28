package com.weaver.fengx.ecmonitor.monitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fengx
 * 服务实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 服务描述
     */
    private String serviceDesc;

    /**
     * 最后探测时间距离当前时间的差值状态，如果大于设定的值，则返回0，否则返回1
     */
    private String checkTimeStatus;

    /**
     * 服务状态：0-离线，1-在线
     */
    private String serviceStatus;

    /**
     * 最后在线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastCheckTime;

}
